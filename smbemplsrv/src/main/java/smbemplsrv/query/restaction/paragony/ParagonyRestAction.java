package smbemplsrv.query.restaction.paragony;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import smbemplsrv.facade.paragon.ParagonFacade;


@RequestScoped
@Path("/query/paragon")
public class ParagonyRestAction {
	
	@EJB
	ParagonFacade paragonFacade;

	@POST
	@Path("/getParagony")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getFaktury(ParagonyResponseData fakturyResponseData) throws JSONException {
		return paragonFacade.getParagonyUzytkownika(fakturyResponseData);
	}
	
	@POST
	@Path("/getParagonById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String geFakturaById(ParagonyResponseData fakturyResponseData) throws JSONException {
			return paragonFacade.getParagonById(fakturyResponseData);
	}
}
