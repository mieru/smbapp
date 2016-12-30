package restapi.user;

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

import businesslogic.user.UserLogicController;

@RequestScoped
@Path("/user")
public class UserRest {
	
	@EJB
	UserLogicController uzytkownikFacade;
	
	@POST
	@Path("/getUserById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUserById(UserRestData userRestData) throws JSONException, IllegalArgumentException, IllegalAccessException {
		return uzytkownikFacade.getUsersById(userRestData);
	}
	
	@POST
	@Path("/saveUserData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveUserData(UserRestData userRestData) throws JSONException, AddressException, MessagingException {
		return uzytkownikFacade.saveUsersData(userRestData);
	}
	
}
