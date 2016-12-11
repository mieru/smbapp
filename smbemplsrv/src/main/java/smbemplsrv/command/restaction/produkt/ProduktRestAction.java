package smbemplsrv.command.restaction.produkt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import smbemplsrv.facade.produkt.ProduktFacade;

@RequestScoped
@Path("/command/produkt")
public class ProduktRestAction {
	
	@EJB
	ProduktFacade produktFacade;
	
	@POST
	@Path("/addNewProd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewProd(ProduktRequestData produktRequestData) throws AddressException, MessagingException, FileNotFoundException, IOException, DocumentException {
		return produktFacade.dodajProdukt(produktRequestData);
	}
	
	@POST
	@Path("/deleteProd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteProd(ProduktRequestData produktRequestData) throws AddressException, MessagingException {
		return produktFacade.usunProdukt(produktRequestData);
	}
	
	@POST
	@Path("/editProd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editProd(ProduktRequestData produktRequestData) throws AddressException, MessagingException, FileNotFoundException, IOException {
		return produktFacade.edytujProdukt(produktRequestData);
	}
	
	
	
}
 