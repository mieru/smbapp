package smbemplsrv.command.ejbcontrol.katprod;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.KategoriaTowar;

@Stateless
@LocalBean
public class KatProdEjbCommandController extends AbstractEjbCommandController<KategoriaTowar> {
	
}
