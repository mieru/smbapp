package smbemplsrv.command.ejbcontrol.magazyn;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.Magazyn;
import smbemplsrv.dbmodel.MailKonf;

@Stateless
@LocalBean
public class MagazynEjbCommandController extends AbstractEjbCommandController<Magazyn> {
	
}
