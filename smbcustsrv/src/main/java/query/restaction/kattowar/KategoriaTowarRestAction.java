package query.restaction.kattowar;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.KategoriaTowar;
import query.ejbcontrol.kategoriatowar.KategoriaTowarEjbQueryController;

	@RequestScoped
	@Path("/query/kattowar")
	public class KategoriaTowarRestAction {
		private static final String PN_NAZWA = "nazwa";
		private static final String PN_ID = "id";

		@Context
		private UriInfo uri;

		@EJB
		KategoriaTowarEjbQueryController kategoriaTowarEjbQueryController;

		@POST
		@Path("/getAll")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public String getAll() throws JSONException {
			JSONArray jsonArray = new JSONArray();

			List<KategoriaTowar> katCol = kategoriaTowarEjbQueryController.findAll();
			
			JSONObject jsonObject = null;
			for (KategoriaTowar kategoriaTowar : katCol) {
				jsonObject = new JSONObject();
				jsonObject.put(PN_NAZWA, kategoriaTowar.getNazwa());
				jsonObject.put(PN_ID, kategoriaTowar.getIdKatTowar());
				jsonArray.put(jsonObject);
			}
			return jsonArray.toString();
		}
}
