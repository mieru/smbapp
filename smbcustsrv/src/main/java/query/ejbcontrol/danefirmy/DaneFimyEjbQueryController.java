package query.ejbcontrol.danefirmy;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.DaneFirmy;
import query.ejbcontrol.abst.AbstractEjbQueryController;


@Stateless
@LocalBean
public class DaneFimyEjbQueryController  extends AbstractEjbQueryController<DaneFirmy> {

	public DaneFimyEjbQueryController(){
		this.dbEntity = DaneFirmy.class;
	}
}
