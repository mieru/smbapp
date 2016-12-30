package dbmenager.transaction;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import dbmenager.abstr.AbstractDAO;
import dbmodel.Transaction;

@Stateless
@LocalBean
public class TransactionDAO extends AbstractDAO<Transaction> {
	
	public TransactionDAO(){
		this.dbEntity = Transaction.class;
	}
}
