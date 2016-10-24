package query.ejbcontrol.uzytkownik;

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
}
