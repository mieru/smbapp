package query.ejbcontrol.uzytkownik;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.Uzytkownik;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class UzytkownikEjbQueryController extends AbstractEjbQueryController<Uzytkownik> {

	public UzytkownikEjbQueryController(){
		this.dbEntity = Uzytkownik.class;
	}
	
	public boolean isLoginExist(String login) {
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setLogin(login);
		List<Uzytkownik> checkLogin = findEntity(uzytkownik);
		return checkLogin.size() > 0;
	}
}
