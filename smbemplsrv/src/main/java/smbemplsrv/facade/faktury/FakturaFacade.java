package smbemplsrv.facade.faktury;

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

import smbemplsrv.command.ejbcontrol.faktua.FakturaEjbCommandController;
import smbemplsrv.dbmodel.FakturaSprzedazy;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.faktura.FakturaEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.restaction.faktury.FakturyResponseData;
import utils.status.Status;


@Stateless
@LocalBean
public class FakturaFacade {

	@EJB
	FakturaEjbQueryController fakturaEjbQueryController;
	
	@EJB
	FakturaEjbCommandController fakturaEjbCommandController;
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	
	public Response getFakturaPdfFile(Integer idFaktury) {
	FakturaSprzedazy fakturaSprzedazy = null;
	if(idFaktury!= null){
		fakturaSprzedazy = fakturaEjbQueryController.findEntityByID(idFaktury);
	}
	File file = new File(System.getProperty("jboss.server.data.dir").toString() + fakturaSprzedazy.getFilePath());
	ResponseBuilder response = Response.ok((Object) file);
	response.header("Content-Disposition", "attachment; filename=\"Faktura-" + fakturaSprzedazy.getIdFaktury() + ".pdf\"");
	return response.build();

}
	
	public String getFakturyUzytkownika(FakturyResponseData fakturyResponseData){
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<FakturaSprzedazy> fakCol = null;
		if(fakturyResponseData.idUser != null){
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(Integer.parseInt(fakturyResponseData.idUser));
			fakCol = uzytkownik.getFakturaSprzedazies2();
		}else{
			fakCol = fakturaEjbQueryController.findAll();
		}
		
		for (FakturaSprzedazy fakturaSprzedazy : fakCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", fakturaSprzedazy.getIdFaktury());
			jsonObject.put("daneKlienta", fakturaSprzedazy.getDaneKlienta());
			jsonObject.put("adnotacje", fakturaSprzedazy.getAdnotacja());
			jsonObject.put("daneWystawiajacego", fakturaSprzedazy.getDaneWystawiajacego());
			if(fakturaSprzedazy.getDataWystawienia() != null)
				jsonObject.put("dataWystawienia", fakturaSprzedazy.getDataWystawienia().getTime());
			jsonObject.put("listaTowarow", fakturaSprzedazy.getListaTowarow());
			jsonObject.put("numerFaktury",fakturaSprzedazy.getNumerFaktury());
			jsonObject.put("status",  Status.FAKTURA_STATE.getText(fakturaSprzedazy.getStatus()));
			jsonObject.put("pdf",  Status.FAKTURA_STATE.SPRZEDARZ.equals(fakturaSprzedazy.getStatus()));
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}

	public String getFakturaById(FakturyResponseData fakturyResponseData) throws JSONException {
		FakturaSprzedazy fakturaSprzedazy = fakturaEjbQueryController.findEntityByID(fakturyResponseData.idFaktury);
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
