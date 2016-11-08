package query.restaction.zgloszenie;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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

import command.restaction.zgloszenie.ZgloszenieJsonData;
import dbmodel.KategoiaZgloszenia;
import dbmodel.Uzytkownik;
import dbmodel.Zgloszenie;
import dbmodel.ZgloszenieKomentarz;
import query.ejbcontrol.kategoriazgloszenie.KategoriaZgloszenieEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;

@RequestScoped
@Path("/query/zgloszenia")
public class ZgloszeniaRestAction {
	@Context
	private UriInfo uri;

	@EJB
	ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@EJB
	KategoriaZgloszenieEjbQueryController kategoriaZgloszenieEjbQueryController;
	
	@POST
	@Path("/getKategorie")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getKategorieZglosznia() throws JSONException, AddressException, MessagingException {
		List<KategoiaZgloszenia> katCol = kategoriaZgloszenieEjbQueryController.findAll();
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (KategoiaZgloszenia kategoiaZgloszenia : katCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", kategoiaZgloszenia.getIdKategrorii());
			jsonObject.put("name", kategoiaZgloszenia.getNazwa());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	@POST
	@Path("/getZgloszeniaUzytkownika")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszeniaUzytkownika(ZgloszenieJsonData json) throws JSONException, AddressException, MessagingException {
		Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(json.idUser);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<Zgloszenie> colZgl = uzytkownik.getZgloszenies2();
		
		for (Zgloszenie zgloszenie : colZgl) {
			jsonObject = new JSONObject();
			jsonObject.put("id", zgloszenie.getIdZgloszenia());
			jsonObject.put("numer_zgl", zgloszenie.getNumerZgloszenia());
			jsonObject.put("data_zgloszenia", zgloszenie.getDataZgloszenia());
			jsonObject.put("data_zamkniecia", zgloszenie.getDataZamkniecia());
			jsonObject.put("kategoria", zgloszenie.getKategoiaZgloszenia().getNazwa());
			jsonObject.put("temat", zgloszenie.getTemat());
			if( zgloszenie.getUzytkownik1() != null){
				jsonObject.put("pracownik_obsl", zgloszenie.getUzytkownik1().getName() +" "+  zgloszenie.getUzytkownik1().getSurname());
			}
			jsonObject.put("status", zgloszenie.getStatus());
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	@POST
	@Path("/getZgloszenieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszenieById(ZgloszenieJsonData json) throws JSONException, AddressException, MessagingException {
		Zgloszenie zgloszenie = zgloszeniaEjbQueryController.findEntityByID(json.idZgloszenia);
		
			JSONObject	jsonObject = new JSONObject();
			jsonObject.put("id", zgloszenie.getIdZgloszenia());
			jsonObject.put("numer_zgl", zgloszenie.getNumerZgloszenia());
			jsonObject.put("data_zgloszenia", zgloszenie.getDataZgloszenia());
			jsonObject.put("data_zamkniecia", zgloszenie.getDataZamkniecia());
			jsonObject.put("kategoria", zgloszenie.getKategoiaZgloszenia().getNazwa());
			jsonObject.put("temat", zgloszenie.getTemat());
			jsonObject.put("tresc", zgloszenie.getTresc());
			jsonObject.put("aktywnosci", getJsonArray(zgloszenie.getZgloszenieKomentarzs()));
			
			if( zgloszenie.getUzytkownik1() != null){
				jsonObject.put("pracownik_obsl", zgloszenie.getUzytkownik1().getName() +" "+  zgloszenie.getUzytkownik1().getSurname());
			}
			jsonObject.put("status", zgloszenie.getStatus());
			
		
		return jsonObject.toString();
	}



private JSONArray getJsonArray(List<ZgloszenieKomentarz> zgloszenieKomentarze) throws JSONException {
JSONArray jsonArray = new JSONArray();
JSONObject jsonObject = null;

for (ZgloszenieKomentarz zglKom : zgloszenieKomentarze) {
jsonObject = new JSONObject();
jsonObject.put("id", zglKom.getIdKomentarzaZgloszenia());
jsonObject.put("data", zglKom.getDataDodania());
jsonObject.put("tresc", zglKom.getTresc());
String uzytkownikName = "";
if(zglKom.getUzytkownik().getCompanyName() != null){
	uzytkownikName = zglKom.getUzytkownik().getCompanyName();
}else{
	uzytkownikName = zglKom.getUzytkownik().getName() + " " + zglKom.getUzytkownik().getSurname();
}
jsonObject.put("uzytkownik", uzytkownikName);
//	jsonObject.put('typ', );

jsonArray.put(jsonObject);
}
return jsonArray;
}
}
