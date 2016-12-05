package smbemplsrv.query.ejbcontrol.tranzakcje;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Tranzakcje;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;

@Stateless
@LocalBean
public class TranzakcjeEjbQueryController extends AbstractEjbQueryController<Tranzakcje> {
	
	public TranzakcjeEjbQueryController(){
		this.dbEntity = Tranzakcje.class;
	}
}
