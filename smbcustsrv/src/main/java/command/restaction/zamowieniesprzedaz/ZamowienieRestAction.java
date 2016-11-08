package command.restaction.zamowieniesprzedaz;

import java.util.Date;

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

import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.wiadomosczamsprz.WiadZamSprzedEjbCommandController;
import command.ejbcontrol.zamowieniesprzedaz.ZamowienieSprzedEjbCommandContoller;
import dbmodel.Uzytkownik;
import dbmodel.WiadomoscZamSprzedaz;
import dbmodel.ZamowienieSprzedaz;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zamowieniasprzedarz.ZamowieniaSprzedEjbQueryController;

@RequestScoped
@Path("/command/zamowienie")
public class ZamowienieRestAction {

	 @Context
	 private UriInfo uri;
	
	@EJB
	ZamowienieSprzedEjbCommandContoller zamowienieSprzedEjbCommandContoller;
	
	@EJB
	ZamowieniaSprzedEjbQueryController zamowienieSprzedEjbQueryContoller;
	
	@EJB
	WiadZamSprzedEjbCommandController wiadZamSprzedEjbCommandController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@POST
	@Path("/addNewOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(ZamowienieJsonData json) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		
		ZamowienieSprzedaz zamowienieSprzedaz = new ZamowienieSprzedaz();
		zamowienieSprzedaz.setAdresDostawy(json.adresDostawy);
		zamowienieSprzedaz.setCzyFaktura(json.czyFaktura);
		zamowienieSprzedaz.setDaneDoFaktury(json.daneDoFaktury);
		zamowienieSprzedaz.setDataZlozenia(new Date(System.currentTimeMillis()));
		zamowienieSprzedaz.setListaProduktow(json.listaPoduktow);
		zamowienieSprzedaz.setStatus("N");

		Uzytkownik uzytkownik  = uzytkownikEjbQueryController.findEntityByID(Integer.parseInt(json.idZamawiajacego));
		zamowienieSprzedaz.setUzytkownik1(uzytkownik);
		
		zamowienieSprzedaz = zamowienieSprzedEjbCommandContoller.insert(zamowienieSprzedaz);
		
		WiadomoscZamSprzedaz wiadomoscZamSprzedaz = new WiadomoscZamSprzedaz();
		wiadomoscZamSprzedaz.setDataDodanie(new Date(System.currentTimeMillis()));
		wiadomoscZamSprzedaz.setTresc("ZAMOWIENIE ZAREJESTROWANE \\n\\n " + json.wiadDoSprzedawcy);
		wiadomoscZamSprzedaz.setUzytkownik(uzytkownik);
		wiadomoscZamSprzedaz.setZamowienieSprzedaz(zamowienieSprzedaz);
		
		wiadZamSprzedEjbCommandController.insert(wiadomoscZamSprzedaz);
		
		
		jsonObject.put("ok",true);
		
		return jsonObject.toString();
		
	}
	
	@POST
	@Path("/addActivity")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addActivity(ZamowienieJsonData json) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Uzytkownik uzytkownik  = uzytkownikEjbQueryController.findEntityByID(json.idUzytkownika);
		ZamowienieSprzedaz zamowienieSprzedaz = zamowienieSprzedEjbQueryContoller.findEntityByID(json.idZamowienia);
		
		if(uzytkownik.getIdUser().equals(zamowienieSprzedaz.getUzytkownik1().getIdUser())){
		
		WiadomoscZamSprzedaz wiadomoscZamSprzedaz = new WiadomoscZamSprzedaz();
		wiadomoscZamSprzedaz.setTresc(json.trescWiadomosci);
		wiadomoscZamSprzedaz.setUzytkownik(uzytkownik);
		wiadomoscZamSprzedaz.setDataDodanie(new Date(System.currentTimeMillis()));
		wiadomoscZamSprzedaz.setZamowienieSprzedaz(zamowienieSprzedaz);
		
		wiadZamSprzedEjbCommandController.insert(wiadomoscZamSprzedaz);
		
		}
		jsonObject.put("ok",true);
		
		return jsonObject.toString();
		
	}
}
