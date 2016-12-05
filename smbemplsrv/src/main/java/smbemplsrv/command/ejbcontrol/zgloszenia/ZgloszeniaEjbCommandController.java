package smbemplsrv.command.ejbcontrol.zgloszenia;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.MailKonf;
import smbemplsrv.dbmodel.Zgloszenie;

@Stateless
@LocalBean
public class ZgloszeniaEjbCommandController extends AbstractEjbCommandController<Zgloszenie> {
	
}
