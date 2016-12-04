package smbemplsrv.query.restaction.uzytkownicy;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import smbemplsrv.facade.uzytkownicy.UzytkownicyFacade;

@RequestScoped
@Path("/query/uzytkonf")
public class UzytkownicyRestAction {
	
	@EJB
	UzytkownicyFacade uzytkownicyFacade;
	
	
	@POST
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUsers(UzytkwonikRequestQueryData uzytkwonikRequestQueryData) throws AddressException, MessagingException {
		return uzytkownicyFacade.getUsers(uzytkwonikRequestQueryData);
	}
	
	
	@POST
	@Path("/getUserById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUserById(UzytkwonikRequestQueryData uzytkwonikRequestQueryData) throws AddressException, MessagingException {
		return uzytkownicyFacade.getUserById(uzytkwonikRequestQueryData);
	}
	
	
}
 