package restapi.companyinfo;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import businesslogic.companyinfo.CompanyInfoLogicController;


@RequestScoped
@Path("/companyinfo")
public class CompanyInfoRest {
	
	@EJB
	CompanyInfoLogicController companyInfoLogicController; 
	
	@POST
	@Path("/getCompanyInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getDaneFirmy() throws JSONException, IllegalArgumentException, IllegalAccessException {
		return companyInfoLogicController.getCompanyInfo();
	}
}