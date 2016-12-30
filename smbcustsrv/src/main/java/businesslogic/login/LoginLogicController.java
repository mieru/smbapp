package businesslogic.login;

import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.user.UserDAO;
import dbmodel.Users;
import restapi.login.LoginRestData;
import utils.Status;

@Stateless
@LocalBean
public class LoginLogicController {
	private final String LOGIN_RESULT = "login_result";
	private final String NOT_ACTIVE = "not_active";
	private final String ID_USER = "id_user";
	
	@EJB
	UserDAO userDAO;
	
	public String checkLoginData(LoginRestData loginRestData) throws JSONException{
			JSONObject jsonObject = new JSONObject();
			
			Users user = new Users();
			user.setLogin(loginRestData.username);
			user.setPassword(new String(Base64.getEncoder().encode(loginRestData.password.getBytes())));
			List<Users> userList = userDAO.findEntity(user);
			
			if (userList.size() == 1 && userList.iterator().next().getRole().contains(Status.USER_ROLE.CUSTOMER)) {
				user = userList.iterator().next();
				if (user.getState().equals(Status.USER_STATE.ACTIVE)) {
					jsonObject.put(LOGIN_RESULT, Boolean.TRUE);
					jsonObject.put(ID_USER, user.getId());
				} else {
					jsonObject.put(LOGIN_RESULT, NOT_ACTIVE);
				}
			} else {
				jsonObject.put(LOGIN_RESULT, Boolean.FALSE);
			}

			return jsonObject.toString();
		}
	
}
