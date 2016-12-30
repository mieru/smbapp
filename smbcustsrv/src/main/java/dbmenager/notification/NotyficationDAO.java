package dbmenager.notification;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Notyfication;


@Stateless
@LocalBean
public class NotyficationDAO extends AbstractDAO<Notyfication> {

	public NotyficationDAO(){
		this.dbEntity = Notyfication.class;
	}
}