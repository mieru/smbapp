package query.ejbcontrol.faktury;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.FakturaSprzedazy;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class FakturyEjbQueryController  extends AbstractEjbQueryController<FakturaSprzedazy> {

	public FakturyEjbQueryController(){
		this.dbEntity = FakturaSprzedazy.class;
	}
}