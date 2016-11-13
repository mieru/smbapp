package command.restaction.uzytkownik;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import dbmodel.Uzytkownik;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;

@RequestScoped
@Path("/command/uzytkownik")
public class UzytkownikRestAction {
	
	@EJB
	UzytkownikEjbCommandController uzytkownikEjbCommandController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@POST
	@Path("/saveUserData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(UzytkownikJsonData json) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Uzytkownik uzytkownik = null;
		if(json.idUser != null){
			uzytkownik = uzytkownikEjbQueryController.findEntityByID(json.idUser);
		}
		
		if(uzytkownik == null){
			jsonObject.put("ERROR", "Nie znaleziono uzytkownika o id = " + json.idUser);
		}else{
			fillUzytkwnik(uzytkownik, json);
			uzytkownikEjbCommandController.update(uzytkownik);
			jsonObject.put("OK", "OK");
		}
		return jsonObject.toString();
	}
	
	private void fillUzytkwnik(Uzytkownik uzytkownik, UzytkownikJsonData json){
		uzytkownik.setName(json.name);
		uzytkownik.setSurname(json.surname);
		uzytkownik.setCompanyName(json.companyName);
		uzytkownik.setNip(json.nip);
		uzytkownik.setMail(json.email);
		uzytkownik.setPhone(json.phone);
		uzytkownik.setStreet(json.street);
		uzytkownik.setBuildingNumber(json.buildingNumber);
		uzytkownik.setRoomNumber(json.flatNumber);
		uzytkownik.setCity(json.city);
		uzytkownik.setPosCode(json.postCode);
	}
	
}
