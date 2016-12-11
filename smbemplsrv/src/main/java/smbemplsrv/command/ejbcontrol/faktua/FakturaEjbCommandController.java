package smbemplsrv.command.ejbcontrol.faktua;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.dbmodel.FakturaSprzedazy;

@Stateless
@LocalBean
public class FakturaEjbCommandController extends AbstractEjbCommandController<FakturaSprzedazy> {
	
}
