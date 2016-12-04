package smbemplsrv.query.ejbcontrol.uzytkownicy;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class UzytkownicyEjbQueryController  extends AbstractEjbQueryController<Uzytkownik> {

	public UzytkownicyEjbQueryController(){
		this.dbEntity = Uzytkownik.class;
	}
}
