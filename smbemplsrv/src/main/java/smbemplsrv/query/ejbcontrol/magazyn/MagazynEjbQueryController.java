package smbemplsrv.query.ejbcontrol.magazyn;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Magazyn;
import smbemplsrv.dbmodel.MailKonf;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;

@Stateless
@LocalBean
public class MagazynEjbQueryController extends AbstractEjbQueryController<Magazyn> {
	
	public MagazynEjbQueryController(){
		this.dbEntity = Magazyn.class;
	}
}
