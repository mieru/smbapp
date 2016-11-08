package query.restaction.oferta;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.KategoriaTowar;
import dbmodel.Towar;
import query.ejbcontrol.kategoriatowar.KategoriaTowarEjbQueryController;
import query.ejbcontrol.towar.TowarEjbQueryController;

@RequestScoped
@Path("/query/oferta")
public class OfertaRestAction {
	@Context
	private UriInfo uri;

	@EJB
	TowarEjbQueryController towarQueryController;
	@EJB
	KategoriaTowarEjbQueryController kategoriaTowarEjbQueryController;

	@POST
	@Path("/getOferta")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(OfertaJsonData json) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		Integer idKat = null;
		KategoriaTowar kategoriaTowar = null;
		List<Towar> towarList = null;
		if(json.kategoria != null){
			idKat = Integer.parseInt(json.kategoria);
			kategoriaTowar = kategoriaTowarEjbQueryController.findEntityByID(idKat);
			towarList = kategoriaTowar.getTowars();
		}else{
			towarList = towarQueryController.findAll(); 
		}
		
		for (Towar t : towarList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lp", towarList.indexOf(t));
			jsonObject.put("nazwa", t.getNazwa());
			jsonObject.put("id", t.getIdTowaru());
			jsonObject.put("image", t.getTowarImage().getImage());
			jsonObject.put("opis", t.getOpis());
			jsonObject.put("cnetto", t.getCenaNetto());
			jsonObject.put("stawka_vat", t.getStawkaVat());
			jsonObject.put("cbrutto", t.getCenaNetto() * (1.00 + t.getStawkaVat() * 0.01));
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
}
