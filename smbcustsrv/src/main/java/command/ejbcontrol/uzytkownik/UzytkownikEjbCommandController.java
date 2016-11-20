package command.ejbcontrol.uzytkownik;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import dbmodel.Uzytkownik;
import utils.status.Status;

@Stateless
@LocalBean
public class UzytkownikEjbCommandController extends AbstractEjbCommandController<Uzytkownik> {

	public void aktywujKonto(Uzytkownik user) {
		user.setState(Status.USER_STATE.ACTIVE);
		update(user);
	}
	
}
