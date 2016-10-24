package command.restaction.register;

import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import dbmodel.Uzytkownik;
import mailer.MailSender;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;

@RequestScoped
@Path("/")
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
		String activationLink = uri.getBaseUri().toString() + "activeAccount?code=" + new String(encodedBytes);

		MailSender mailSender = new MailSender();
		String subject = "Link aktywacyjny";
		mailSender.sendMail(subject, activationLink, json.email);
		
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toString();
	}
	
	@GET
	@Path("/activeAccount")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String activeAccount(@QueryParam("code") String encodeId) throws JSONException {
		byte[] decodedBytes = Base64.getDecoder().decode(encodeId);
		Integer idUser = Integer.valueOf(new String(decodedBytes));
		Uzytkownik user = uzytkownikEjbQueryController.findEntityByID(idUser);
		user.setState("A");
		uzytkownikEjbCommandController.update(user);
		System.out.println("Konto aktywowane");
		JSONObject jsonObject = new JSONObject();
		return jsonObject.toString();
	}
	
}
