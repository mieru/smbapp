package dbmenager.notyfication;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Notyfication;

@Stateless
@LocalBean
public class NotificationDAO extends AbstractDAO<Notyfication> {
	
	public NotificationDAO(){
		this.dbEntity = Notyfication.class;
	}
	
	public String generujNumerZgloszenia() {
		Integer id = entityManager.createQuery("select max(z.id) from Notyfication z", Integer.class).getSingleResult();
		if(id == null)
			id = 0;
		id++;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
		return dateFormat.format(new Date(System.currentTimeMillis())) + "/" + id;
	}
	
}
