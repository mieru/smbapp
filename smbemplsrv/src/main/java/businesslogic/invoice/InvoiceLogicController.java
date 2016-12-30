package businesslogic.invoice;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.invoice.InvoiceDAO;
import dbmenager.users.UsersDAO;
import dbmodel.Invoice;
import dbmodel.Users;
import restapi.invoice.InvoiceRestData;
import utils.status.Status;


@Stateless
@LocalBean
public class InvoiceLogicController {

	@EJB
	InvoiceDAO invoiceDAO;
	
	@EJB
	UsersDAO usersDAO;
	
	public Response getInvoicePdfFile(Integer invoiceID) {
	Invoice invoice = null;
	if(invoiceID!= null){
		invoice = invoiceDAO.findEntityByID(invoiceID);
	}
	File file = new File(System.getProperty("jboss.server.data.dir").toString() + invoice.getFilePath());
	ResponseBuilder response = Response.ok((Object) file);
	response.header("Content-Disposition", "attachment; filename=\"Faktura-" + invoice.getId() + ".pdf\"");
	return response.build();

}
	
	public String getUserInvoices(InvoiceRestData invoiceRestData){
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Invoice> invoiceCol = null;
		if(invoiceRestData.idUser != null){
			Users user = usersDAO.findEntityByID(Integer.parseInt(invoiceRestData.idUser));
			invoiceCol = user.getInvoices2();
		}else{
			invoiceCol = invoiceDAO.findAll();
		}
		
		for (Invoice invoice : invoiceCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", invoice.getId());
			jsonObject.put("daneKlienta", invoice.getCustomerData());
			jsonObject.put("daneWystawiajacego", invoice.getIssuingEntity());
			if(invoice.getCreationDate() != null)
				jsonObject.put("dataWystawienia", invoice.getCreationDate().getTime());
			jsonObject.put("listaTowarow", invoice.getCommodityList());
			jsonObject.put("numerFaktury",invoice.getInvoiceNumber());
			jsonObject.put("status",  Status.FAKTURA_STATE.getText(invoice.getStatus()));
			jsonObject.put("pdf",  Status.FAKTURA_STATE.SPRZEDARZ.equals(invoice.getStatus()));
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}

	public String getInvoiceById(InvoiceRestData invoiceRestData) throws JSONException {
		Invoice invoice = invoiceDAO.findEntityByID(invoiceRestData.idFaktury);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", invoice.getId());
		jsonObject.put("daneKlienta", invoice.getCustomerData());
		jsonObject.put("daneWystawiajacego", invoice.getIssuingEntity());
		if(invoice.getCreationDate() != null)
			jsonObject.put("dataWystawienia", invoice.getCreationDate().getTime());
		jsonObject.put("listaTowarow", invoice.getCommodityList());
		jsonObject.put("numerFaktury",invoice.getInvoiceNumber());
		jsonObject.put("status",  Status.FAKTURA_STATE.getText(invoice.getStatus()));
		
		Float netPriceSum = 0f;
		Float taxSum = 0f;
		Float grossPriceSum = 0f;
		
		JSONArray jsonArray = new JSONArray(invoice.getCommodityList());
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
}
