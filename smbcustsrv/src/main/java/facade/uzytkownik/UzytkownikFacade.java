package facade.uzytkownik;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import command.restaction.uzytkownik.UzytkownikRequestCommandData;
import dbmodel.Uzytkownik;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.restaction.uzytkownik.UzytkwonikRequestQueryData;

@Stateless
@LocalBean
public class UzytkownikFacade {
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@EJB
	UzytkownikEjbCommandController uzytkownikEjbCommandController;
	
	
	public String getUzytkownikaById(UzytkwonikRequestQueryData uzytkwonikRequestQueryData) throws JSONException {
		Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(uzytkwonikRequestQueryData.idUser);
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
	
	public String poprawDaneUzytkownika(UzytkownikRequestCommandData uzytkownikRequestData) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		Uzytkownik uzytkownik = null;
		if(uzytkownikRequestData.idUser != null){
			uzytkownik = uzytkownikEjbQueryController.findEntityByID(uzytkownikRequestData.idUser);
		}
		
		if(uzytkownik == null){
			jsonObject.put("ERROR", "Nie znaleziono uzytkownika o id = " + uzytkownikRequestData.idUser);
		}else{
			fillUzytkwnik(uzytkownik, uzytkownikRequestData);
			uzytkownikEjbCommandController.update(uzytkownik);
			jsonObject.put("OK", "OK");
		}
		return jsonObject.toString();
	}
	
	private void fillUzytkwnik(Uzytkownik uzytkownik, UzytkownikRequestCommandData uzytkownikRequestData){
		uzytkownik.setName(uzytkownikRequestData.name);
		uzytkownik.setSurname(uzytkownikRequestData.surname);
		uzytkownik.setCompanyName(uzytkownikRequestData.companyName);
		uzytkownik.setNip(uzytkownikRequestData.nip);
		uzytkownik.setMail(uzytkownikRequestData.email);
		uzytkownik.setPhone(uzytkownikRequestData.phone);
		uzytkownik.setStreet(uzytkownikRequestData.street);
		uzytkownik.setBuildingNumber(uzytkownikRequestData.buildingNumber);
		uzytkownik.setRoomNumber(uzytkownikRequestData.flatNumber);
		uzytkownik.setCity(uzytkownikRequestData.city);
		uzytkownik.setPosCode(uzytkownikRequestData.postCode);
	}
}
