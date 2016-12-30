package restapi.receipt;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import businesslogic.receipt.ReceiptLogicController;


@RequestScoped
@Path("/receipt")
public class ReceiptRestAction {
	
	@EJB
	ReceiptLogicController receiptLogicController;

	@POST
	@Path("/getUserReceipts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUserReceipts(ReceiptRestData receiptRestData) throws JSONException {
		return receiptLogicController.getUserReceipts(receiptRestData);
	}
	
	@POST
	@Path("/getReceiptById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getReceiptById(ReceiptRestData receiptRestData) throws JSONException {
			return receiptLogicController.getReceiptById(receiptRestData);
	}
}
