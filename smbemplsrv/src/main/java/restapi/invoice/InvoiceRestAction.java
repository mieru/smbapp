package restapi.invoice;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import businesslogic.invoice.InvoiceLogicController;


@RequestScoped
@Path("/invoice")
public class InvoiceRestAction {
	
	@EJB
	InvoiceLogicController invoiceLogicController;

	@GET
	@Path("/getInvoicePdfFile")
	@Produces("application/pdf")
	public Response getInvoicePdfFile(@QueryParam("idFaktury") Integer idFaktury) {
		return invoiceLogicController.getInvoicePdfFile(idFaktury);
	}
	
	@POST
	@Path("/getUsersInvoice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getInvoice(InvoiceRestData invoiceRestData) throws JSONException {
		return invoiceLogicController.getUserInvoices(invoiceRestData);
	}
	
	@POST
	@Path("/getInvoiceById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getInvoiceById(InvoiceRestData fakturyResponseData) throws JSONException {
			return invoiceLogicController.getInvoiceById(fakturyResponseData);
	}
}
