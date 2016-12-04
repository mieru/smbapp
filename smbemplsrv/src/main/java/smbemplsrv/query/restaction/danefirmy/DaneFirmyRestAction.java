package smbemplsrv.query.restaction.danefirmy;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import smbemplsrv.facade.danefirmy.DaneFirmyFacade;
import smbemplsrv.facade.konfmail.KonfMailFacade;

@RequestScoped
@Path("/query/danefirmy")
public class DaneFirmyRestAction {
	
	@EJB
	DaneFirmyFacade daneFirmyFacade;
	
	
	@POST
	@Path("/getData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getData() throws AddressException, MessagingException {
		return daneFirmyFacade.getDaneFirmy();
	}
	
	
}
 