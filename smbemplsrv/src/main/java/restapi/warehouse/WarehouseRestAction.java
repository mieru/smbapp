package restapi.warehouse;

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

import businesslogic.warehouse.WarehouseLogicController;

@RequestScoped
@Path("/warehouseconf")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarehouseRestAction {
	
	@EJB
	WarehouseLogicController warehouseLogicController;

	@POST
	@Path("/getWarehouses")
	public String getWarehouses() throws JSONException, AddressException, MessagingException {
		return warehouseLogicController.getWarehouses();
	}
	
	@POST
	@Path("/getWarehouseById")
	public String getWarehouseById(WarehouseRestData warehouseRestData) throws JSONException, AddressException, MessagingException {
		return warehouseLogicController.getWarehouseById(warehouseRestData);
	}
	
	@POST
	@Path("/addWarehouse")
	public String addWarehouse(WarehouseRestData warehouseRestData) throws AddressException, MessagingException {
		return warehouseLogicController.addWarehouse(warehouseRestData);
	}
	
	@POST
	@Path("/deleteWarehouse")
	public String deleteWarehouse(WarehouseRestData warehouseRestData) throws AddressException, MessagingException {
		return warehouseLogicController.deleteWarehouse(warehouseRestData);
	}
	
}
 