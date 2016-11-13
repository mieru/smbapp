package query.restaction.faktury;

import java.io.File;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import dbmodel.FakturaSprzedazy;
import query.ejbcontrol.faktury.FakturyEjbQueryController;

@RequestScoped
@Path("/faktury/query/")
@Produces("application/json")
@Consumes("application/json")
public class DownloadFileRestAction {

	@EJB
	FakturyEjbQueryController fakturyEjbQueryController;

	@GET
	@Path("/downloadPdf")
	@Produces("application/pdf")
	public Response getTextFile(@QueryParam("idFaktury") Integer idFaktury) {
		FakturaSprzedazy fakturaSprzedazy = null;
		if(idFaktury!= null){
			fakturaSprzedazy = fakturyEjbQueryController.findEntityByID(idFaktury);
		}
		File file = new File(System.getProperty("jboss.server.data.dir").toString() + fakturaSprzedazy.getFilePath());
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\"" + fakturaSprzedazy.getFileName() + "\"");
		return response.build();

	}
	
}