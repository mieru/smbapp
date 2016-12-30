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
import dbmenager.user.UserDAO;
import dbmodel.Invoice;
import dbmodel.Users;
import restapi.invoice.InvoiceRestData;

@Stateless
@LocalBean
public class InvoiceLogicController {

	@EJB
	InvoiceDAO invoiceDAO;
	
	@EJB
	UserDAO userDAO;
	
	public Response getInvoicePdfFile(Integer invoiceId) {
	Invoice invoice = null;
	if(invoiceId != null){
		invoice = invoiceDAO.findEntityByID(invoiceId);
	}
	File file = new File(System.getProperty("jboss.server.data.dir").toString() + invoice.getFilePath());
	ResponseBuilder response = Response.ok((Object) file);
	response.header("Content-Disposition", "attachment; filename=\"Faktura-" + invoice.getId() + ".pdf\"");
	return response.build();

}
	
	public String getFakturyUzytkownika(InvoiceRestData invoiceRestData){
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Invoice> invoiceCol = null;
		if(invoiceRestData.idUser != null){
			Users user = userDAO.findEntityByID(Integer.parseInt(invoiceRestData.idUser));
			invoiceCol = user.getInvoices1();
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
			jsonObject.put("numerFaktury", invoice.getInvoiceNumber());
			jsonObject.put("status", invoice.getStatus());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}

	public String getFakturaById(InvoiceRestData fakturyResponseData) throws JSONException {
		Invoice invoice = invoiceDAO.findEntityByID(fakturyResponseData.idFaktury);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", invoice.getId());
		jsonObject.put("daneKlienta", invoice.getCustomerData());
		jsonObject.put("daneWystawiajacego", invoice.getIssuingEntity());
		jsonObject.put("dataWystawienia", invoice.getCreationDate().getTime());
		jsonObject.put("listaTowarow", invoice.getCommodityList());
		jsonObject.put("numerFaktury", invoice.getInvoiceNumber());
		jsonObject.put("status", invoice.getStatus());
		
		Float wartoscAllNetto = 0f;
		Float sumaPodatkuVat = 0f;
		Float wartoscAllBrutto = 0f;
		
		JSONArray jsonArray = new JSONArray(invoice.getCommodityList());
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
}
