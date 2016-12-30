package restapi.commodity;

import java.io.FileNotFoundException;
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

import com.lowagie.text.DocumentException;

import businesslogic.commodity.CommodityLogicController;

@RequestScoped
@Path("/commodity")
public class CommodityRestAction {
	
	@EJB
	CommodityLogicController commodityLogicController;

	@POST
	@Path("/getProdukty")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getProdukty(CommodityRestData produktRequestData) throws JSONException, AddressException, MessagingException, IOException {
		return commodityLogicController.getCommodities(produktRequestData);
	}
	
	@POST
	@Path("/getProduktById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getProduktById(CommodityRestData produktRequestData) throws JSONException, AddressException, MessagingException {
		return commodityLogicController.getCommodityById(produktRequestData);
	}
	
	@POST
	@Path("/addNewProd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewProd(CommodityRestData produktRequestData) throws AddressException, MessagingException, FileNotFoundException, IOException, DocumentException {
		return commodityLogicController.addCommodity(produktRequestData);
	}
	
	@POST
	@Path("/deleteProd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String deleteProd(CommodityRestData produktRequestData) throws AddressException, MessagingException {
		return commodityLogicController.deleteCommodity(produktRequestData);
	}
	
	@POST
	@Path("/editProd")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String editProd(CommodityRestData produktRequestData) throws AddressException, MessagingException, FileNotFoundException, IOException {
		return commodityLogicController.editCommodity(produktRequestData);
	}
}
 