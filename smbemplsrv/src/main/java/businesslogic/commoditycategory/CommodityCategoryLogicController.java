package businesslogic.commoditycategory;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.commoditycategory.CommodityCategoryDAO;
import dbmodel.CommodityCategory;
import restapi.commoditycategory.CommodityCatRestData;

@Stateless
@LocalBean
public class CommodityCategoryLogicController {

	@EJB
	CommodityCategoryDAO commodityCategoryDAO;

	
	public String getAll() {
		List<CommodityCategory> commodityCatCol = commodityCategoryDAO.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (CommodityCategory commodityCat : commodityCatCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", commodityCat.getId());
			jsonObject.put("name", commodityCat.getName());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	public String addNewCommodityCategoty(CommodityCatRestData commodityCatRestData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		CommodityCategory commodityCategory = new CommodityCategory();
		commodityCategory.setName(commodityCatRestData.name);
		
		commodityCategory = commodityCategoryDAO.insert(commodityCategory);
		
		jsonObject.put("id", commodityCategory.getId());
		jsonObject.put("name", commodityCategory.getName());
		
		return jsonObject.toString();
	}
	
	public String deleteCommodityCategory(CommodityCatRestData commodityCatRestData) throws JSONException, AddressException, MessagingException {
		CommodityCategory commodityCategory = commodityCategoryDAO.findEntityByID(commodityCatRestData.id);
		
		commodityCategoryDAO.delete(commodityCategory);
		return "";
	}


}
