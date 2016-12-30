package restapi.commoditycategory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import businesslogic.commoditycategory.CommodityCategoryLogicController;

	@RequestScoped
	@Path("/commoditycategory")
	public class CommodityCategotyRest {
		
		@EJB
		CommodityCategoryLogicController commodityCategoryLogicController;

		@POST
		@Path("/getAll")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String getAll() throws JSONException {
			return commodityCategoryLogicController.getAll();
		}
}
