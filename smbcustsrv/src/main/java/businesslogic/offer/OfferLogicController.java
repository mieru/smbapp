package businesslogic.offer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.commodity.CommodityDAO;
import dbmenager.commoditycategory.CommodityCategoryDAO;
import dbmodel.Commodity;
import dbmodel.CommodityCategory;
import restapi.offer.OfferRestData;

@Stateless
@LocalBean
public class OfferLogicController {
	@EJB
	CommodityDAO commodityDAO;
	@EJB
	CommodityCategoryDAO commodityCategoryDAO;

	public String getOffer(OfferRestData offerRestData) throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		Integer commodityCategoryId = null;
		CommodityCategory commodityCategory = null;
		List<Commodity> commodities = null;
		if(offerRestData.kategoria != null){
			commodityCategoryId = Integer.parseInt(offerRestData.kategoria);
			commodityCategory = commodityCategoryDAO.findEntityByID(commodityCategoryId);
			commodities = commodityCategory.getCommodities();
		}else{
			commodities = commodityDAO.findAll(); 
		}
		for (Commodity commodity : commodities) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lp", commodities.indexOf(commodity));
			jsonObject.put("nazwa", commodity.getName());
			jsonObject.put("id", commodity.getIs());
			
			   
			jsonObject.put("opis", commodity.getDescription());
			jsonObject.put("cnetto", commodity.getNetPrice());
			jsonObject.put("stawka_vat", commodity.getTaxRate());
			
			jsonObject.put("jednostka", commodity.getUnit());
			
			
			  File filename = new File(System.getProperty("jboss.server.data.dir").toString() + commodity.getCommoditiesImage().getImagePath());
			     String base64 = getStringImage(filename);
			
			jsonObject.put("cbrutto", commodity.getNetPrice() * (1.00 + commodity.getTaxRate() * 0.01));
			jsonObject.put("image",base64);
			
			jsonArray.put(jsonObject);
			
		}
		return jsonArray.toString();
	}
	
	private String getStringImage(File file){
	    try {
	        FileInputStream fin = new FileInputStream(file);
	        byte[] imageBytes = new byte[(int)file.length()];
	        fin.read(imageBytes, 0, imageBytes.length);
	        fin.close();
	        return Base64.getEncoder().encodeToString(imageBytes);
	    } catch (Exception ex) {
	    }
	    return null;
	}
}
