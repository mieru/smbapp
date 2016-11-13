package command.restaction.zgloszenie;

import java.sql.Timestamp;
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
import utils.SmbUtil;
import utils.status.Status;

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
		KategoiaZgloszenia kategoiaZgloszenia = null;
		Uzytkownik uzytkownik = null;
		if(json.idUser != null){
			 uzytkownik = uzytkownikEjbQueryController.findEntityByID(json.idUser);
		}
		
		if(json.idKategoria != null){
			 kategoiaZgloszenia = kategoriaZgloszenieEjbQueryController.findEntityByID(json.idKategoria);
		}
		
		if(uzytkownik != null && kategoiaZgloszenia != null){
			Zgloszenie zgloszenie = new Zgloszenie();
			zgloszenie.setUzytkownik2(uzytkownik);
			zgloszenie.setTresc(json.tresc);
			zgloszenie.setTemat(json.temat);
			zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.NOWE);
			zgloszenie.setNumerZgloszenia(zgloszeniaEjbQueryController.generujNumerZgloszenia());
			zgloszenie.setDataZgloszenia(new Timestamp(System.currentTimeMillis()));
			zgloszenie.setKategoiaZgloszenia(kategoiaZgloszenia);
			zgloszeniaEjbCommandContoller.insert(zgloszenie);
		}
		
		return "";
	}
	
	@POST
	@Path("/addActivity")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void dodajAktywnoscDoZgloszenie(ZgloszenieJsonData json) throws JSONException, AddressException, MessagingException {
		Zgloszenie zgloszenie = null;
		if(json.idZgloszenia != null){
			 zgloszenie = zgloszeniaEjbQueryController.findEntityByID(json.idZgloszenia);
		}
		Uzytkownik uzytkownik = null;
		if(json.idUser != null){
			 uzytkownik = uzytkownikEjbQueryController.findEntityByID(json.idUser);
		}
		
		ZgloszenieKomentarz zgloszenieKomentarz = new ZgloszenieKomentarz();
		zgloszenieKomentarz.setTresc(json.tresc);
		zgloszenieKomentarz.setTyp("K");
		zgloszenieKomentarz.setDataDodania(new Timestamp(System.currentTimeMillis()));
		zgloszenieKomentarz.setUzytkownik(uzytkownik);
		zgloszenieKomentarz.setZgloszenie(zgloszenie);
		
		zgloszenieKomentarzEjbCommandContoller.insert(zgloszenieKomentarz);
		
	}
	
}
 