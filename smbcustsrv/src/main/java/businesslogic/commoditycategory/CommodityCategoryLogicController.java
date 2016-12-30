package businesslogic.commoditycategory;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.commoditycategory.CommodityCategoryDAO;
import dbmodel.CommodityCategory;

@Stateless
@LocalBean
public class CommodityCategoryLogicController {
	private static final String PN_NAME = "nazwa";
	private static final String PN_ID = "id";

	@EJB
	CommodityCategoryDAO commodityCategoryDAO;

	public String getAll() throws JSONException {
		JSONArray jsonArray = new JSONArray();

		List<CommodityCategory> commodityCategoriesCol = commodityCategoryDAO.findAll();
		
		JSONObject jsonObject = null;
		for (CommodityCategory category : commodityCategoriesCol) {
			jsonObject = new JSONObject();
			jsonObject.put(PN_ID, category.getId());
			jsonObject.put(PN_NAME, category.getName());
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
}
