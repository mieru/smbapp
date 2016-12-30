package businesslogic.login;

import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.users.UsersDAO;
import dbmodel.Users;
import restapi.login.LoginRestData;
import utils.status.Status;

@Stateless
@LocalBean
public class LoginLogicController {
	private final String LOGIN_RESULT = "login_result";
	private final String NOT_ACTIVE = "not_active";
	private final String ID_USER = "id_user";
	private final String ID_ADM = "isAdm";
	private final String ID_PRAC = "isPrac";
	
	@EJB
	UsersDAO usersDAO;
	
	public String checkLoginData(LoginRestData loginRestData) throws JSONException{
			JSONObject jsonObject = new JSONObject();
			
			Users user = new Users();
			user.setLogin(loginRestData.username);
			user.setPassword(new String(Base64.getEncoder().encode(loginRestData.password.getBytes())));
			List<Users> userList = usersDAO.findEntity(user);
			
			if (userList.size() == 1 && userList.iterator().next().getRole().contains("E")) {
				user = userList.iterator().next();
				if (user.getState().equals(Status.USER_STATE.ACTIVE)) {
					jsonObject.put(LOGIN_RESULT, Boolean.TRUE);
					jsonObject.put(ID_USER, user.getId());
					jsonObject.put(ID_ADM, user.getRole().contains("A"));
					jsonObject.put(ID_PRAC, user.getRole().contains("E"));
				} else {
					jsonObject.put(LOGIN_RESULT, NOT_ACTIVE);
				}
			} else {
				jsonObject.put(LOGIN_RESULT, Boolean.FALSE);
			}

			return jsonObject.toString();
		} 
	
}
