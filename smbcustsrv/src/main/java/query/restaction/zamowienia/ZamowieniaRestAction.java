package query.restaction.zamowienia;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import facade.zamowienie.ZamowienieFacade;

@RequestScoped
@Path("/query/zamowienia")
public class ZamowieniaRestAction {

	@EJB
	ZamowienieFacade zamowienieFacade;
	
	@POST
	@Path("/getZamowienia")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienia(ZamowieniaRequestQueryData zamowieniaRequestQueryData) throws JSONException {
		return zamowienieFacade.getZamowienia(zamowieniaRequestQueryData);
	}
	
	@POST
	@Path("/getZamowienieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienieById(ZamowieniaRequestQueryData zamowieniaRequestQueryData) throws JSONException {
		return zamowienieFacade.getZamowienieById(zamowieniaRequestQueryData);
	}

	
}
