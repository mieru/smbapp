package query.ejbcontrol.kategoriatowar;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.KategoriaTowar;
import query.ejbcontrol.abst.AbstractEjbQueryController;


@Stateless
@LocalBean
public class KategoriaTowarEjbQueryController  extends AbstractEjbQueryController<KategoriaTowar> {

	public KategoriaTowarEjbQueryController(){
		this.dbEntity = KategoriaTowar.class;
	}
}
