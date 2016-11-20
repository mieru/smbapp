package command.restaction.zgloszenie;

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

import facade.zgloszenia.ZgloszenieFacade;

@RequestScoped
@Path("/command/zgloszenia")
public class ZgloszenieRestAction {
	
	@EJB
	ZgloszenieFacade zgloszenieFacade;
	
	
	@POST
	@Path("/addNewNotification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewNotification(ZgloszenieRequestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException {
		return zgloszenieFacade.dodajZgloszenie(zgloszenieRequestData).toString();
	}
	
	@POST
	@Path("/addMessageToNotification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addMessageToNotification(ZgloszenieRequestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException {
		zgloszenieFacade.dodajWiadomoscDoZgloszenia(zgloszenieRequestData);
	}
	
}
 