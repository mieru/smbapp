package smbemplsrv.command.ejbcontrol.uzytkownicy;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.Uzytkownik;

@Stateless
@LocalBean
public class UzytkownicyEjbCommandController extends AbstractEjbCommandController<Uzytkownik> {
	
}
