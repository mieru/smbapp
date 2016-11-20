package facade.logowanie;

import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.Uzytkownik;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.restaction.login.LoginRequestData;
import utils.status.Status;

@Stateless
@LocalBean
public class LogowanieFacade {
	private final String LOGIN_RESULT = "login_result";
	private final String NOT_ACTIVE = "not_active";
	private final String ID_USER = "id_user";
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	public String sprawdzDaneLogowania(LoginRequestData loginRequestData) throws JSONException{
			JSONObject jsonObject = new JSONObject();
			
			Uzytkownik uzytkownik = new Uzytkownik();
			uzytkownik.setLogin(loginRequestData.username);
			uzytkownik.setPassword(new String(Base64.getEncoder().encode(loginRequestData.password.getBytes())));
			List<Uzytkownik> userList = uzytkownikEjbQueryController.findEntity(uzytkownik);
			
			if (userList.size() == 1) {
				uzytkownik = userList.iterator().next();
				if (uzytkownik.getState().equals(Status.USER_STATE.ACTIVE)) {
					jsonObject.put(LOGIN_RESULT, Boolean.TRUE);
					jsonObject.put(ID_USER, uzytkownik.getIdUser());
				} else {
					jsonObject.put(LOGIN_RESULT, NOT_ACTIVE);
				}
			} else {
				jsonObject.put(LOGIN_RESULT, Boolean.FALSE);
			}

			return jsonObject.toString();
		}
	
}
