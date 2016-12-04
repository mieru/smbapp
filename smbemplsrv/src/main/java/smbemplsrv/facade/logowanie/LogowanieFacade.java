package smbemplsrv.facade.logowanie;

import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.restaction.logowanie.LoginRequestData;
import utils.status.Status;

@Stateless
@LocalBean
public class LogowanieFacade {
	private final String LOGIN_RESULT = "login_result";
	private final String NOT_ACTIVE = "not_active";
	private final String ID_USER = "id_user";
	private final String ID_ADM = "isAdm";
	private final String ID_PRAC = "isPrac";
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	
	public String sprawdzDaneLogowania(LoginRequestData loginRequestData) throws JSONException{
			JSONObject jsonObject = new JSONObject();
			
			Uzytkownik uzytkownik = new Uzytkownik();
			uzytkownik.setLogin(loginRequestData.username);
			uzytkownik.setPassword(new String(Base64.getEncoder().encode(loginRequestData.password.getBytes())));
			List<Uzytkownik> userList = uzytkownicyEjbQueryController.findEntity(uzytkownik);
			
			if (userList.size() == 1 && userList.iterator().next().getRole().contains("E")) {
				uzytkownik = userList.iterator().next();
				if (uzytkownik.getState().equals(Status.USER_STATE.ACTIVE)) {
					jsonObject.put(LOGIN_RESULT, Boolean.TRUE);
					jsonObject.put(ID_USER, uzytkownik.getIdUser());
					jsonObject.put(ID_ADM, uzytkownik.getRole().contains("A"));
					jsonObject.put(ID_PRAC, uzytkownik.getRole().contains("E"));
				} else {
					jsonObject.put(LOGIN_RESULT, NOT_ACTIVE);
				}
			} else {
				jsonObject.put(LOGIN_RESULT, Boolean.FALSE);
			}

			return jsonObject.toString();
		} 
	
}
