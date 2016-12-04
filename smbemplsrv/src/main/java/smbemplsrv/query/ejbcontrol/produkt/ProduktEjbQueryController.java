package smbemplsrv.query.ejbcontrol.produkt;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.Towar;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;

@Stateless
@LocalBean
public class ProduktEjbQueryController extends AbstractEjbQueryController<Towar> {
	
	public ProduktEjbQueryController(){
		this.dbEntity = Towar.class;
	}
}
