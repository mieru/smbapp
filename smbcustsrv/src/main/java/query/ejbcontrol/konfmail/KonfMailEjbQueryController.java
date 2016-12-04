package query.ejbcontrol.konfmail;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.MailKonf;
import query.ejbcontrol.abst.AbstractEjbQueryController;


@Stateless
@LocalBean
public class KonfMailEjbQueryController  extends AbstractEjbQueryController<MailKonf> {

	public KonfMailEjbQueryController(){
		this.dbEntity = MailKonf.class;
	}
}
