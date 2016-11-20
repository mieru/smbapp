package query.restaction.kattowar;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import facade.kategoriatowar.KategoriaTowarFacade;

	@RequestScoped
	@Path("/query/kattowar")
	public class KategoriaTowarRestAction {
		
		@EJB
		KategoriaTowarFacade kategoriaTowarFacade;

		@POST
		@Path("/getAll")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String getAll() throws JSONException {
			return kategoriaTowarFacade.getAll();
		}
}
