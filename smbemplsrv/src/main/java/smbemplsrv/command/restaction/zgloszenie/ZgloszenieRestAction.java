package smbemplsrv.command.restaction.zgloszenie;

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
import smbemplsrv.facade.zgloszenia.ZgloszeniaFacade;

@RequestScoped
@Path("/command/zgloszenie")
public class ZgloszenieRestAction {
	
	@EJB
	ZgloszeniaFacade zgloszeniaFacade;
	
	@POST
	@Path("/addNewMessage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewUser(ZgloszenieRequestData zgloszenieRequestData) throws AddressException, MessagingException {
		return zgloszeniaFacade.dodajWiadomoscDoZgloszenia(zgloszenieRequestData);
	}
	
	@POST
	@Path("/closeZgl")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String closeZgl(ZgloszenieRequestData zgloszenieRequestData) throws AddressException, MessagingException {
		return zgloszeniaFacade.zamknijZgloszenie(zgloszenieRequestData);
	}
}
