package query.restaction.zgloszenie;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import command.restaction.zgloszenie.ZgloszenieRequestData;
import facade.zgloszenia.ZgloszenieFacade;

@RequestScoped
@Path("/query/zgloszenia")
public class ZgloszeniaRestAction {
	@EJB
	ZgloszenieFacade zgloszenieFacade;
	
	@POST
	@Path("/getKategorie")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getKategorieZglosznia() throws JSONException, AddressException, MessagingException {
		return zgloszenieFacade.getKategorieZglosznia();
	}
	
	@POST
	@Path("/getZgloszeniaUzytkownika")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszeniaUzytkownika(ZgloszenieRequestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException {
		return zgloszenieFacade.getZgloszeniaUzytkownika(zgloszenieRequestData);
	}
	
	@POST
	@Path("/getZgloszenieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszenieById(ZgloszenieRequestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException {
		return zgloszenieFacade.getZgloszenieById(zgloszenieRequestData);
	}



}
