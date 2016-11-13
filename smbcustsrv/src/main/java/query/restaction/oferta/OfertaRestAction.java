package query.restaction.oferta;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
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
	public String checkLoginData(OfertaJsonData json) throws JSONException, IOException {
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
			
			     File filename = new File(System.getProperty("jboss.server.data.dir").toString() + t.getTowarImage().getImage());
			     String base64 = getStringImage(filename);
			
			jsonObject.put("image",base64);
			jsonObject.put("opis", t.getOpis());
			jsonObject.put("cnetto", t.getCenaNetto());
			jsonObject.put("stawka_vat", t.getStawkaVat());
			jsonObject.put("cbrutto", t.getCenaNetto() * (1.00 + t.getStawkaVat() * 0.01));
			jsonObject.put("jednostka", t.getJednostka());
			jsonArray.put(jsonObject);
			
		}
		return jsonArray.toString();
	}
	
	private String getStringImage(File file){
	    try {
	        FileInputStream fin = new FileInputStream(file);
	        byte[] imageBytes = new byte[(int)file.length()];
	        fin.read(imageBytes, 0, imageBytes.length);
	        fin.close();
	        return Base64.getEncoder().encodeToString(imageBytes);
	    } catch (Exception ex) {
	    }
	    return null;
	}
	
}
