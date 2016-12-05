package smbemplsrv.command.restaction.uzytkownicy;

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
@Path("/command/uzytkonf")
public class UzytkownikRestAction {
	
	@EJB
	UzytkownicyFacade uzytkownicyFacade;
	
	@POST
	@Path("/addNewUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewUser(UzytkownikRequestData uzytkownikRequestData) throws AddressException, MessagingException {
		return uzytkownicyFacade.dodajUzytkownika(uzytkownikRequestData);
	}
	
	@POST
	@Path("/editUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editUser(UzytkownikRequestData uzytkownikRequestData) throws AddressException, MessagingException {
		return uzytkownicyFacade.edytujUzytkownika(uzytkownikRequestData);
	}
	
	@POST
	@Path("/deleteUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteUser(UzytkownikRequestData uzytkownikRequestData) throws AddressException, MessagingException {
		return uzytkownicyFacade.usunUzytkownika(uzytkownikRequestData);
	}
	
	
}
 