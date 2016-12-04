package smbemplsrv.command.ejbcontrol.konfmail;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.MailKonf;

@Stateless
@LocalBean
public class KonfMailEjbCommandController extends AbstractEjbCommandController<MailKonf> {
	
}
