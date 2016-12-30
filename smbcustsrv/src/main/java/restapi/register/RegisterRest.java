package restapi.register;

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

import businesslogic.register.RegisterLogicController;

@RequestScoped
@Path("/register")
public class RegisterRest {

	
	@EJB
	RegisterLogicController registerLogicController;
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String registerUser(RegisterRestData registerJsonData) throws JSONException, AddressException, MessagingException {
		return registerLogicController.registerUser(registerJsonData);
	}
	
	@POST
	@Path("/activeAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String activeAccount(RegisterRestData registerJsonData) throws JSONException {
		return registerLogicController.activateAccount(registerJsonData);
		
	}
	
}
