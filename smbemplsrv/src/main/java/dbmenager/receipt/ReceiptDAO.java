package dbmenager.receipt;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Receipt;

@Stateless
@LocalBean
public class ReceiptDAO extends AbstractDAO<Receipt> {
	
	public ReceiptDAO(){
		this.dbEntity = Receipt.class;
	}
}
