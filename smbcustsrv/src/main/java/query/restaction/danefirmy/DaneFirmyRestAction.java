package query.restaction.danefirmy;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import facade.danefirmy.DaneFirmyFacade;


@RequestScoped
@Path("/query/danefirmy")
public class DaneFirmyRestAction {
	
	@EJB
	DaneFirmyFacade daneFirmyFacade; 
	
	@POST
	@Path("/getDaneFirmy")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getDaneFirmy() throws JSONException {
		return daneFirmyFacade.getDaneFirmy();
	}
}