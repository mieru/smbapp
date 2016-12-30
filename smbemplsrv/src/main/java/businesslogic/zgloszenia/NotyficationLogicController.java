package businesslogic.zgloszenia;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import businesslogic.systemmailconfiguration.MailSender;
import dbmenager.commodity.CommodityDAO;
import dbmenager.notificationcategory.NotificationCategoryDAO;
import dbmenager.notyfication.NotificationDAO;
import dbmenager.notyficationmessage.NotificationMessageDAO;
import dbmenager.users.UsersDAO;
import dbmodel.Commodity;
import dbmodel.NotificationCategory;
import dbmodel.NotificationMessage;
import dbmodel.Notyfication;
import dbmodel.Users;
import restapi.notification.NotificationRestData;
import utils.status.Status;

@Stateless
@LocalBean
public class NotyficationLogicController {

	@EJB
	NotificationDAO notificationDAO;
	@EJB
	NotificationMessageDAO  notificationMessageDAO;
	
	@EJB
	NotificationCategoryDAO notificationCategoryDAO;
	
	@EJB
	UsersDAO usersDAO;
	
	@EJB
	CommodityDAO commodityDAO;
	
	@EJB
	MailSender mailSender;
	
	
	public String getNotyfication(NotificationRestData notificationRestData) throws JSONException, IOException {
		NotificationCategory notificationCategory = null;
		if(notificationRestData.idKategorii != null)
			 notificationCategory = notificationCategoryDAO.findEntityByID(notificationRestData.idKategorii);
		Users user = null;
		List<Notyfication> colZgl = null;
		if(notificationRestData.idKategorii != null && notificationCategory.getIsWarehouse() != null && notificationCategory.getIsWarehouse()){
			 colZgl = notificationCategory.getNotyfications();
		}else{
			if(notificationRestData.idUser != null){
			 user = usersDAO.findEntityByID(notificationRestData.idUser);
			 colZgl = user.getNotyfications1();
			}else{
				colZgl = notificationDAO.findAll();
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (Notyfication notyfication : colZgl) {
			if(notificationRestData.idKategorii != null && !notyfication.getNotificationCategory().getId().equals(notificationRestData.idKategorii)) continue;
			jsonObject = new JSONObject();
			jsonObject.put("id", notyfication.getId());
			jsonObject.put("numer_zgl", notyfication.getNotificationNumber());
			jsonObject.put("data_zgloszenia", notyfication.getCreatDate().getTime());
			if(notyfication.getEndDate() != null)
			jsonObject.put("data_zamkniecia", notyfication.getEndDate().getTime());
			jsonObject.put("kategoria", notyfication.getNotificationCategory().getName());
			jsonObject.put("temat", notyfication.getSubject());
			if( notyfication.getUser2() != null){
				jsonObject.put("klient", notyfication.getUser2().getName() +" "+  notyfication.getUser2().getSurname());
			}
			jsonObject.put("status", Status.ZGLOSZENIE_STATE.getText(notyfication.getStatus()));
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray.toString();
	}


	public String getNotificationById(NotificationRestData notificationRestData) {
		JSONObject jsonObject = new JSONObject();
		Notyfication notyfication = notificationDAO.findEntityByID(notificationRestData.idZgloszenia);
		
		jsonObject.put("id", notyfication.getId());
		jsonObject.put("numer_zgl", notyfication.getNotificationNumber());
		jsonObject.put("data_zgloszenia", notyfication.getCreatDate().getTime());
		if(notyfication.getEndDate() != null)
		jsonObject.put("data_zamkniecia", notyfication.getEndDate().getTime());
		jsonObject.put("kategoria", notyfication.getNotificationCategory().getName());
		jsonObject.put("temat", notyfication.getSubject());
			jsonObject.put("tresc", notyfication.getContent());
			jsonObject.put("aktywnosci", getJsonArray(notyfication.getNotificationMessages()));
			
			if( notyfication.getUser2() != null){
				jsonObject.put("klient", notyfication.getUser2().getName() +" "+  notyfication.getUser2().getSurname());
			}
			jsonObject.put("status", Status.ZGLOSZENIE_STATE.getText(notyfication.getStatus()));
			
		
		return jsonObject.toString();
	}
	
	public String addMessageToNotification(NotificationRestData notificationRestData) throws AddressException, MessagingException{
		Notyfication notyfication = null;
		if(notificationRestData.idZgloszenia != null){
			 notyfication = notificationDAO.findEntityByID(notificationRestData.idZgloszenia);
		}
		Users user = null;
		if(notificationRestData.idUser != null){
			 user = usersDAO.findEntityByID(notificationRestData.idUser);
		}
		
		NotificationMessage notificationMessage = new NotificationMessage();
		notificationMessage.setContent(notificationRestData.tresc);
		notificationMessage.setType("K");
		notificationMessage.setAddDate(new Timestamp(System.currentTimeMillis()));
		notificationMessage.setUser(user);
		notificationMessage.setNotyfication(notyfication);
		
		notificationMessageDAO.insert(notificationMessage);
		if(notyfication.getUser2() != null)
		mailSender.sendMail("Nowa wiadomosc do zgloszenia nr : " + notyfication.getNotificationNumber(), "Do twojego zgłoszenia dodano nowa wiadomoś.", notyfication.getUser2().getEmail());
		
		return "";
	}
	
	private JSONArray getJsonArray(
			List<NotificationMessage> notificationMessages)
			throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;

		for (NotificationMessage notificationMessage : notificationMessages) {
			jsonObject = new JSONObject();
			jsonObject.put("id", notificationMessage.getId());
			jsonObject.put("data", notificationMessage.getAddDate().getTime());
			jsonObject.put("tresc", notificationMessage.getContent());
			String userName = "";
			if (notificationMessage.getUser().getCompanyName() != null) {
				userName = notificationMessage.getUser().getCompanyName();
			} else {
				userName = notificationMessage.getUser().getName() + " "
						+ notificationMessage.getUser().getSurname();
			}
			jsonObject.put("uzytkownik", userName);

			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}


	public String closeNotification(NotificationRestData notificationRestData) {
		Notyfication notyfication = notificationDAO.findEntityByID(notificationRestData.idZgloszenia);
		if(notyfication.getNotificationCategory().getIsWarehouse()){
			JSONArray jsonArray = new JSONArray(notyfication.getCommoditiesToSuplement());
			for (int i = 0; i < jsonArray.length(); i++) {
				Commodity commodity = commodityDAO.findEntityByID(jsonArray.getInt(i));
				commodity.setNotified(false);
				commodityDAO.update(commodity);
			}
		}
		notyfication.setStatus(Status.ZGLOSZENIE_STATE.ZAKONCZONE);
		notyfication.setEndDate(new Timestamp(System.currentTimeMillis()));
		notificationDAO.update(notyfication);
		
		return "";
	}
	
	
}
