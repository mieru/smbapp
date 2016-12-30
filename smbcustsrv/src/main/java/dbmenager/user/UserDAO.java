package dbmenager.user;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Users;
import utils.Status;

@Stateless
@LocalBean
public class UserDAO extends AbstractDAO<Users> {

	public UserDAO() {
		this.dbEntity = Users.class;
	}
	
	public void activateAccount(Users user) {
		user.setState(Status.USER_STATE.ACTIVE);
		update(user);
	}

	public boolean isLoginExist(String login) {
		Users user = new Users();
		user.setLogin(login);
		List<Users> users = findEntity(user);
		if(users.size() > 0){
			return true;
		}
		return false;
	}
	
}
