package smbemplsrv.query.restaction.konfmail;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import smbemplsrv.facade.konfmail.KonfMailFacade;

@RequestScoped
@Path("/query/konfmail")
public class KonfMailRestAction {
	
	@EJB
	KonfMailFacade konfMailFacade;
	
	
	@POST
	@Path("/getMailConfiguration")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMailConfiguration() throws AddressException, MessagingException {
		return konfMailFacade.getKonfMail();
	}
	
	
}
 