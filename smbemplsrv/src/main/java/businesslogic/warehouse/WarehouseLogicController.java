package businesslogic.warehouse;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.warehouse.WarehouseDAO;
import dbmodel.Warehouse;
import restapi.warehouse.WarehouseRestData;

@Stateless
@LocalBean
public class WarehouseLogicController {

	@EJB
	WarehouseDAO warehouseDAO;
	
	
	public String getWarehouses() throws JSONException, AddressException, MessagingException {
		List<Warehouse> warehouses = warehouseDAO.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (Warehouse warehouse : warehouses) {
			jsonObject = new JSONObject();
			jsonObject.put("id", warehouse.getId());
			jsonObject.put("name", warehouse.getName());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}


	public String addWarehouse(WarehouseRestData warehouseRestData) {
		JSONObject jsonObject = new JSONObject();
		Warehouse warehouse = new Warehouse();
		warehouse.setName(warehouseRestData.name);
		
		warehouse = warehouseDAO.insert(warehouse);
		
		jsonObject.put("id", warehouse.getId());
		jsonObject.put("name", warehouse.getName());
		
		return jsonObject.toString();
	}



	
	public String deleteWarehouse(WarehouseRestData warehouseRestData) throws JSONException, AddressException, MessagingException {
		Warehouse warehouse = warehouseDAO.findEntityByID(warehouseRestData.id);
		warehouseDAO.delete(warehouse);
		return "";
	}


	public String getWarehouseById(WarehouseRestData warehouseRestData) {
		JSONObject jsonObject = new JSONObject();
		Warehouse warehouse = warehouseDAO.findEntityByID(warehouseRestData.idMagazynu);
		
		jsonObject.put("id", warehouse.getId());
		jsonObject.put("name", warehouse.getName());
		
		return jsonObject.toString();
	}


	
}
