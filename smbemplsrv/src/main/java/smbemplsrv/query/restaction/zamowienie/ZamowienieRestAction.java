package smbemplsrv.query.restaction.zamowienie;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import smbemplsrv.facade.zamowienie.ZamowienieFacade;


@RequestScoped
@Path("/query/zamowienia")
public class ZamowienieRestAction {

	@EJB
	ZamowienieFacade zamowienieFacade;
	
	@POST
	@Path("/getZamowienia")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienia(ZamowienieRequestQueryData zamowieniaRequestQueryData) throws JSONException {
		return zamowienieFacade.getZamowienia(zamowieniaRequestQueryData);
	}
	
	@POST
	@Path("/getZamowienieById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienieById(ZamowienieRequestQueryData zamowieniaRequestQueryData) throws JSONException {
		return zamowienieFacade.getZamowienieById(zamowieniaRequestQueryData);
	}

	
}
