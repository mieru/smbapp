package businesslogic.notificationcategory;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.notificationcategory.NotificationCategoryDAO;
import dbmodel.NotificationCategory;
import restapi.notyficationcategory.NotyficationCategoryRestData;

@Stateless
@LocalBean
public class NotificationCategoryLogicController {

	@EJB
	NotificationCategoryDAO notificationCategoryDAO;

	public String getNotificationCategory() throws JSONException, AddressException, MessagingException {
		List<NotificationCategory> categoryCol = notificationCategoryDAO.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (NotificationCategory notificationCategory : categoryCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", notificationCategory.getId());
			jsonObject.put("name", notificationCategory.getName());
			jsonObject.put("czyKlient", notificationCategory.getCanCustomerSee());
			jsonObject.put("czyMagazyn", notificationCategory.getIsWarehouse());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	public String addNewCategory(NotyficationCategoryRestData notyficationCategoryRestData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		NotificationCategory notificationCategory = new NotificationCategory();
		notificationCategory.setName(notyficationCategoryRestData.name);
		if(notyficationCategoryRestData.czyKlient == null){
			notificationCategory.setCanCustomerSee(false);
		}else{
			notificationCategory.setCanCustomerSee(notyficationCategoryRestData.czyKlient);
		}
		if(notyficationCategoryRestData.czyMagazyn == null){
			notificationCategory.setIsWarehouse(false);
		}else{
			notificationCategory.setIsWarehouse(notyficationCategoryRestData.czyMagazyn);
		}
		
		
		notificationCategory = notificationCategoryDAO.insert(notificationCategory);
		
		jsonObject.put("id", notificationCategory.getId());
		jsonObject.put("name", notificationCategory.getName());
		jsonObject.put("czyKlient", notificationCategory.getCanCustomerSee());
		jsonObject.put("czyMagazyn", notificationCategory.getIsWarehouse());
		
		return jsonObject.toString();
	}
	
	public String deleteCategory(NotyficationCategoryRestData notyficationCategoryRestData) throws JSONException, AddressException, MessagingException {
		NotificationCategory notificationCategory =notificationCategoryDAO.findEntityByID(notyficationCategoryRestData.id);
		
		notificationCategoryDAO.delete(notificationCategory);
		return "";
	}


}
