package query.restaction.oferta;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import facade.oferta.OfertaFacade;

@RequestScoped
@Path("/query/oferta")
public class OfertaRestAction {

	@EJB
	OfertaFacade ofertaFacade;

	@POST
	@Path("/getOferta")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(OfertaRequestData ofertaRequestData) throws JSONException, IOException {
		return ofertaFacade.getOferta(ofertaRequestData);
	}
	
}
