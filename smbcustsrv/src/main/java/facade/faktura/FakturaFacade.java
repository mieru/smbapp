package facade.faktura;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.FakturaSprzedazy;
import dbmodel.Uzytkownik;
import query.ejbcontrol.faktury.FakturyEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.restaction.faktury.FakturyResponseData;

@Stateless
@LocalBean
public class FakturaFacade {

	@EJB
	FakturyEjbQueryController fakturyEjbQueryController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	public Response getFakturaPdfFile(Integer idFaktury) {
	FakturaSprzedazy fakturaSprzedazy = null;
	if(idFaktury!= null){
		fakturaSprzedazy = fakturyEjbQueryController.findEntityByID(idFaktury);
	}
	File file = new File(System.getProperty("jboss.server.data.dir").toString() + fakturaSprzedazy.getFilePath());
	ResponseBuilder response = Response.ok((Object) file);
	response.header("Content-Disposition", "attachment; filename=\"" + fakturaSprzedazy.getFileName() + "\"");
	return response.build();

}
	
	public String getFakturyUzytkownika(FakturyResponseData fakturyResponseData){
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<FakturaSprzedazy> fakCol = null;
		if(fakturyResponseData.idUser != null){
			Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(Integer.parseInt(fakturyResponseData.idUser));
			fakCol = uzytkownik.getFakturaSprzedazies1();
		
		for (FakturaSprzedazy fakturaSprzedazy : fakCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", fakturaSprzedazy.getIdFaktury());
			jsonObject.put("daneKlienta", fakturaSprzedazy.getDaneKlienta());
			jsonObject.put("adnotacje", fakturaSprzedazy.getAdnotacja());
			jsonObject.put("daneWystawiajacego", fakturaSprzedazy.getDaneWystawiajacego());
			if(fakturaSprzedazy.getDataWystawienia() != null)
				jsonObject.put("dataWystawienia", fakturaSprzedazy.getDataWystawienia().getTime());
			jsonObject.put("listaTowarow", fakturaSprzedazy.getListaTowarow());
			jsonObject.put("numerFaktury", fakturaSprzedazy.getNumerFaktury());
			jsonObject.put("status", fakturaSprzedazy.getStatus());
			
			jsonArray.put(jsonObject);
		}
		}
		return jsonArray.toString();
	}

	public String getFakturaById(FakturyResponseData fakturyResponseData) throws JSONException {
		FakturaSprzedazy fakturaSprzedazy = fakturyEjbQueryController.findEntityByID(fakturyResponseData.idFaktury);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", fakturaSprzedazy.getIdFaktury());
		jsonObject.put("daneKlienta", fakturaSprzedazy.getDaneKlienta());
		jsonObject.put("adnotacje", fakturaSprzedazy.getAdnotacja());
		jsonObject.put("daneWystawiajacego", fakturaSprzedazy.getDaneWystawiajacego());
		jsonObject.put("dataWystawienia", fakturaSprzedazy.getDataWystawienia().getTime());
		jsonObject.put("listaTowarow", fakturaSprzedazy.getListaTowarow());
		jsonObject.put("numerFaktury", fakturaSprzedazy.getNumerFaktury());
		jsonObject.put("status", fakturaSprzedazy.getStatus());
		
		Float wartoscAllNetto = 0f;
		Float sumaPodatkuVat = 0f;
		Float wartoscAllBrutto = 0f;
		
		JSONArray jsonArray = new JSONArray(fakturaSprzedazy.getListaTowarow());
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
			wartoscAllNetto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
			wartoscAllBrutto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cbrutto").toString());
		}
		
		sumaPodatkuVat += wartoscAllBrutto - wartoscAllNetto;
		
		jsonObject.put("wartoscAllNetto", wartoscAllNetto);
		jsonObject.put("sumaPodatkuVat", sumaPodatkuVat);
		jsonObject.put("wartoscAllBrutto", wartoscAllBrutto);
		
		
		
	return jsonObject.toString();
}
}
