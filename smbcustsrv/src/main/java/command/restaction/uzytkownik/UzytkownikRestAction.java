package command.restaction.uzytkownik;

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

import facade.uzytkownik.UzytkownikFacade;

@RequestScoped
@Path("/command/uzytkownik")
public class UzytkownikRestAction {
	
	@EJB
	UzytkownikFacade uzytkownikFacade;
	
	@POST
	@Path("/saveUserData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(UzytkownikRequestCommandData uzytkownikRequestData) throws JSONException, AddressException, MessagingException {
		return uzytkownikFacade.sprawdzDaneLogowania(uzytkownikRequestData);
	}
	
}
