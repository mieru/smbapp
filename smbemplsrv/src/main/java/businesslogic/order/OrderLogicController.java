package businesslogic.order;


import java.io.IOException;
import java.sql.SQLException;
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

import com.lowagie.text.DocumentException;

import businesslogic.sale.SaleLogicController;
import businesslogic.systemmailconfiguration.MailSender;
import dbmenager.order.OrderDAO;
import dbmenager.ordermessage.OrderMessageDAO;
import dbmenager.users.UsersDAO;
import dbmodel.Order;
import dbmodel.OrderMessage;
import dbmodel.Users;
import restapi.order.OrderRestData;
import restapi.transaction.TransactionRestData;
import utils.status.Status;


@Stateless
@LocalBean
public class OrderLogicController {

	@EJB
	OrderDAO orderDAO;
	
	@EJB
	UsersDAO usersDAO;
	
	@EJB
	OrderMessageDAO orderMessageDAO;
	
	@EJB
	SaleLogicController saleLogicController;
	
	@EJB
	MailSender mailSender;
	
	public String addMessageToOrder(OrderRestData orderRestData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Users user  = usersDAO.findEntityByID(orderRestData.idZamawiajacego);
		Order order = orderDAO.findEntityByID(orderRestData.idZamowienia);
		
		if(user.getId().equals(order.getUser2().getId())){
			addMessage(user, order, orderRestData);
		}
		jsonObject.put("ok",true);
		
		return jsonObject.toString();
	}
	
	private void addMessage(Users user,Order order, OrderRestData orderRestData) throws AddressException, MessagingException{
		OrderMessage orderMessage = new OrderMessage();
		orderMessage.setContent(orderRestData.trescWiadomosci);
		orderMessage.setUser(user);
		orderMessage.setAddedDate(new Timestamp(System.currentTimeMillis()));
		orderMessage.setOrder(order);
		
		orderMessageDAO.insert(orderMessage);
		if(order.getUser1() != null)
		mailSender.sendMail("Nowa wiadomosc do zamowienia nr : " + order.getOrderNumber(), "Dodanow wiadomosc do zamowienia..", order.getUser1().getEmail());
	}
	
	public String getOrders(OrderRestData orderRestData) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Order> orders = null;
		if(orderRestData.idUser != null){
			Order order = new Order();
			Users user = usersDAO.findEntityByID(orderRestData.idUser);
			order.setUser1(user);
			orders = user.getOrders2();
		}else{
			orders = orderDAO.findAll();
		}
		
		for (Order order : orders) {
			jsonObject = new JSONObject();
			
			jsonObject.put("id", order.getId());
			jsonObject.put("dataZlozenia", order.getCreatedData().getTime());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(order.getStatus()));
			if(order.getUser1() != null)
				jsonObject.put("klient", order.getUser1().getName() + " " +  order.getUser1().getSurname());
			jsonObject.put("numer_zam", order.getOrderNumber());
			jsonObject.put("dataZamkniecia",order.getEndDate());
			
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
			
			if(order.getUser1() != null)
				jsonObject.put("klient", order.getUser1().getName() + " " +  order.getUser1().getSurname());
			
			Float netPriceSum = 0f;
			Float taxSum = 0f;
			Float grossPriceSum = 0f;
			
			JSONArray jsonArray = new JSONArray(order.getCommodityList());
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				netPriceSum += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
				grossPriceSum += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cbrutto").toString());
			}
			
			taxSum += grossPriceSum - netPriceSum;
			
			jsonObject.put("wartoscAllNetto", netPriceSum);
			jsonObject.put("sumaPodatkuVat", taxSum);
			jsonObject.put("wartoscAllBrutto", grossPriceSum);
			
			
			
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

	public String closeOrder(OrderRestData orderRestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		TransactionRestData transactionRestData = new TransactionRestData();
		transactionRestData.typ = "S";
		transactionRestData.czyFaktura = orderRestData.czyFaktura;
		transactionRestData.daneDoFaktury = orderRestData.daneDoFaktury;
		transactionRestData.listaPoduktow = orderRestData.listaPoduktow;
		transactionRestData.idPracownika = orderRestData.idUser;
		
		Order order = orderDAO.findEntityByID(orderRestData.idZamowienia);
		saleLogicController.addNew(transactionRestData, order.getUser1());
		order.setEndDate(new Timestamp(System.currentTimeMillis()));
		order.setStatus(Status.ZAMOWIENIE_STATE.ZREALIZOWANE);
		orderDAO.update(order);
		return "";
	}

	public String cancelOrder(OrderRestData orderRestData) {
		Order order = orderDAO.findEntityByID(orderRestData.idZamowienia);
		order.setEndDate(new Timestamp(System.currentTimeMillis()));
		order.setStatus(Status.ZAMOWIENIE_STATE.ANULOWANE);
		orderDAO.update(order);
		return "";
	}
	
	
}
