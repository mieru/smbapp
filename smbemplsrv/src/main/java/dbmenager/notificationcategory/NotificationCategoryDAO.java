package dbmenager.notificationcategory;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.NotificationCategory;



@Stateless
@LocalBean
public class NotificationCategoryDAO  extends AbstractDAO<NotificationCategory> {

	public NotificationCategoryDAO(){
		this.dbEntity = NotificationCategory.class;
	}
}
