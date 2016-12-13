package smbemplsrv.command.ejbcontrol.zamowienie;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.MailKonf;
import smbemplsrv.dbmodel.ZamowienieSprzedaz;
import smbemplsrv.dbmodel.Zgloszenie;

@Stateless
@LocalBean
public class ZamowienieEjbCommandController extends AbstractEjbCommandController<ZamowienieSprzedaz> {
	
}
