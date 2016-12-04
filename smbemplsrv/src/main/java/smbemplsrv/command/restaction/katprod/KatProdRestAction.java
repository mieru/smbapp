package smbemplsrv.command.restaction.katprod;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import smbemplsrv.facade.katprod.KatProdFacade;

@RequestScoped
@Path("/command/katprod")
public class KatProdRestAction {
	
	@EJB
	KatProdFacade katProdFacade;
	
	@POST
	@Path("/addNewCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewCategory(KatProdRequestData katProdRequestData) throws AddressException, MessagingException {
		return katProdFacade.dodajNowaKategorie(katProdRequestData);
	}
	
	@POST
	@Path("/deleteCategory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteCategory(KatProdRequestData katProdRequestData) throws AddressException, MessagingException {
		return katProdFacade.usunKategorie(katProdRequestData);
	}
	
	
}
 