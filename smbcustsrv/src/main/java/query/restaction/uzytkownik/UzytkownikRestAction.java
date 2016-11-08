package query.restaction.uzytkownik;

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

import dbmodel.Uzytkownik;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;

@RequestScoped
@Path("/query/uzytkownik")
public class UzytkownikRestAction {
	@Context
	private UriInfo uri;

	@EJB
	UzytkownikEjbQueryController uzytkownikQueryControler;

	@POST
	@Path("/getUzytkownikaById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUzytkownikaById(UzytkwonikJsonData json) throws JSONException {
		Uzytkownik uzytkownik = uzytkownikQueryControler.findEntityByID(json.idUser);
		JSONObject jsonObject = new JSONObject();
		
		if(uzytkownik != null){
			jsonObject.put("id_uzytkownika", uzytkownik.getIdUser());
			jsonObject.put("name", uzytkownik.getName());
			jsonObject.put("surname", uzytkownik.getSurname());
			jsonObject.put("email", uzytkownik.getMail());
			jsonObject.put("phone", uzytkownik.getPhone());
			jsonObject.put("street", uzytkownik.getStreet());
			jsonObject.put("number_house", uzytkownik.getBuildingNumber());
			jsonObject.put("number_flat", uzytkownik.getRoomNumber());
			jsonObject.put("city", uzytkownik.getCity());
			jsonObject.put("post_code", uzytkownik.getPosCode());
			jsonObject.put("nip", uzytkownik.getNip());
			jsonObject.put("company_name", uzytkownik.getCompanyName());
		}

		return jsonObject.toString();
	}
}
