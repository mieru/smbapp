package query.ejbcontrol.zgloszenia;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.Zgloszenie;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class ZgloszeniaEjbQueryController extends AbstractEjbQueryController<Zgloszenie> {

	public ZgloszeniaEjbQueryController(){
		this.dbEntity = Zgloszenie.class;
	}
}