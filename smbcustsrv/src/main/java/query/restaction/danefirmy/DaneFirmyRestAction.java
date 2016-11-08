package query.restaction.danefirmy;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.DaneFirmy;
import query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;


@RequestScoped
@Path("/query/danefirmy")
public class DaneFirmyRestAction {
	@Context
	private UriInfo uri;

	
	@EJB
	DaneFimyEjbQueryController daneFimyEjbQueryController; 
	
	@POST
	@Path("/getDaneFirmy")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienia() throws JSONException {
			DaneFirmy dane = daneFimyEjbQueryController.findAll().iterator().next();
		
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", dane.getIdDanychFimy());
			jsonObject.put("nazwa", dane.getNazwa());
			jsonObject.put("nip", dane.getNip());
			jsonObject.put("regon", dane.getRegon());
			jsonObject.put("krs", dane.getKrs());
			jsonObject.put("ulica", dane.getUlica());
			jsonObject.put("nr_budynku", dane.getNrBudynku());
			jsonObject.put("nr_lokalu", dane.getNrLokalu());
			jsonObject.put("kod_pocztowy", dane.getKodPocztowy());
			jsonObject.put("miasto", dane.getMiasto());
		
		return jsonObject.toString();
	}
}