package query.ejbcontrol.zamowieniasprzedarz;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmodel.ZamowienieSprzedaz;
import query.ejbcontrol.abst.AbstractEjbQueryController;

@Stateless
@LocalBean
public class ZamowieniaSprzedEjbQueryController extends AbstractEjbQueryController<ZamowienieSprzedaz> {

	public ZamowieniaSprzedEjbQueryController(){
		this.dbEntity = ZamowienieSprzedaz.class;
	}
	
	public String generujNumerZgloszenia() {
		Integer id = entityManager.createQuery("select max(z.id) from ZamowienieSprzedaz z", Integer.class).getSingleResult();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
		return dateFormat.format(new Date(System.currentTimeMillis())) + "/" + ++id;
	}
	
}