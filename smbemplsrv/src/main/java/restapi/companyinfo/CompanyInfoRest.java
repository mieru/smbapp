package restapi.companyinfo;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	public String getCompanyInfo() throws AddressException, MessagingException {
		return companyInfoLogicController.getCompanyInfo();
	}
	
	@POST
	@Path("/saveCompanyInfo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveCompanyInfo(CompanyInfoRestData companyInfoRestData) throws AddressException, MessagingException {
		return companyInfoLogicController.saveCompanyInfo(companyInfoRestData);
	}
	
}
 