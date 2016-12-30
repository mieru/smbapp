package businesslogic.register;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.user.UserDAO;
import dbmodel.Users;
import mailmenager.MailSender;
import restapi.register.RegisterRestData;
import utils.SmallSmbUtils;
import utils.Status;

@Stateless
@LocalBean
public class RegisterLogicController {
	@EJB
	UserDAO userDAO;
	
	@EJB
	MailSender mailSender;
	
	public String registerUser(RegisterRestData registerRestData) throws JSONException, AddressException, MessagingException{
		JSONObject jsonObject = new JSONObject();
		
		Users user = fillUserByRequest(registerRestData);
		if(userDAO.isLoginExist(user.getLogin())){
			jsonObject.put("ERROR", "Podany login jest zajęty.");
		}else{
			user = userDAO.insert(user);
			mailSender.sendActivationMail(user, registerRestData.url);
		}
		
		return jsonObject.toString();
	}
	
	public String activateAccount(RegisterRestData registerRestData) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			Integer idUser = SmallSmbUtils.decodeInteger(registerRestData.idEncoded);
			Users user = userDAO.findEntityByID(idUser);
			userDAO.activateAccount(user);
			jsonObject.put("response", "OK");
		} catch (Exception e) {
			jsonObject.put("response", "ERROR");
			jsonObject.put("description", e.getMessage());
		}
		
		return jsonObject.toString();
		
	}
	
	private Users fillUserByRequest(RegisterRestData json){
		Users user = new Users();
		user.setLogin(json.username);
		user.setPassword(SmallSmbUtils.encodeString(json.password));
		user.setName(json.name);
		user.setSurname(json.surname);
		user.setCompanyName(json.companyName);
		user.setNip(json.nip);
		user.setEmail(json.email);
		user.setPhone(json.phone);
		user.setStreet(json.street);
		user.setBuildingNumber(json.buildingNumber);
		user.setApartamentNumber(json.flatNumber);
		user.setCity(json.city);
		user.setPostCode(json.postCode);
		user.setUserType(json.customerType);
		user.setRole(Status.USER_ROLE.CUSTOMER);
		user.setState(Status.USER_STATE.NEW);
		return user;
	}
	
}
