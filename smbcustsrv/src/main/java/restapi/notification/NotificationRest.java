package restapi.notification;

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

import businesslogic.notification.NotificationLogicController;

@RequestScoped
@Path("/zgloszenia")
public class NotificationRest {
	
	@EJB
	NotificationLogicController notificationLogicController;
	
	
	@POST
	@Path("/getKategorie")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getKategorieZglosznia() throws JSONException, AddressException, MessagingException, IllegalArgumentException, IllegalAccessException {
		return notificationLogicController.getNotificationCategory();
	}
	
	@POST
	@Path("/getZgloszeniaUzytkownika")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszeniaUzytkownika(NotificationRestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException, IllegalArgumentException, IllegalAccessException {
		return notificationLogicController.getUserNotifications(zgloszenieRequestData);
	}
	
	@POST
	@Path("/getZgloszenieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszenieById(NotificationRestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException, IllegalArgumentException, IllegalAccessException {
		return notificationLogicController.getNotificationById(zgloszenieRequestData);
	}
	
	@POST
	@Path("/addNewNotification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewNotification(NotificationRestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException {
		return notificationLogicController.dodajZgloszenie(zgloszenieRequestData).toString();
	}
	
	@POST
	@Path("/addMessageToNotification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addMessageToNotification(NotificationRestData zgloszenieRequestData) throws JSONException, AddressException, MessagingException {
		notificationLogicController.dodajWiadomoscDoZgloszenia(zgloszenieRequestData);
	}
	
}
 