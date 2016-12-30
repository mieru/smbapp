package businesslogic.transaction;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;

import dbmenager.commodity.CommodityDAO;
import dbmenager.companyinfo.CompanyInfoDAO;
import dbmenager.invoice.InvoiceDAO;
import dbmenager.receipt.ReceiptDAO;
import dbmenager.transaction.TransactionDAO;
import dbmenager.users.UsersDAO;
import dbmodel.Transaction;
import dbmodel.Users;
import restapi.transaction.TransactionRestData;
import utils.status.Status;

@Stateless
@LocalBean
public class TransactionLogicController {

	@EJB
	CompanyInfoDAO companyInfoDAO;
	@EJB
	CommodityDAO commodityDAO;
	@EJB
	UsersDAO usersDAO;
	@EJB
	TransactionDAO transactionDAO;
	@EJB
	InvoiceDAO invoiceDAO;
	@EJB
	ReceiptDAO receiptDAO;
 	
	public String getTransactions(TransactionRestData transactionRestData) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Transaction> transactions = null;
		if(transactionRestData.idUser != null){
			Users user = usersDAO.findEntityByID(transactionRestData.idUser);
			transactions = user.getTransactions();
		}else{
			transactions = transactionDAO.findAll();
		}
		for (Transaction transaction : transactions) {
			jsonObject = new JSONObject();
			jsonObject.put("id", transaction.getId());
			jsonObject.put("typ", Status.FAKTURA_STATE.getText(transaction.getType()));
			if(transaction.getInvoice() != null)
				jsonObject.put("nr_faktury", transaction.getInvoice().getInvoiceNumber());
			jsonObject.put("dataWystawienia", transaction.getTranzactionDate().getTime());
			jsonObject.put("kwota", transaction.getSum());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}


	public String getTransactionById(TransactionRestData transactionRestData) {
		JSONObject jsonObject = null;
		
		Transaction transaction = transactionDAO.findEntityByID(transactionRestData.idTranzakcji);
			jsonObject = new JSONObject();
			jsonObject.put("id", transaction.getId());
			jsonObject.put("dataWystawienia", transaction.getTranzactionDate().getTime());
			jsonObject.put("kwota", transaction.getSum());
			
		return jsonObject.toString();
	}



}
