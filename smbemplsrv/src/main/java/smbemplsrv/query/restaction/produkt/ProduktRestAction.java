package smbemplsrv.query.restaction.produkt;

import java.io.IOException;

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

import smbemplsrv.facade.produkt.ProduktFacade;

@RequestScoped
@Path("/query/produkt")
public class ProduktRestAction {
	
	@EJB
	ProduktFacade produktFacade;

	@POST
	@Path("/getProdukty")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getProdukty(ProduktRequestQueryData produktRequestData) throws JSONException, AddressException, MessagingException, IOException {
		return produktFacade.getProdukty(produktRequestData);
	}
	
	@POST
	@Path("/getProduktById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getProduktById(ProduktRequestQueryData produktRequestData) throws JSONException, AddressException, MessagingException {
		return produktFacade.getProduktById(produktRequestData);
	}
}
 