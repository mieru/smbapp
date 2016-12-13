package smbemplsrv.query.ejbcontrol.zamowieniekomentarz;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.WiadomoscZamSprzedaz;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class ZamowienieKomentarzEjbQueryController  extends AbstractEjbQueryController<WiadomoscZamSprzedaz> {

	public ZamowienieKomentarzEjbQueryController(){
		this.dbEntity = WiadomoscZamSprzedaz.class;
	}
}
