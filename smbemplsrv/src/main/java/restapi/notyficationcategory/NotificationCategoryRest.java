package restapi.notyficationcategory;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import businesslogic.notificationcategory.NotificationCategoryLogicController;

@RequestScoped
@Path("/notificationcategory")
public class NotificationCategoryRest {
	
	@EJB
	NotificationCategoryLogicController notificationCategoryLogicController;
	
	@POST
	@Path("/getNotificationCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getNotificationCategory() throws AddressException, MessagingException {
		return notificationCategoryLogicController.getNotificationCategory();
	}
	
	@POST
	@Path("/addNewCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewCategory(NotyficationCategoryRestData notyficationCategoryRestData) throws AddressException, MessagingException {
		return notificationCategoryLogicController.addNewCategory(notyficationCategoryRestData);
	}
	
	@POST
	@Path("/deleteCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteCategory(NotyficationCategoryRestData katZglRequestData) throws AddressException, MessagingException {
		return notificationCategoryLogicController.deleteCategory(katZglRequestData);
	}
	
	
}
 