package smbemplsrv.query.restaction.zgloszenia;

import java.io.IOException;

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
import smbemplsrv.facade.produkt.ProduktFacade;
import smbemplsrv.facade.zgloszenia.ZgloszeniaFacade;

@RequestScoped
@Path("/query/zgloszenia")
public class ZgloszeniaRestAction {
	
	@EJB
	ZgloszeniaFacade zgloszeniaFacade;

	@POST
	@Path("/getZgloszenia")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszenia(ZgloszeniaRequestQueryData zgloszeniaRequestQueryData) throws JSONException, AddressException, MessagingException, IOException {
		return zgloszeniaFacade.getZgloszenia(zgloszeniaRequestQueryData);
	}
	
	@POST
	@Path("/getZgloszenieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZgloszenieById(ZgloszeniaRequestQueryData zgloszeniaRequestQueryData) throws JSONException, AddressException, MessagingException {
		return  zgloszeniaFacade.getZgloszeniaById(zgloszeniaRequestQueryData);
	}
}
 