package command.restaction.register;

import java.util.Base64;
import java.util.List;

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
import mailer.MailSender;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import utils.status.Status;

@RequestScoped
@Path("/command/register")
public class RegisterRestAction {

	@EJB
	UzytkownikEjbCommandController uzytkownikEjbCommandController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(RegisterJsonData json) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Uzytkownik uzytkownik =	null;
		if(isLoginExist(json.username)){
			jsonObject.put("ERROR", "Podany login jest zajęty.");
		}else{
			uzytkownik = fillUzytkownikByRequest(json);
			uzytkownik = uzytkownikEjbCommandController.insert(uzytkownik);
		}
	
		if(uzytkownik.getIdUser() != null){
			sendActivationMail(uzytkownik, json.activationUri);
		}
		return jsonObject.toString();
	}
	
	private void sendActivationMail(Uzytkownik uzytkownik, String activationUri) throws AddressException, MessagingException{
		byte[] encodedBytes = Base64.getEncoder().encode(uzytkownik.getIdUser().toString().getBytes());
		String activationLink = activationUri.substring(0, activationUri.indexOf("rejestracja")) + "activationAccount?code=" + new String(encodedBytes);

		MailSender mailSender = new MailSender();
		String subject = "Link aktywacyjny";
		mailSender.sendMail(subject, activationLink, uzytkownik.getMail());
	}
	
	private boolean isLoginExist(String login) {
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setLogin(login);
		List<Uzytkownik> checkLogin = uzytkownikEjbQueryController.findEntity(uzytkownik);
		return checkLogin.size() > 0;
	}

	private Uzytkownik fillUzytkownikByRequest(RegisterJsonData json){
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setLogin(json.username);
		uzytkownik.setPassword(new String(Base64.getEncoder().encode(json.password.getBytes())));
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
		uzytkownik.setRole("C");
		uzytkownik.setState("R");
		return uzytkownik;
	}
	
	@POST
	@Path("/activeAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String activeAccount(RegisterJsonData json) throws JSONException {
		byte[] decodedBytes = Base64.getDecoder().decode(json.idEncoded);
		Integer idUser = Integer.valueOf(new String(decodedBytes));
		Uzytkownik user = uzytkownikEjbQueryController.findEntityByID(idUser);
		user.setState(Status.USER_STATE.ACTIVE);
		uzytkownikEjbCommandController.update(user);
		System.out.println("Konto aktywowane");
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toString();
	}
	
}
