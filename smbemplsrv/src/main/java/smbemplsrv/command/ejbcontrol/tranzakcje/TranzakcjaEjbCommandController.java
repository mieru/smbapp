package smbemplsrv.command.ejbcontrol.tranzakcje;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.Tranzakcje;

@Stateless
@LocalBean
public class TranzakcjaEjbCommandController extends AbstractEjbCommandController<Tranzakcje> {
	
}
