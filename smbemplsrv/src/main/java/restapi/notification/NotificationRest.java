package restapi.notification;

import java.io.IOException;

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

import businesslogic.zgloszenia.NotyficationLogicController;

@RequestScoped
@Path("/notification")
public class NotificationRest {
	
	@EJB
	NotyficationLogicController notyficationLogicController;

	@POST
	@Path("/getNotyfication")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getNotyfication(NotificationRestData notificationRestData) throws JSONException, AddressException, MessagingException, IOException {
		return notyficationLogicController.getNotyfication(notificationRestData);
	}
	
	@POST
	@Path("/getNotificationById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getNotificationById(NotificationRestData notificationRestData) throws JSONException, AddressException, MessagingException {
		return  notyficationLogicController.getNotificationById(notificationRestData);
	}
	
	@POST
	@Path("/addMessageToNotification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessageToNotification(NotificationRestData notificationRestData) throws AddressException, MessagingException {
		return notyficationLogicController.addMessageToNotification(notificationRestData);
	}
	
	@POST
	@Path("/closeNotification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String closeNotification(NotificationRestData notificationRestData) throws AddressException, MessagingException {
		return notyficationLogicController.closeNotification(notificationRestData);
	}
}
 