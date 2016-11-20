package command.restaction.register;

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

import facade.rejestracja.RejestracjaFacade;

@RequestScoped
@Path("/command/register")
public class RegisterRestAction {

	
	@EJB
	RejestracjaFacade rejestracjaFacade;
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerUser(RegisterRequestData registerJsonData) throws JSONException, AddressException, MessagingException {
		return rejestracjaFacade.rejestrujUzytkownika(registerJsonData);
	}
	
	@POST
	@Path("/activeAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String activeAccount(RegisterRequestData registerJsonData) throws JSONException {
		return rejestracjaFacade.aktywujKonto(registerJsonData);
		
	}
	
}
