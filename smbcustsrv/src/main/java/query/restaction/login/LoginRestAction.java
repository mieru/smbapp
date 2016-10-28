package query.restaction.login;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.Uzytkownik;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import utils.status.Status;

@RequestScoped
@Path("/query/login")
public class LoginRestAction {
	private final String LOGIN_RESULT = "login_result";
	private final String NOT_ACTIVE = "not_active";
	@Context
	private UriInfo uri;

	@EJB
	UzytkownikEjbQueryController uzytkownikQueryControler;

	@POST
	@Path("/checkLoginData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(LoginJsonData json) throws JSONException {

		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setLogin(json.username);
		uzytkownik.setPassword(json.password);
		List<Uzytkownik> userList = uzytkownikQueryControler.findEntity(uzytkownik);
		JSONObject jsonObject = new JSONObject();
		if (userList.size() == 1) {
			uzytkownik = userList.iterator().next();
			if (uzytkownik.getState().equals(Status.USER_STATS.ACTIVE)) {
				jsonObject.put(LOGIN_RESULT, Boolean.TRUE);
			} else {
				jsonObject.put(LOGIN_RESULT, NOT_ACTIVE);
			}
		} else {
			jsonObject.put(LOGIN_RESULT, Boolean.FALSE);
		}

		return jsonObject.toString();
	}
}