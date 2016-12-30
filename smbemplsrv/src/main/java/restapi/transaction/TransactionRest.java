package restapi.transaction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import com.lowagie.text.DocumentException;

import businesslogic.sale.SaleLogicController;
import businesslogic.transaction.TransactionLogicController;

@RequestScoped
@Path("/transaction")
public class TransactionRest {
	
	@EJB
	TransactionLogicController transactionLogicController;

	@EJB
	SaleLogicController saleLogicController;
	
	@POST
	@Path("/getTransactions")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTransactions(TransactionRestData transactionRestData) throws JSONException {
		return transactionLogicController.getTransactions(transactionRestData);
	}
	
	@POST
	@Path("/getTransactionById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTransactionById(TransactionRestData transactionRestData) throws JSONException {
			return transactionLogicController.getTransactionById(transactionRestData);
	}
	
	@POST
	@Path("/addNew")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNew(TransactionRestData transactionRestData) throws AddressException, MessagingException, FileNotFoundException, IOException, DocumentException, SQLException {
		return saleLogicController.addNew(transactionRestData, null);
	}
}
