package businesslogic.commodity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import dbmenager.commodityimage.CommodityImageDAO;
import dbmenager.warehouse.WarehouseDAO;
import dbmodel.CommoditiesImage;
import dbmodel.Commodity;
import dbmodel.CommodityCategory;
import dbmodel.Warehouse;
import restapi.commodity.CommodityRestData;

@Stateless
@LocalBean
public class CommodityLogicController {

	@EJB
	CommodityDAO commodityDAO;
	@EJB
	WarehouseDAO warehouseDAO;
	@EJB
	CommodityCategoryDAO commodityCategoryDAO;
	@EJB
	CommodityImageDAO commodityImageDAO;
	
	
	
	public String getCommodities(CommodityRestData commodityRestData) throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		Integer warehouseId = null;
		Warehouse warehouse = null;
		List<Commodity> commodities = null;
		if(commodityRestData.idMagazynu != null){
			warehouseId = commodityRestData.idMagazynu;
			warehouse = warehouseDAO.findEntityByID(warehouseId);
			commodities = warehouse.getCommodities();
		}else{
			commodities = commodityDAO.findAll(); 
		}
		
		for (Commodity commodity : commodities) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("lp", commodities.indexOf(commodity));
			jsonObject.put("nazwa", commodity.getName());
			jsonObject.put("id", commodity.getIs());
			     File filename = new File(System.getProperty("jboss.server.data.dir").toString() + commodity.getCommoditiesImage().getImagePath());
			     String base64 = getStringImage(filename);
			     
			     if(commodity.getMinAmount() != null && commodity.getAmountInWarehouse() != null && commodity.getMinAmount() > commodity.getAmountInWarehouse()){
			    	 jsonObject.put("fontColor","red");
			     }else{
			    	 jsonObject.put("fontColor","black");
			     }
			
