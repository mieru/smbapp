package smbemplsrv.query.ejbcontrol.danefirmy;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class DaneFimyEjbQueryController  extends AbstractEjbQueryController<DaneFirmy> {

	public DaneFimyEjbQueryController(){
		this.dbEntity = DaneFirmy.class;
	}
}
