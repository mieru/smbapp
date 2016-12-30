package restapi.users;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import businesslogic.users.UsersLogicController;

@RequestScoped
@Path("/userconf")
public class UsersRest {
	
	@EJB
	UsersLogicController usersLogicController;
	
	
	@POST
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUsers(UsersRestData usersRestData) throws AddressException, MessagingException {
		return usersLogicController.getUsers(usersRestData);
	}
	
	
	@POST
	@Path("/getUserById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUserById(UsersRestData usersRestData) throws AddressException, MessagingException {
		return usersLogicController.getUserById(usersRestData);
	}
	
	@POST
	@Path("/addUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addUser(UsersRestData usersRestData) throws AddressException, MessagingException {
		return usersLogicController.addUser(usersRestData);
	}
	
	@POST
	@Path("/editUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editUser(UsersRestData usersRestData) throws AddressException, MessagingException {
		return usersLogicController.editUser(usersRestData);
	}
	
	@POST
	@Path("/deleteUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteUser(UsersRestData usersRestData) throws AddressException, MessagingException {
		return usersLogicController.deleteUser(usersRestData);
	}
	
	
}
 