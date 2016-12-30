package dbmenager.notyficationcategory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.NotificationCategory;

@Stateless
@LocalBean
public class NotifocationCategoryDAO extends AbstractDAO<NotificationCategory> {

	public NotifocationCategoryDAO(){
		this.dbEntity = NotificationCategory.class;
	}
	
}
