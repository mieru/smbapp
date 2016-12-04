package smbemplsrv.command.ejbcontrol.produkt;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.Towar;

@Stateless
@LocalBean
public class ProduktEjbCommandController extends AbstractEjbCommandController<Towar> {
	
}
