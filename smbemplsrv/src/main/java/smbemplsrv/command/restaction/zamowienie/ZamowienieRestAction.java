package smbemplsrv.command.restaction.zamowienie;

import java.io.IOException;
import java.sql.SQLException;

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

import com.lowagie.text.DocumentException;

import smbemplsrv.command.restaction.zgloszenie.ZgloszenieRequestData;
import smbemplsrv.facade.zamowienie.ZamowienieFacade;

@RequestScoped
@Path("/command/zamowienie")
public class ZamowienieRestAction {
	
	@EJB
	ZamowienieFacade zamowienieFacade;
	
	@POST
	@Path("/addMessageToOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessageToOrder(ZamowienieRequestCommandData zamowienieRequestData) throws JSONException, AddressException, MessagingException {
		return zamowienieFacade.dodajWiadomoscDoZamowienia(zamowienieRequestData);
	}
	

	@POST
	@Path("/closeZam")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String closeZgl(ZamowienieRequestCommandData zamowienieRequestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		return zamowienieFacade.zamknijZamowienie(zamowienieRequestData);
	}
	
	@POST
	@Path("/anulujZam")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String anulujZam(ZamowienieRequestCommandData zamowienieRequestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		return zamowienieFacade.anulujZam(zamowienieRequestData);
	}
}
 