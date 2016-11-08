package query.restaction.faktury;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.FakturaSprzedazy;
import dbmodel.Uzytkownik;
import query.ejbcontrol.faktury.FakturyEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;

@RequestScoped
@Path("/query/faktury")
public class FakturyRestAction {
	@Context
	private UriInfo uri;

	@EJB
	FakturyEjbQueryController fakturyEjbQueryController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@POST
	@Path("/getFaktury")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienia(FakturyJsonData json) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<FakturaSprzedazy> fakCol = null;
		if(json.idUser != null){
			Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(Integer.parseInt(json.idUser));
			fakCol = uzytkownik.getFakturaSprzedazies1();
		}
		
		for (FakturaSprzedazy fakturaSprzedazy : fakCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", fakturaSprzedazy.getIdFaktury());
			jsonObject.put("daneKlienta", fakturaSprzedazy.getDaneKlienta());
			jsonObject.put("adnotacje", fakturaSprzedazy.getAdnotacja());
			jsonObject.put("daneWystawiajacego", fakturaSprzedazy.getDaneWystawiajacego());
			jsonObject.put("dataWystawienia", fakturaSprzedazy.getDataWystawienia());
			jsonObject.put("listaTowarow", fakturaSprzedazy.getListaTowarow());
			jsonObject.put("numerFaktury", fakturaSprzedazy.getNumerFaktury());
			jsonObject.put("status", fakturaSprzedazy.getStatus());
			
			jsonArray.put(jsonObject);
		}
		
		
		return jsonArray.toString();
	}
	
	@POST
	@Path("/getFaktuaById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String geFakturaById(FakturyJsonData json) throws JSONException {
			FakturaSprzedazy fakturaSprzedazy = fakturyEjbQueryController.findEntityByID(json.idFaktury);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", fakturaSprzedazy.getIdFaktury());
			jsonObject.put("daneKlienta", fakturaSprzedazy.getDaneKlienta());
			jsonObject.put("adnotacje", fakturaSprzedazy.getAdnotacja());
			jsonObject.put("daneWystawiajacego", fakturaSprzedazy.getDaneWystawiajacego());
			jsonObject.put("dataWystawienia", fakturaSprzedazy.getDataWystawienia());
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
