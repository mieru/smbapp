package smbemplsrv.query.restaction.magazyn;

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

import smbemplsrv.facade.magazyn.MagazynFacade;

@RequestScoped
@Path("/query/magkonf")
public class MagazynRestAction {
	
	@EJB
	MagazynFacade magazynFacade;

	@POST
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getAll() throws JSONException, AddressException, MessagingException {
		return magazynFacade.getMagzyny();
	}
	
	@POST
	@Path("/getMagazynById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMagazynById(MagazynRequestData magazynRequestData) throws JSONException, AddressException, MessagingException {
		return magazynFacade.getMagazynById(magazynRequestData);
	}
}
 