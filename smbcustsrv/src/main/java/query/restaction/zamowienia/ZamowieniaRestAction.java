package query.restaction.zamowienia;

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

import dbmodel.Uzytkownik;
import dbmodel.WiadomoscZamSprzedaz;
import dbmodel.ZamowienieSprzedaz;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zamowieniasprzedarz.ZamowieniaSprzedEjbQueryController;
import utils.status.Status;

@RequestScoped
@Path("/query/zamowienia")
public class ZamowieniaRestAction {
	@Context
	private UriInfo uri;

	@EJB
	ZamowieniaSprzedEjbQueryController zamowieniaSprzedEjbQueryController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@POST
	@Path("/getZamowienia")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienia(ZamowieniaJsonData json) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<ZamowienieSprzedaz> zamCol = null;
		if(json.idUser != null){
			ZamowienieSprzedaz zamowienieSprzedaz = new ZamowienieSprzedaz();
			Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(Integer.parseInt(json.idUser));
			zamowienieSprzedaz.setUzytkownik1(uzytkownik);
			zamCol = uzytkownik.getZamowienieSprzedazs1();
		}else{
			zamCol = zamowieniaSprzedEjbQueryController.findAll();
		}
		
		for (ZamowienieSprzedaz zamowienieSprzedaz : zamCol) {
			jsonObject = new JSONObject();
			
			jsonObject.put("id", zamowienieSprzedaz.getIdZamowieniaSprzedaz());
			jsonObject.put("dataZlozenia", zamowienieSprzedaz.getDataZlozenia().getTime());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(zamowienieSprzedaz.getStatus()));
			if(zamowienieSprzedaz.getUzytkownik2() != null)
				jsonObject.put("pracownik", zamowienieSprzedaz.getUzytkownik2().getName() + " " +  zamowienieSprzedaz.getUzytkownik2().getSurname());
			jsonObject.put("numer_zam", zamowienieSprzedaz.getNumerZamowienia());
			
			jsonArray.put(jsonObject);
		}
		
		
		return jsonArray.toString();
	}
	
	@POST
	@Path("/getZamowienieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienieById(ZamowieniaJsonData json) throws JSONException {
			ZamowienieSprzedaz zamowienieSprzedaz = zamowieniaSprzedEjbQueryController.findEntityByID(json.idZamowienia);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", zamowienieSprzedaz.getIdZamowieniaSprzedaz());
			jsonObject.put("czyFaktura", zamowienieSprzedaz.getCzyFaktura());
			jsonObject.put("daneDoFaktury", zamowienieSprzedaz.getDaneDoFaktury());
			jsonObject.put("listaProd", zamowienieSprzedaz.getListaProduktow());
			if(zamowienieSprzedaz.getDataZakonczenia() != null)
			jsonObject.put("dataZakonczenia", zamowienieSprzedaz.getDataZakonczenia().getTime());
			jsonObject.put("dataZlozenia", zamowienieSprzedaz.getDataZlozenia().getTime());
			jsonObject.put("adresDostawy", zamowienieSprzedaz.getAdresDostawy());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(zamowienieSprzedaz.getStatus()));
			jsonObject.put("aktywnosci", getJsonArray(zamowienieSprzedaz.getWiadomoscZamSprzedazs()));
			
			Float wartoscAllNetto = 0f;
			Float sumaPodatkuVat = 0f;
			Float wartoscAllBrutto = 0f;
			
			JSONArray jsonArray = new JSONArray(zamowienieSprzedaz.getListaProduktow());
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

	private JSONArray getJsonArray(List<WiadomoscZamSprzedaz> wiadomoscZamSprzedazs) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (WiadomoscZamSprzedaz wiadomoscZamSprzedaz : wiadomoscZamSprzedazs) {
			jsonObject = new JSONObject();
			jsonObject.put("id", wiadomoscZamSprzedaz.getIdWiadZamSprzed());
			jsonObject.put("data", wiadomoscZamSprzedaz.getDataDodanie().getTime());
			jsonObject.put("tresc", wiadomoscZamSprzedaz.getTresc());
			String uzytkownikName = "";
			if(wiadomoscZamSprzedaz.getUzytkownik().getCompanyName() != null){
				uzytkownikName = wiadomoscZamSprzedaz.getUzytkownik().getCompanyName();
			}else{
				uzytkownikName = wiadomoscZamSprzedaz.getUzytkownik().getName() + " " + wiadomoscZamSprzedaz.getUzytkownik().getSurname();
			}
			jsonObject.put("uzytkownik", uzytkownikName);
			
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}
	
}
