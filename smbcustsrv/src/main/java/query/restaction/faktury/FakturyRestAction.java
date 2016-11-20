package query.restaction.faktury;

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

import facade.faktura.FakturaFacade;

@RequestScoped
@Path("/query/faktury")
public class FakturyRestAction {
	
	@EJB
	FakturaFacade fakturaFacade;

	@GET
	@Path("/downloadPdf")
	@Produces("application/pdf")
	public Response getTextFile(@QueryParam("idFaktury") Integer idFaktury) {
		return fakturaFacade.getFakturaPdfFile(idFaktury);
	}
	
	@POST
	@Path("/getFaktury")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getFaktury(FakturyResponseData fakturyResponseData) throws JSONException {
		return fakturaFacade.getFakturyUzytkownika(fakturyResponseData);
	}
	
	@POST
	@Path("/getFaktuaById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String geFakturaById(FakturyResponseData fakturyResponseData) throws JSONException {
			return fakturaFacade.getFakturaById(fakturyResponseData);
	}
}