			jsonObject.put("image",base64);
			jsonObject.put("ilosc",commodity.getAmountInWarehouse());
			jsonObject.put("iloscZakup",1);
			jsonObject.put("stan_min",commodity.getMinAmount());
			jsonObject.put("opis", commodity.getDescription());
			jsonObject.put("cnetto", commodity.getNetPrice());
			jsonObject.put("stawka_vat", commodity.getTaxRate());
			jsonObject.put("cbrutto", commodity.getNetPrice() * (1.00 + commodity.getTaxRate() * 0.01));
			jsonObject.put("jednostka", commodity.getUnit());
			jsonArray.put(jsonObject);
			
		}
		return jsonArray.toString();
	}
	
	public String getCommodityById(CommodityRestData produktRequestData) {
		Commodity commodity = commodityDAO.findEntityByID(produktRequestData.id);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", commodity.getName());
		jsonObject.put("id", commodity.getIs());
		     File filename = new File(System.getProperty("jboss.server.data.dir").toString() + commodity.getCommoditiesImage().getImagePath());
		     String base64 = getStringImage(filename);
		     
		jsonObject.put("image","data:image/jpeg;base64,"+base64);
		jsonObject.put("ilosc",commodity.getAmountInWarehouse());
		jsonObject.put("stan_min",commodity.getMinAmount());
		jsonObject.put("stan_min_uzyt",commodity.getUserMinAmount());
		jsonObject.put("stan_min_sys",commodity.getSysMinAmoint());
		jsonObject.put("opis", commodity.getDescription());
		jsonObject.put("kat", commodity.getCommodityCategory().getId().toString());
		jsonObject.put("id_magazynu", commodity.getWarehouse().getId().toString());
		jsonObject.put("cnetto", commodity.getNetPrice());
		jsonObject.put("stawka_vat", commodity.getTaxRate());
		jsonObject.put("cbrutto", commodity.getNetPrice() * (1.00 + commodity.getTaxRate() * 0.01));
		jsonObject.put("jednostka", commodity.getUnit());
		jsonObject.put("czyStanUzyt", commodity.getIsUserMinAmount());
		jsonObject.put("czyStanSys", !commodity.getIsUserMinAmount());
		
		return jsonObject.toString();
	}


	public String addCommodity(CommodityRestData commodityRestData) throws FileNotFoundException, IOException {
		Commodity commodity = new Commodity();
		commodity.setNetPrice(commodityRestData.cnetto);
		commodity.setAmountInWarehouse(commodityRestData.ilosc);
		commodity.setUnit(commodityRestData.jednostka);
		commodity.setName(commodityRestData.name);
		commodity.setDescription(commodityRestData.opis);
		commodity.setTaxRate(commodityRestData.stawkaVat);
		commodity.setUserMinAmount(commodityRestData.stan_min);
		commodity.setNotified(false);
		commodity.setIsUserMinAmount(false);
		commodity.setSysMinAmoint(1);
		if(commodityRestData.stan_min != null){
			commodity.setMinAmount(commodityRestData.stan_min);
		}else{
			commodity.setMinAmount(1);
		}
		
		
		
		Warehouse warehouse = warehouseDAO.findEntityByID(commodityRestData.idMagazynu);
		CommodityCategory commodityCategory = commodityCategoryDAO.findEntityByID(commodityRestData.kat);
		
		commodity.setWarehouse(warehouse);
		commodity.setCommodityCategory(commodityCategory);
		
		commodity = commodityDAO.insert(commodity);
		byte[] data = Base64.getDecoder().decode(commodityRestData.image.split(",")[1]);
		try (OutputStream stream = new FileOutputStream(System.getProperty("jboss.server.data.dir").toString() + "\\zdjecia\\produkt" + commodity.getIs() + ".jpg")) {
		    stream.write(data);
		}
		CommoditiesImage commoditiesImage = new CommoditiesImage();
		commoditiesImage.setImagePath("\\zdjecia\\produkt" + commodity.getIs() + ".jpg");
		commoditiesImage = commodityImageDAO.insert(commoditiesImage);
		
		commodity.setCommoditiesImage(commoditiesImage);
		commodity = commodityDAO.update(commodity);
		
		return "";
	}


	public String deleteCommodity(CommodityRestData commodityRestData) {
	    Commodity commodity = commodityDAO.findEntityByID(commodityRestData.id);
	    commodityDAO.delete(commodity);
		return "";
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

	public String editCommodity(CommodityRestData commodityRestData) throws FileNotFoundException, IOException {
		Commodity commodity = commodityDAO.findEntityByID(commodityRestData.id);
		commodity.setNetPrice(commodityRestData.cnetto);
		commodity.setAmountInWarehouse(commodityRestData.ilosc);
		commodity.setUnit(commodityRestData.jednostka);
		commodity.setName(commodityRestData.name);
		commodity.setDescription(commodityRestData.opis);
		commodity.setTaxRate(commodityRestData.stawkaVat);
		commodity.setUserMinAmount(commodityRestData.stan_min_uzyt);
		
		if(commodityRestData.czyStanUzyt != null && commodityRestData.czyStanUzyt){
			commodity.setMinAmount(commodityRestData.stan_min_uzyt);
			commodity.setIsUserMinAmount(true);
		}
		if(commodityRestData.czyStanSys != null && commodityRestData.czyStanSys){
			commodity.setMinAmount(commodityRestData.stan_min_sys);
			commodity.setIsUserMinAmount(false);
		}
		
		
		Warehouse warehouse = warehouseDAO.findEntityByID(commodityRestData.idMagazynu);
		CommodityCategory commodityCategory = commodityCategoryDAO.findEntityByID(commodityRestData.kat);
		
		commodity.setWarehouse(warehouse);
		commodity.setCommodityCategory(commodityCategory);
		
		byte[] data = Base64.getDecoder().decode(commodityRestData.image.split(",")[1]);
		try (OutputStream stream = new FileOutputStream(System.getProperty("jboss.server.data.dir").toString() + "\\zdjecia\\produkt" + commodity.getIs() + ".jpg")) {
		    stream.write(data);
		}
		
		commodityDAO.update(commodity);
		
		return "";
	}
	
}
