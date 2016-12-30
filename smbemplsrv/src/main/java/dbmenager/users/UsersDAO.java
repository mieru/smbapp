package dbmenager.users;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Users;



@Stateless
@LocalBean
public class UsersDAO  extends AbstractDAO<Users> {

	public UsersDAO(){
		this.dbEntity = Users.class;
	}
}
