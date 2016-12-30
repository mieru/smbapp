package businesslogic.user;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.user.UserDAO;
import dbmodel.Users;
import restapi.user.UserRestData;
import utils.SmbJSONUtils;

@Stateless
@LocalBean
public class UserLogicController {
	
	@EJB
	UserDAO userMenager;
	
	public String getUsersById(UserRestData userRestData) throws JSONException {
		Users user = userMenager.findEntityByID(userRestData.idUser);
		JSONObject jsonObject = new JSONObject();
		if(user != null){
			jsonObject =new JSONObject();
			jsonObject.put("id_uzytkownika", user.getId());
			jsonObject.put("name", user.getName());
			jsonObject.put("surname", user.getSurname());
			jsonObject.put("email", user.getEmail());
			jsonObject.put("phone", user.getPhone());
			jsonObject.put("street", user.getStreet());
			jsonObject.put("number_house", user.getBuildingNumber());
			jsonObject.put("number_flat", user.getApartamentNumber());
			jsonObject.put("city", user.getCity());
			jsonObject.put("post_code", user.getPostCode());
			jsonObject.put("nip", user.getNip());
			jsonObject.put("company_name", user.getCompanyName());
		}

		return jsonObject.toString();
	}
	
	public String saveUsersData(UserRestData userRestData) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		Users user = null;
		if(userRestData.idUser != null){
			user = userMenager.findEntityByID(userRestData.idUser);
		}
		
		if(user == null){
			jsonObject.put("ERROR", "Nie znaleziono uzytkownika o id = " + userRestData.idUser);
		}else{
			fillUsers(user, userRestData);
			userMenager.update(user);
			jsonObject.put("OK", "OK");
		}
		return jsonObject.toString();
	}
	
	private void fillUsers(Users user, UserRestData userRestData){
		user.setName(userRestData.name);
		user.setSurname(userRestData.surname);
		user.setCompanyName(userRestData.companyName);
		user.setNip(userRestData.nip);
		user.setEmail(userRestData.email);
		user.setPhone(userRestData.phone);
		user.setStreet(userRestData.street);
		user.setBuildingNumber(userRestData.buildingNumber);
		user.setApartamentNumber(userRestData.flatNumber);
		user.setCity(userRestData.city);
		user.setPostCode(userRestData.postCode);
	}
}
