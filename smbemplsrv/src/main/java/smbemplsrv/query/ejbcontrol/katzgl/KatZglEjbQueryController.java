package smbemplsrv.query.ejbcontrol.katzgl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class KatZglEjbQueryController  extends AbstractEjbQueryController<KategoiaZgloszenia> {

	public KatZglEjbQueryController(){
		this.dbEntity = KategoiaZgloszenia.class;
	}
}
