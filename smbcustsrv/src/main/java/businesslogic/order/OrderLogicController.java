package businesslogic.order;

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

import dbmenager.order.OrderDAO;
import dbmenager.ordermessage.OrderMessageDAO;
import dbmenager.user.UserDAO;
import dbmodel.Order;
import dbmodel.OrderMessage;
import dbmodel.Users;
import mailmenager.MailSender;
import restapi.order.OrderRestData;
import utils.Status;

@Stateless
@LocalBean
public class OrderLogicController {

	@EJB
	OrderDAO orderDAO;
	
	@EJB
	UserDAO userDAO;
	
	@EJB
	OrderMessageDAO orderMessageDAO;
	
	@EJB
	MailSender mailSender;
	
	public String addNewOrder(OrderRestData zamowienieRequestCommandData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		
		Order zamowienieSprzedaz = new Order();
		zamowienieSprzedaz.setDeliveryAddress(zamowienieRequestCommandData.adresDostawy);
		zamowienieSprzedaz.setIsInvoice(zamowienieRequestCommandData.czyFaktura);
		if(zamowienieSprzedaz.getIsInvoice() == null){
			zamowienieSprzedaz.setIsInvoice(false);
		}else{
		    zamowienieSprzedaz.setDataToInvoice(zamowienieRequestCommandData.daneDoFaktury);
		}
		
		zamowienieSprzedaz.setCreatedData(new Timestamp(System.currentTimeMillis()));
		zamowienieSprzedaz.setCommodityList(zamowienieRequestCommandData.listaPoduktow);
		zamowienieSprzedaz.setStatus(Status.USER_STATE.NEW);
		zamowienieSprzedaz.setOrderNumber("2016/12/89");
		
		Users uzytkownik  = userDAO.findEntityByID(zamowienieRequestCommandData.idZamawiajacego);
		zamowienieSprzedaz.setUser1(uzytkownik);
		
		zamowienieSprzedaz = orderDAO.insert(zamowienieSprzedaz);
		if(zamowienieRequestCommandData.trescWiadomosci != null)
			dodajWiadomosc(uzytkownik, zamowienieSprzedaz, zamowienieRequestCommandData);
		
		jsonObject.put("ok",true);
		 
		zamowienieRequestCommandData.idZamowienia = zamowienieSprzedaz.getId();
		return jsonObject.toString();
		
	}
	
	public String addMessageToOrder(OrderRestData zamowienieRequestCommandData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Users uzytkownik  = userDAO.findEntityByID(zamowienieRequestCommandData.idZamawiajacego);
		Order zamowienieSprzedaz = orderDAO.findEntityByID(zamowienieRequestCommandData.idZamowienia);
		
		if(uzytkownik.getId().equals(zamowienieSprzedaz.getUser1().getId())){
			dodajWiadomosc(uzytkownik, zamowienieSprzedaz, zamowienieRequestCommandData);
		}
		jsonObject.put("ok",true);
		
		return jsonObject.toString();
	}
	
	private void dodajWiadomosc(Users uzytkownik,Order zamowienieSprzedaz, OrderRestData zamowienieRequestData) throws AddressException, MessagingException{
		OrderMessage wiadomoscZamSprzedaz = new OrderMessage();
		wiadomoscZamSprzedaz.setContent(zamowienieRequestData.trescWiadomosci);
		wiadomoscZamSprzedaz.setUser(uzytkownik);
		wiadomoscZamSprzedaz.setAddedDate(new Timestamp(System.currentTimeMillis()));
		wiadomoscZamSprzedaz.setOrder(zamowienieSprzedaz);
		
		orderMessageDAO.insert(wiadomoscZamSprzedaz);
		if(zamowienieSprzedaz.getUser1() != null)
			mailSender.sendMail("Nowa wiadomosc do zamowienia nr : " + zamowienieSprzedaz.getOrderNumber(), "Dodanow wiadomosc do zamowienia..", zamowienieSprzedaz.getUser2().getEmail());
	}
	
	public String getOrders(OrderRestData zamowieniaRequestQueryData) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Order> zamCol = null;
		if(zamowieniaRequestQueryData.idUser != null){
			Order zamowienieSprzedaz = new Order();
			Users uzytkownik = userDAO.findEntityByID(Integer.parseInt(zamowieniaRequestQueryData.idUser));
			zamowienieSprzedaz.setUser1(uzytkownik);
			zamCol = uzytkownik.getOrders1();
		}else{
			zamCol = orderDAO.findAll();
		}
		
		for (Order zamowienieSprzedaz : zamCol) {
			jsonObject = new JSONObject();
			
			jsonObject.put("id", zamowienieSprzedaz.getId());
			jsonObject.put("dataZlozenia", zamowienieSprzedaz.getCreatedData().getTime());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(zamowienieSprzedaz.getStatus()));
			if(zamowienieSprzedaz.getUser2() != null)
				jsonObject.put("pracownik", zamowienieSprzedaz.getUser2().getName() + " " +  zamowienieSprzedaz.getUser2().getSurname());
			jsonObject.put("numer_zam", zamowienieSprzedaz.getOrderNumber());
			
			jsonArray.put(jsonObject);
		}
		
		
		return jsonArray.toString();
	}
	
	public String getOrderById(OrderRestData orderRestData) throws JSONException {
			Order order = orderDAO.findEntityByID(orderRestData.idZamowienia);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", order.getId());
			jsonObject.put("czyFaktura", order.getIsInvoice());
			jsonObject.put("daneDoFaktury", order.getDataToInvoice());
			jsonObject.put("listaProd", order.getCommodityList());
			if(order.getEndDate() != null)
				jsonObject.put("dataZakonczenia", order.getEndDate().getTime());
			jsonObject.put("dataZlozenia", order.getCreatedData().getTime());
			jsonObject.put("adresDostawy", order.getDeliveryAddress());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(order.getStatus()));
			jsonObject.put("aktywnosci", getJsonArray(order.getOrderMessages()));
			
			if(order.getUser2() != null)
				jsonObject.put("pracownik", order.getUser2().getName() + " " +  order.getUser2().getSurname());
			
			Float wartoscAllNetto = 0f;
			Float sumaPodatkuVat = 0f;
			Float wartoscAllBrutto = 0f;
			
			JSONArray jsonArray = new JSONArray(order.getCommodityList());
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				wartoscAllNetto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
				wartoscAllBrutto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cbrutto").toString());
			}
			
			sumaPodatkuVat += wartoscAllBrutto - wartoscAllNetto;
			
			jsonObject.put("wartoscAllNetto", wartoscAllNetto);
			jsonObject.put("sumaPodatkuVat", sumaPodatkuVat);
			jsonObject.put("wartoscAllBrutto", wartoscAllBrutto);
			
			
			
		return jsonObject.toString();
	}

	private JSONArray getJsonArray(List<OrderMessage> orderMessages) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (OrderMessage orderMessage : orderMessages) {
			jsonObject = new JSONObject();
			jsonObject.put("id", orderMessage.getId());
			jsonObject.put("data", orderMessage.getAddedDate().getTime());
			jsonObject.put("tresc", orderMessage.getContent());
			String userName = "";
			if(orderMessage.getUser().getCompanyName() != null){
				userName = orderMessage.getUser().getCompanyName();
			}else{
				userName = orderMessage.getUser().getName() + " " + orderMessage.getUser().getSurname();
			}
			jsonObject.put("uzytkownik", userName);
			
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}
	
	
}
