package businesslogic.notification;

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

import dbmenager.notification.NotyficationDAO;
import dbmenager.notificationmessage.NotificationMessageDAO;
import dbmenager.notyficationcategory.NotifocationCategoryDAO;
import dbmenager.user.UserDAO;
import dbmodel.NotificationCategory;
import dbmodel.NotificationMessage;
import dbmodel.Notyfication;
import dbmodel.Users;
import mailmenager.MailSender;
import restapi.notification.NotificationRestData;
import utils.SmbJSONUtils;
import utils.Status;

@Stateless
@LocalBean
public class NotificationLogicController {
	@EJB
	NotyficationDAO notyficationDAO;

	@EJB
	UserDAO userDAO;

	@EJB
	NotifocationCategoryDAO notifocationCategoryDAO;

	@EJB
	NotificationMessageDAO notificationMessageDAO;
	
	@EJB
	MailSender mailSender;

	public String getNotificationCategory()
			throws JSONException, AddressException, MessagingException, IllegalArgumentException, IllegalAccessException {
		List<NotificationCategory> notificationCategoryCol = notifocationCategoryDAO.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (NotificationCategory notificationCategory : notificationCategoryCol) {
			if (notificationCategory.getCanCustomerSee()!=null && notificationCategory.getCanCustomerSee()) {
				jsonObject = new JSONObject();
				jsonObject.put("id", notificationCategory.getId());
				jsonObject.put("name", notificationCategory.getName());
				jsonArray.put(jsonObject);
			}
		}
		return jsonArray.toString();
	}

	public String getUserNotifications(NotificationRestData notificationRestData) throws JSONException, AddressException, MessagingException, IllegalArgumentException, IllegalAccessException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<Notyfication> notyficationCol = null;
		
		if(notificationRestData.idUser != null){
			Users user = userDAO.findEntityByID(notificationRestData.idUser);
			notyficationCol = user.getNotyfications2();
		}else{
			notyficationCol = notyficationDAO.findAll();
		}
		
		
		for (Notyfication notyfication : notyficationCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", notyfication.getId());
			jsonObject.put("numer_zgl", notyfication.getNotificationNumber());
			jsonObject.put("data_zgloszenia",
					notyfication.getCreatDate().getTime());
			if (notyfication.getEndDate() != null)
				jsonObject.put("data_zamkniecia",
						notyfication.getEndDate().getTime());
			jsonObject.put("kategoria",
					notyfication.getNotificationCategory().getName());
			jsonObject.put("temat", notyfication.getSubject());
			jsonObject.put("tresc", notyfication.getContent());
			jsonObject.put("aktywnosci",
					getJsonArray(notyfication.getNotificationMessages()));

			if (notyfication.getUser1() != null) {
				jsonObject.put("pracownik_obsl",
						notyfication.getUser1().getName() + " "
								+ notyfication.getUser1().getSurname());
			}
			jsonObject.put("status",
					Status.ZGLOSZENIE_STATE.getText(notyfication.getStatus()));
			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	public String getNotificationById(NotificationRestData notificationRestData) throws JSONException, AddressException, MessagingException {
		Notyfication notyfication = notyficationDAO.findEntityByID(notificationRestData.idZgloszenia);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", notyfication.getId());
		jsonObject.put("numer_zgl", notyfication.getNotificationNumber());
		jsonObject.put("data_zgloszenia",
				notyfication.getCreatDate().getTime());
		if (notyfication.getEndDate() != null)
			jsonObject.put("data_zamkniecia",
					notyfication.getEndDate().getTime());
		jsonObject.put("kategoria",
				notyfication.getNotificationCategory().getName());
		jsonObject.put("temat", notyfication.getSubject());
		jsonObject.put("tresc", notyfication.getContent());
		jsonObject.put("aktywnosci",
				getJsonArray(notyfication.getNotificationMessages()));

		if (notyfication.getUser1() != null) {
			jsonObject.put("pracownik_obsl",
					notyfication.getUser1().getName() + " "
							+ notyfication.getUser1().getSurname());
		}
		jsonObject.put("status",
				Status.ZGLOSZENIE_STATE.getText(notyfication.getStatus()));

		return jsonObject.toString();
	}

	private JSONArray getJsonArray(
			List<NotificationMessage> zgloszenieKomentarze)
			throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;

		for (NotificationMessage zglKom : zgloszenieKomentarze) {
			jsonObject = new JSONObject();
			jsonObject.put("id", zglKom.getId());
			jsonObject.put("data", zglKom.getAddDate().getTime());
			jsonObject.put("tresc", zglKom.getContent());
			String uzytkownikName = "";
			if (zglKom.getUser().getCompanyName() != null) {
				uzytkownikName = zglKom.getUser().getCompanyName();
			} else {
				uzytkownikName = zglKom.getUser().getName() + " "
						+ zglKom.getUser().getSurname();
			}
			jsonObject.put("uzytkownik", uzytkownikName);
			// jsonObject.put('typ', );

			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}

	public Integer dodajZgloszenie(
			NotificationRestData zgloszenieRequestData) {
		try {
			NotificationCategory kategoiaZgloszenia = null;
			Users uzytkownik = null;
			if (zgloszenieRequestData.idUser != null) {
				uzytkownik = userDAO
						.findEntityByID(zgloszenieRequestData.idUser);
			}

			if (zgloszenieRequestData.idKategoria != null) {
				kategoiaZgloszenia = notifocationCategoryDAO
						.findEntityByID(zgloszenieRequestData.idKategoria);
			}

			Notyfication zgloszenie = null;

			if (uzytkownik != null && kategoiaZgloszenia != null) {
				zgloszenie = new Notyfication();
				zgloszenie.setUser2(uzytkownik);
				zgloszenie.setContent(zgloszenieRequestData.tresc);
				zgloszenie.setSubject(zgloszenieRequestData.temat);
				zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.NOWE);
				zgloszenie.setNotificationNumber("2016/12/89");
				zgloszenie.setCreatDate(new Timestamp(System.currentTimeMillis()));
				zgloszenie.setNotificationCategory(kategoiaZgloszenia);

				zgloszenie = notyficationDAO.insert(zgloszenie);
			}
			return zgloszenie.getId();
		} catch (Exception e) {
			return null;
		}
	}

	public void dodajWiadomoscDoZgloszenia(
			NotificationRestData zgloszenieRequestData) throws AddressException, MessagingException {
		Notyfication zgloszenie = null;
		if (zgloszenieRequestData.idZgloszenia != null) {
			zgloszenie = notyficationDAO
					.findEntityByID(zgloszenieRequestData.idZgloszenia);
		}
		Users uzytkownik = null;
		if (zgloszenieRequestData.idUser != null) {
			uzytkownik = userDAO
					.findEntityByID(zgloszenieRequestData.idUser);
		}

		NotificationMessage zgloszenieKomentarz = new NotificationMessage();
		zgloszenieKomentarz.setContent(zgloszenieRequestData.tresc);
		zgloszenieKomentarz.setType("K");
		zgloszenieKomentarz
				.setAddDate(new Timestamp(System.currentTimeMillis()));
		zgloszenieKomentarz.setUser(uzytkownik);
		zgloszenieKomentarz.setNotyfication(zgloszenie);

		notificationMessageDAO.insert(zgloszenieKomentarz);
		if(zgloszenie.getUser1() != null)
		mailSender.sendMail("Nowa wiadomosc do zgloszenia nr : " + zgloszenie.getNotificationNumber(), "Dodanow wiadomosc do zgłoszenia..", zgloszenie.getUser1().getEmail());

	}

}
