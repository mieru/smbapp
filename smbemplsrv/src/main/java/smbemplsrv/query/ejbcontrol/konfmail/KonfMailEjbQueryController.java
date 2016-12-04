package smbemplsrv.query.ejbcontrol.konfmail;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.MailKonf;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;

@Stateless
@LocalBean
public class KonfMailEjbQueryController extends AbstractEjbQueryController<MailKonf> {
	
	public KonfMailEjbQueryController(){
		this.dbEntity = MailKonf.class;
	}
}
