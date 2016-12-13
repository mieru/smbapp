package smbemplsrv.query.ejbcontrol.paragon;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Paragon;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;

@Stateless
@LocalBean
public class ParagonEjbQueryController extends AbstractEjbQueryController<Paragon> {
	
	public ParagonEjbQueryController(){
		this.dbEntity = Paragon.class;
	}
}
