package smbemplsrv.command.ejbcontrol.zamowieniekomentarz;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.WiadomoscZamSprzedaz;
import smbemplsrv.dbmodel.ZgloszenieKomentarz;

@Stateless
@LocalBean
public class ZamowienieKomentarzEjbCommandController extends AbstractEjbCommandController<WiadomoscZamSprzedaz> {
	
}
