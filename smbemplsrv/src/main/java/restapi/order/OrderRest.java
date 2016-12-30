package restapi.order;


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

import org.json.JSONException;

import com.lowagie.text.DocumentException;

import businesslogic.order.OrderLogicController;


@RequestScoped
@Path("/order")
public class OrderRest {

	@EJB
	OrderLogicController zamowienieFacade;
	
	@POST
	@Path("/getOrders")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getOrders(OrderRestData zamowieniaRequestQueryData) throws JSONException {
		return zamowienieFacade.getOrders(zamowieniaRequestQueryData);
	}
	
	@POST
	@Path("/getOrderById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getOrderById(OrderRestData orderRestData) throws JSONException {
		return zamowienieFacade.getOrderById(orderRestData);
	}

	@POST
	@Path("/addMessageToOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessageToOrder(OrderRestData orderRestData) throws JSONException, AddressException, MessagingException {
		return zamowienieFacade.addMessageToOrder(orderRestData);
	}
	

	@POST
	@Path("/closeOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String closeOrder(OrderRestData orderRestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		return zamowienieFacade.closeOrder(orderRestData);
	}
	
	@POST
	@Path("/cancelOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String cancelOrder(OrderRestData orderRestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		return zamowienieFacade.cancelOrder(orderRestData);
	}
	
}
