package restapi.order;

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

import businesslogic.order.OrderLogicController;

@RequestScoped
@Path("/zamowienie")
public class OrderRest {
	
	@EJB
	OrderLogicController orderLogicController;
	
	@POST
	@Path("/getOrders")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getZamowienia(OrderRestData orderRestData) throws JSONException, IllegalArgumentException, IllegalAccessException {
		return orderLogicController.getOrders(orderRestData);
	}
	
	@POST
	@Path("/getOrderById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getOrderById(OrderRestData orderRestData) throws JSONException, IllegalArgumentException, IllegalAccessException {
		return orderLogicController.getOrderById(orderRestData);
	}
	
	@POST
	@Path("/addNewOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addNewOrder(OrderRestData orderRestData) throws JSONException, AddressException, MessagingException {
		return orderLogicController.addNewOrder(orderRestData);
	}
	
	@POST
	@Path("/addMessageToOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String addMessageToOrder(OrderRestData orderRestData) throws JSONException, AddressException, MessagingException {
		return orderLogicController.addMessageToOrder(orderRestData);
	}
}
