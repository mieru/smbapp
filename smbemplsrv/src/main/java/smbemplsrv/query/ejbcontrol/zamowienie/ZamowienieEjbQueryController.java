package smbemplsrv.query.ejbcontrol.zamowienie;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.ZamowienieSprzedaz;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class ZamowienieEjbQueryController  extends AbstractEjbQueryController<ZamowienieSprzedaz> {

	public ZamowienieEjbQueryController(){
		this.dbEntity = ZamowienieSprzedaz.class;
	}
}
