package smbemplsrv.command.restaction.magazyn;

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
import smbemplsrv.facade.magazyn.MagazynFacade;

@RequestScoped
@Path("/command/magkonf")
public class MagazynRestAction {
	
	@EJB
	MagazynFacade magazynFacade;
	
	@POST
	@Path("/addNewMag")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewMag(MagazynRequestData magazynRequestData) throws AddressException, MessagingException {
		return magazynFacade.dodajMagazyn(magazynRequestData);
	}
	
	@POST
	@Path("/deleteMag")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteMag(MagazynRequestData magazynRequestData) throws AddressException, MessagingException {
		return magazynFacade.usunMagazyn(magazynRequestData);
	}
	
	
	
}
 