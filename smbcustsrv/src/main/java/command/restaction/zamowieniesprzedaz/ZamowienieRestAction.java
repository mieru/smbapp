package command.restaction.zamowieniesprzedaz;

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

import facade.zamowienie.ZamowienieFacade;

@RequestScoped
@Path("/command/zamowienie")
public class ZamowienieRestAction {
	
	@EJB
	ZamowienieFacade zamowienieFacade;
	
	@POST
	@Path("/addNewOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewOrder(ZamowienieRequestCommandData zamowienieRequestData) throws JSONException, AddressException, MessagingException {
		return zamowienieFacade.dodajNoweZamowienie(zamowienieRequestData);
	}
	
	@POST
	@Path("/addMessageToOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessageToOrder(ZamowienieRequestCommandData zamowienieRequestData) throws JSONException, AddressException, MessagingException {
		return zamowienieFacade.dodajWiadomoscDoZamowienia(zamowienieRequestData);
	}
}
