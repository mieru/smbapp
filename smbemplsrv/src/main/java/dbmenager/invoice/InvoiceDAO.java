package dbmenager.invoice;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Invoice;



@Stateless
@LocalBean
public class InvoiceDAO  extends AbstractDAO<Invoice> {

	public InvoiceDAO(){
		this.dbEntity = Invoice.class;
	}
	
	public String generujNumerZgloszenia() {
		Integer id = entityManager.createQuery("select max(f.id) from Invoice f", Integer.class).getSingleResult();
		if(id == null)
			id = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
		return dateFormat.format(new Date(System.currentTimeMillis())) + "/" + ++id;
	}
	
}
