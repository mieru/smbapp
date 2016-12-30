package restapi.login;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import businesslogic.login.LoginLogicController;


@RequestScoped
@Path("/login")
public class LoginRestAction {

	@EJB
	LoginLogicController loginLogicController;

	@POST
	@Path("/checkLogin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(LoginRestData loginRestData) throws JSONException{
		return loginLogicController.checkLoginData(loginRestData);
	} 
}
