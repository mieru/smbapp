package query.restaction.uzytkownik;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import facade.uzytkownik.UzytkownikFacade;

@RequestScoped
@Path("/query/uzytkownik")
public class UzytkownikRestAction {

	@EJB
	UzytkownikFacade uzytkownikFacade;

	@POST
	@Path("/getUzytkownikaById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getUzytkownikaById(UzytkwonikRequestQueryData uzytkwonikRequestData) throws JSONException {
		return uzytkownikFacade.getUzytkownikaById(uzytkwonikRequestData);
	}
}
