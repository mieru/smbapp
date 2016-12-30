package businesslogic.users;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;

import dbmenager.users.UsersDAO;
import dbmodel.Users;
import restapi.users.UsersRestData;
import utils.SmbUtil;
import utils.status.Status;

@Stateless
@LocalBean
public class UsersLogicController {

	@EJB
	UsersDAO usersDAO;

	public String getUsers(
			UsersRestData usersRestData) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<Users> users = new ArrayList<Users>();
		Users user = new Users();
		user.setRole(usersRestData.type);
		
		
		try {
			if(usersRestData.type.equals("E")){
				users.addAll(usersDAO.findEntity(user));
				user.setRole("E,M");
				users.addAll(usersDAO.findEntity(user));
				user.setRole("E,A,M");
				users.addAll(usersDAO.findEntity(user));
				user.setRole("E,A");
				users.addAll(usersDAO.findEntity(user));
				user.setRole("A");
				users.addAll(usersDAO.findEntity(user));
			}else{
				users.addAll(usersDAO.findEntity(user));
			}
			

			for (Users u : users) {
				jsonObject = new JSONObject();

				jsonObject.put("id_uzytkownika", u.getId());
				jsonObject.put("name", u.getName());
				jsonObject.put("surname", u.getSurname());
				jsonObject.put("login", u.getLogin());
				jsonObject.put("email", u.getEmail());
				jsonObject.put("phone", u.getPhone());
				jsonObject.put("street", u.getStreet());
				jsonObject.put("number_house", u.getBuildingNumber());
				jsonObject.put("number_flat", u.getApartamentNumber());
				jsonObject.put("city", u.getCity());
				jsonObject.put("post_code", u.getPostCode());
				jsonObject.put("nip", u.getNip());
				jsonObject.put("rola", u.getUserType());
				jsonObject.put("upr", Status.USER_ROLE.getText(u.getRole()));
				jsonObject.put("uprKod", u.getRole());
				jsonObject.put("status",
						Status.USER_STATE.getText(u.getState()));
				jsonObject.put("company_name", u.getCompanyName());

				jsonArray.put(jsonObject);
			}

		} catch (Exception e) {
		}
		return jsonArray.toString();

	}

	public String getUserById(UsersRestData uzytkwonikRequestQueryData) {
	JSONObject jsonObject = new JSONObject();
	Users u = usersDAO.findEntityByID(uzytkwonikRequestQueryData.idUzytkownika);
		jsonObject.put("id_uzytkownika", u.getId());
		jsonObject.put("name", u.getName());
		jsonObject.put("surname", u.getSurname());
		jsonObject.put("login", u.getLogin());
		jsonObject.put("email", u.getEmail());
		jsonObject.put("phone", u.getPhone());
		jsonObject.put("street", u.getStreet());
		jsonObject.put("number_house", u.getBuildingNumber());
		jsonObject.put("number_flat", u.getApartamentNumber());
		jsonObject.put("city", u.getCity());
		jsonObject.put("post_code", u.getPostCode());
		jsonObject.put("nip", u.getNip());
		jsonObject.put("rola", u.getUserType());
		jsonObject.put("upr", Status.USER_ROLE.getText(u.getRole()));
		jsonObject.put("uprKod", u.getRole());
		jsonObject.put("status",
				Status.USER_STATE.getText(u.getState()));
		jsonObject.put("company_name", u.getCompanyName());
		jsonObject.put("password", SmbUtil.decodeString(u.getPassword()));

		return jsonObject.toString();
	}

	public String editUser(UsersRestData usersRestData) {
			JSONObject jsonObject = new JSONObject();
			Users user = null;
			if(usersRestData.idUser != null){
				user = usersDAO.findEntityByID(usersRestData.idUser);
			}
			
			if(user == null){
				jsonObject.put("ERROR", "Nie znaleziono uzytkownika o id = " + usersRestData.idUser);
			}else{
				fillUser(user, usersRestData);
				usersDAO.update(user);
				jsonObject.put("OK", "OK");
			}
			return jsonObject.toString();
		}
		
		private void fillUser(Users user, UsersRestData usersRestData){
			user.setName(usersRestData.name);
			if(usersRestData.status.length() > 1){
				user.setState(Status.USER_STATE.ACTIVE);
			}else{
				user.setState(usersRestData.status);
			}
			user.setSurname(usersRestData.surname);
			user.setUserType(usersRestData.customerType);
			user.setCompanyName(usersRestData.companyName);
			user.setNip(usersRestData.nip);
			user.setEmail(usersRestData.email);
			user.setPhone(usersRestData.phone);
			user.setStreet(usersRestData.street);
			user.setBuildingNumber(usersRestData.buildingNumber);
			user.setApartamentNumber(usersRestData.flatNumber);
			user.setCity(usersRestData.city);
			user.setPostCode(usersRestData.postCode);
			user.setRole(usersRestData.uprKod);
			user.setLogin(usersRestData.login);
			user.setPassword(SmbUtil.encodeString(usersRestData.password));
		}

		public String deleteUser(UsersRestData usersRestData) {
			Users user = usersDAO.findEntityByID(usersRestData.idUser);
			usersDAO.delete(user);
			return "";
		}

		public String addUser(UsersRestData usersRestData) {
			Users user = new Users();
			fillUser(user,usersRestData);
			usersDAO.insert(user);
			return "";
		}

}
