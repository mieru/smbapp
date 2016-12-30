package dbmenager.notyficationmessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.NotificationMessage;

@Stateless
@LocalBean
public class NotificationMessageDAO extends AbstractDAO<NotificationMessage> {
	
	public NotificationMessageDAO(){
		this.dbEntity = NotificationMessage.class;
	}
	
	
}
