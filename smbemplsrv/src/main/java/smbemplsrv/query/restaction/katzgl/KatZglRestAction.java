package smbemplsrv.query.restaction.katzgl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import smbemplsrv.facade.katzgl.KatZglFacade;

@RequestScoped
@Path("/query/katzgl")
public class KatZglRestAction {
	
	@EJB
	KatZglFacade katZglFacade;
	
	
	@POST
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAll() throws AddressException, MessagingException {
		return katZglFacade.getKategorieZglosznia();
	}
	
	
}
 