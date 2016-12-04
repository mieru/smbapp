package smbemplsrv.command.ejbcontrol.katzgl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.command.ejbcontrol.abstr.AbstractEjbCommandController;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.dbmodel.KategoiaZgloszenia;

@Stateless
@LocalBean
public class KatZglEjbCommandController extends AbstractEjbCommandController<KategoiaZgloszenia> {
	
}
