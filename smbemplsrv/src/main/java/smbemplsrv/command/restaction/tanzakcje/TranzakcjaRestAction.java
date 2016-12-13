package smbemplsrv.command.restaction.tanzakcje;

import java.io.FileNotFoundException;
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

import com.lowagie.text.DocumentException;

import smbemplsrv.facade.sprzedaz.SprzedarzFacade;

@RequestScoped
@Path("/command/sprzedaz")
public class TranzakcjaRestAction {
	
	@EJB
	SprzedarzFacade sprzedazFacade;
	
	@POST
	@Path("/addNew")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNew(TanzakcjaRequestData tanzakcjaRequestData) throws AddressException, MessagingException, FileNotFoundException, IOException, DocumentException, SQLException {
		return sprzedazFacade.addNew(tanzakcjaRequestData, null);
	}
	
}
 