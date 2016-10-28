package command.restaction.register;

import java.util.Base64;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;

import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import dbmodel.Uzytkownik;
import mailer.MailSender;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;

@RequestScoped
@Path("/command/register")
public class RegisterRestAction {

	 @Context
	 private UriInfo uri;
	
	@EJB
	UzytkownikEjbCommandController uzytkownikEjbCommandController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@POST
	@Path("/registerUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkLoginData(RegisterJsonData json) throws JSONException, AddressException, MessagingException {
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setLogin(json.username);
		uzytkownik.setPassword(json.password);
		uzytkownik.setRole("C");
		uzytkownik.setMail(json.email);
		uzytkownik.setState("R");
		uzytkownik = uzytkownikEjbCommandController.insert(uzytkownik);
		
		byte[] encodedBytes = Base64.getEncoder().encode(uzytkownik.getIdUser().toString().getBytes());
		String activationLink = json.activationUri.substring(0, json.activationUri.indexOf("rejestracja")) + "activationAccount?code=" + new String(encodedBytes);

		MailSender mailSender = new MailSender();
		String subject = "Link aktywacyjny";
		mailSender.sendMail(subject, activationLink, json.email);
		
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toString();
	}
	
	@POST
	@Path("/activeAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String activeAccount(RegisterJsonData json) throws JSONException {
		byte[] decodedBytes = Base64.getDecoder().decode(json.idEncoded);
		Integer idUser = Integer.valueOf(new String(decodedBytes));
		Uzytkownik user = uzytkownikEjbQueryController.findEntityByID(idUser);
		user.setState("A");
		uzytkownikEjbCommandController.update(user);
		System.out.println("Konto aktywowane");
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toString();
	}
	
}
