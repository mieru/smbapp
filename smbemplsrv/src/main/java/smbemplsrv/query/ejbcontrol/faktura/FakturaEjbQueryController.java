package smbemplsrv.query.ejbcontrol.faktura;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import smbemplsrv.dbmodel.FakturaSprzedazy;
import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;



@Stateless
@LocalBean
public class FakturaEjbQueryController  extends AbstractEjbQueryController<FakturaSprzedazy> {

	public FakturaEjbQueryController(){
		this.dbEntity = FakturaSprzedazy.class;
	}
	
	public String generujNumerZgloszenia() {
		Integer id = entityManager.createQuery("select max(f.id) from FakturaSprzedazy f", Integer.class).getSingleResult();
		if(id == null)
			id = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
		return dateFormat.format(new Date(System.currentTimeMillis())) + "/" + ++id;
	}
	
}
