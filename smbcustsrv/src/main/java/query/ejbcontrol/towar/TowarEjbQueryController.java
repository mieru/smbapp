package query.ejbcontrol.towar;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.Towar;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class TowarEjbQueryController extends AbstractEjbQueryController<Towar> {

	public TowarEjbQueryController(){
		this.dbEntity = Towar.class;
	}
}