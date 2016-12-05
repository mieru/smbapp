package smbemplsrv.query.restaction.logowanie;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import smbemplsrv.facade.logowanie.LogowanieFacade;


@RequestScoped
@Path("/query/login")
public class LoginRestAction {

	@EJB
	LogowanieFacade logowanieFacade;

	@POST
	@Path("/checkLogin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(LoginRequestData loginRequestData) throws JSONException{
		return logowanieFacade.sprawdzDaneLogowania(loginRequestData);
	} 
}
