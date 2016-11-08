package command.restaction.zgloszenie;

import java.util.Date;
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

import command.ejbcontrol.zglkomentarz.ZgloszenieKomentarzEjbCommandContoller;
import command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandContoller;
import dbmodel.KategoiaZgloszenia;
import dbmodel.Uzytkownik;
import dbmodel.Zgloszenie;
import dbmodel.ZgloszenieKomentarz;
import query.ejbcontrol.kategoriazgloszenie.KategoriaZgloszenieEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;

@RequestScoped
@Path("/command/zgloszenia")
public class ZgloszenieRestAction {

	 @Context
	 private UriInfo uri;
	
	@EJB
	ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;
	
	@EJB
	ZgloszeniaEjbCommandContoller zgloszeniaEjbCommandContoller;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@EJB
	KategoriaZgloszenieEjbQueryController kategoriaZgloszenieEjbQueryController;
	
	@EJB
	ZgloszenieKomentarzEjbCommandContoller zgloszenieKomentarzEjbCommandContoller; 
	
	
	@POST
	@Path("/dodajZgloszenie")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String dodajZgloszenie(ZgloszenieJsonData json) throws JSONException, AddressException, MessagingException {
		Zgloszenie zgloszenie = new Zgloszenie();
		Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(json.idUser);
		zgloszenie.setUzytkownik2(uzytkownik);
		zgloszenie.setTresc(json.tresc);
		zgloszenie.setTemat(json.temat);
		zgloszenie.setStatus("N");
		zgloszenie.setNumerZgloszenia("2016/11/01");
		zgloszenie.setDataZgloszenia(new Date(System.currentTimeMillis()));
		KategoiaZgloszenia kategoiaZgloszenia = kategoriaZgloszenieEjbQueryController.findEntityByID(json.idKategoria);
		zgloszenie.setKategoiaZgloszenia(kategoiaZgloszenia);
		
		zgloszeniaEjbCommandContoller.insert(zgloszenie);
		
	return "";
	}
	
	@POST
	@Path("/addActivity")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void dodajAktywnoscDoZgloszenie(ZgloszenieJsonData json) throws JSONException, AddressException, MessagingException {
		Zgloszenie zgloszenie = zgloszeniaEjbQueryController.findEntityByID(json.idZgloszenia);
		Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(json.idUser);
		
		ZgloszenieKomentarz zgloszenieKomentarz = new ZgloszenieKomentarz();
		zgloszenieKomentarz.setTresc(json.tresc);
		zgloszenieKomentarz.setTyp("K");
		zgloszenieKomentarz.setDataDodania(new Date(System.currentTimeMillis()));
		zgloszenieKomentarz.setUzytkownik(uzytkownik);
		zgloszenieKomentarz.setZgloszenie(zgloszenie);
		
		zgloszenieKomentarzEjbCommandContoller.insert(zgloszenieKomentarz);
		
	}
	
}
 