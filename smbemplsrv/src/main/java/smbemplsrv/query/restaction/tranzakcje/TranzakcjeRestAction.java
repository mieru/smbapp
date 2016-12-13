package smbemplsrv.query.restaction.tranzakcje;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import smbemplsrv.facade.tranzakcje.TranzakcjeFacade;

@RequestScoped
@Path("/query/tranzakcje")
public class TranzakcjeRestAction {
	
	@EJB
	TranzakcjeFacade tranzakcjeFacade;

	
	@POST
	@Path("/getTranzakcje")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTranzakcje(TranzakcjeRequestQueryData tranzakcjeRequestQueryData) throws JSONException {
		return tranzakcjeFacade.getTranzakcje(tranzakcjeRequestQueryData);
	}
	
	@POST
	@Path("/getTranzakcjaById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getTranzakcjaById(TranzakcjeRequestQueryData tranzakcjeRequestQueryData) throws JSONException {
			return tranzakcjeFacade.getTranzakcjaById(tranzakcjeRequestQueryData);
	}
}
