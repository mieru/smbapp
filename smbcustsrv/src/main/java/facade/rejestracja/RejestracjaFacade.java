package facade.rejestracja;

import java.util.Base64;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import command.restaction.register.RegisterRequestData;
import dbmodel.Uzytkownik;
import mailer.MailSender;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import utils.SmbUtil;
import utils.status.Status;

@Stateless
@LocalBean
public class RejestracjaFacade {
	@EJB
	UzytkownikEjbCommandController uzytkownikEjbCommandController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	public String rejestrujUzytkownika(RegisterRequestData registerRequestData) throws JSONException, AddressException, MessagingException{
		JSONObject jsonObject = new JSONObject();
		MailSender mailSender = new MailSender();
		
		Uzytkownik uzytkownik = fillUzytkownikByRequest(registerRequestData);
		if(uzytkownikEjbQueryController.isLoginExist(uzytkownik.getLogin())){
			jsonObject.put("ERROR", "Podany login jest zajęty.");
		}else{
			uzytkownik = uzytkownikEjbCommandController.insert(uzytkownik);
			mailSender.sendActivationMail(uzytkownik, registerRequestData.url);
		}
		
		return jsonObject.toString();
	}
	
	public String aktywujKonto(RegisterRequestData registerRequestData) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		try {
			Integer idUser = SmbUtil.decodeInteger(registerRequestData.idEncoded);
			Uzytkownik user = uzytkownikEjbQueryController.findEntityByID(idUser);
			uzytkownikEjbCommandController.aktywujKonto(user);
			jsonObject.put("response", "OK");
		} catch (Exception e) {
			jsonObject.put("response", "ERROR");
			jsonObject.put("description", e.getMessage());
		}
		
		return jsonObject.toString();
		
	}
	
	private Uzytkownik fillUzytkownikByRequest(RegisterRequestData json){
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setLogin(json.username);
		uzytkownik.setPassword(SmbUtil.encodeString(json.password));
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
		uzytkownik.setCustomerType(json.customerType);
		uzytkownik.setRole(Status.USER_ROLE.CUSTOMER);
		uzytkownik.setState(Status.USER_STATE.NEW);
		return uzytkownik;
	}
	
}
