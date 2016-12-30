package restapi.offer;

import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import businesslogic.offer.OfferLogicController;

@RequestScoped
@Path("/oferta")
public class OfertaRestAction {

	@EJB
	OfferLogicController offerLogicController;

	@POST
	@Path("/getOferta")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(OfferRestData offerRestData) throws JSONException, IOException, IllegalArgumentException, IllegalAccessException {
		return offerLogicController.getOffer(offerRestData);
	}
	
}
