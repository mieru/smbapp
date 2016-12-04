package query.ejbcontrol.zgloszenia;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.Zgloszenie;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class ZgloszeniaEjbQueryController extends AbstractEjbQueryController<Zgloszenie> {

	public ZgloszeniaEjbQueryController(){
		this.dbEntity = Zgloszenie.class;
	}
	
	
	public String generujNumerZgloszenia() {
		Integer id = entityManager.createQuery("select max(z.id) from Zgloszenie z", Integer.class).getSingleResult();
		if(id == null)
			id = 0;
		id++;
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
		return dateFormat.format(new Date(System.currentTimeMillis())) + "/" + id;
	}

	
}