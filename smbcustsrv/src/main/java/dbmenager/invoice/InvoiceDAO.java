package dbmenager.invoice;

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
}