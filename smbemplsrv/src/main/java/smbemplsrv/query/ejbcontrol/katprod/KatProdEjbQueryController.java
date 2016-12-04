package smbemplsrv.query.ejbcontrol.katprod;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.KategoriaTowar;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class KatProdEjbQueryController  extends AbstractEjbQueryController<KategoriaTowar> {

	public KatProdEjbQueryController(){
		this.dbEntity = KategoriaTowar.class;
	}
}
