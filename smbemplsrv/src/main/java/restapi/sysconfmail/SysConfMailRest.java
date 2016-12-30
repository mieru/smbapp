package restapi.sysconfmail;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import businesslogic.systemmailconfiguration.SystemMailConfiguratuinLogicController;

@RequestScoped
@Path("/sysconfmail")
public class SysConfMailRest {
	
	@EJB
	SystemMailConfiguratuinLogicController systemMailConfiguratuinLogicController;
	
	
	@POST
	@Path("/getSysMailConfiguration")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMailConfiguration() throws AddressException, MessagingException {
		return systemMailConfiguratuinLogicController.getSysMailConfiguration();
	}
	
	@POST
	@Path("/saveMailConfiguration")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveMailKonfiguration(SysMailConfRestData sysMailConfRestData) throws AddressException, MessagingException {
		return systemMailConfiguratuinLogicController.saveMailConfiguration(sysMailConfRestData);
	}
	
}
 