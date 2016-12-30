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
public class InvoiceRest {
	
	@EJB
	InvoiceLogicController invoiceLogicController;

	@GET
	@Path("/downloadPdf")
	@Produces("application/pdf")
	public Response downloadPdf(@QueryParam("idFaktury") Integer invoiceId) {
		return invoiceLogicController.getInvoicePdfFile(invoiceId);
	}
	
	@POST
	@Path("/getInvoices")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getInvoices(InvoiceRestData fakturyResponseData) throws JSONException, IllegalArgumentException, IllegalAccessException {
		return invoiceLogicController.getFakturyUzytkownika(fakturyResponseData);
	}
	
	@POST
	@Path("/getInvoiceById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getInvoiceById(InvoiceRestData fakturyResponseData) throws JSONException, IllegalArgumentException, IllegalAccessException {
			return invoiceLogicController.getFakturaById(fakturyResponseData);
	}
}
