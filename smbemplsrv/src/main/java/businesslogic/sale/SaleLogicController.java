package businesslogic.sale;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lowagie.text.DocumentException;

import businesslogic.invoice.InvoicePdfGenerator;
import dbmenager.commodity.CommodityDAO;
import dbmenager.companyinfo.CompanyInfoDAO;
import dbmenager.invoice.InvoiceDAO;
import dbmenager.receipt.ReceiptDAO;
import dbmenager.transaction.TransactionDAO;
import dbmenager.users.UsersDAO;
import dbmodel.Commodity;
import dbmodel.CompanyInfo;
import dbmodel.Invoice;
import dbmodel.Receipt;
import dbmodel.Transaction;
import dbmodel.Users;
import restapi.transaction.TransactionRestData;

@Stateless
@LocalBean
public class SaleLogicController {

	@EJB
	CompanyInfoDAO companyInfoDAO;
	@EJB
	CommodityDAO commodityDAO;
	@EJB
	UsersDAO usersDAO;
	@EJB
	TransactionDAO transactionDAO;
	@EJB
	InvoiceDAO invoiceDAO;
	@EJB
	ReceiptDAO receiptDAO;
 	
	private StringBuilder response = new StringBuilder();

	public String addNew(TransactionRestData transactionRestData, Users customer) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		Transaction transaction = new Transaction();
		transaction.setType(transactionRestData.typ);
		transaction.setTranzactionDate(new Timestamp(System.currentTimeMillis()));
		transaction.setCommodityList(transactionRestData.listaPoduktow);
		Users user = usersDAO.findEntityByID(transactionRestData.idPracownika);
		transaction.setUser(user);
		Float netPriceSum = 0f;
		Float grossProceSum = 0f;
		
		JSONArray jsonArray = new JSONArray(transactionRestData.listaPoduktow);
		if("S".equals(transactionRestData.typ)){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				checkWarehouseAmount(objecTmp);
			}
		}
		if(response.toString().isEmpty()){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				changeWarehouseAmount(objecTmp, transactionRestData.typ);
				Float netPrice = Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
				netPriceSum += netPrice;
				Float vat = 1.00f + Float.parseFloat(objecTmp.getString("stawka_vat")) * 0.01f;
				grossProceSum += netPrice * vat;
			}
			
			transaction.setSum(grossProceSum.doubleValue());
			
			
			if((transactionRestData.czyFaktura != null && transactionRestData.czyFaktura) || "K".equals(transactionRestData.typ)){
				creteInvoice(transactionRestData, transaction, netPriceSum, grossProceSum, transactionRestData.typ, customer);
			}else{
				createReceipt(transactionRestData, transaction, netPriceSum, grossProceSum, transactionRestData.typ);
			}
			
			transactionDAO.insert(transaction); 
		}
		JSONObject resp = new JSONObject();
		resp.put("resp", response.toString());
		return resp.toString();
	}


	private void createReceipt(TransactionRestData transactionRestData, Transaction transaction , Float netPriceSum, Float grossPriceSum, String typ) {
		Receipt receipt = new Receipt();
		receipt.setCreationDate(new Timestamp(System.currentTimeMillis()));
		receipt.setCommodityList(transactionRestData.listaPoduktow);
		
		CompanyInfo companyInfo = companyInfoDAO.findAll().iterator().next();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
		.append(companyInfo.getName()+"\n")
		.append("NIP: " + companyInfo.getNip()+"\n")
		.append(companyInfo.getStreet() + " " +companyInfo.getBuildingNumber() + "/" + companyInfo.getApartamentNumber()+"\n")
		.append(companyInfo.getPostCode() + " " + companyInfo.getCity()+"\n");
		
		receipt.setIssuingEntity(stringBuilder.toString());
		Users user = usersDAO.findEntityByID(transactionRestData.idPracownika);
		receipt.setUser(user);
		
		receipt = receiptDAO.insert(receipt);
		transaction.setReceipt(receipt);
	}


	private void checkWarehouseAmount(JSONObject objecTmp) {
		Commodity commodity = commodityDAO.findEntityByID(objecTmp.getInt("id"));
		if(commodity.getAmountInWarehouse() < objecTmp.getInt("ilosc")){
			response.append("Za mała ilosc " + commodity.getName() + "\n");
		}
	}


	private void creteInvoice(TransactionRestData transactionRestData, Transaction transaction, Float netPriceSum, Float grossPricesum, String type, Users customer) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		Invoice invoice = new Invoice();
		invoice.setCommodityList(transaction.getCommodityList());
		invoice.setCustomerData(transactionRestData.daneDoFaktury);
		invoice.setStatus(type);
		if(customer != null)
			invoice.setUser1(customer);
		
		Users user = usersDAO.findEntityByID(transactionRestData.idPracownika);
		invoice.setUser2(user);
		invoice.setCreationDate(new Timestamp(System.currentTimeMillis()));
		invoice.setInvoiceNumber(invoiceDAO.generujNumerZgloszenia());
		
		CompanyInfo companyInfo = companyInfoDAO.findAll().iterator().next();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
		.append(companyInfo.getName()+"\n")
		.append("NIP: " + companyInfo.getNip()+"\n")
		.append(companyInfo.getStreet() + " " +companyInfo.getBuildingNumber() + "/" + companyInfo.getApartamentNumber()+"\n")
		.append(companyInfo.getPostCode() + " " + companyInfo.getCity()+"\n");
		
		
		invoice.setIssuingEntity(stringBuilder.toString());
		
		invoice = invoiceDAO.insert(invoice);
		
		if("S".equals(type)){
			InvoicePdfGenerator invoicePdfGenerator =new InvoicePdfGenerator();
			invoice.setFilePath(invoicePdfGenerator.generatePdf(invoice, netPriceSum, grossPricesum));
			invoiceDAO.update(invoice);
		}
		
		transaction.setInvoice(invoice);
		
	}


	private void changeWarehouseAmount(JSONObject objecTmp, String typ) {
		Commodity commodity = commodityDAO.findEntityByID(objecTmp.getInt("id"));
		if("S".equals(typ)){
			commodity.setAmountInWarehouse(commodity.getAmountInWarehouse() - objecTmp.getInt("ilosc"));
		}else{
			commodity.setAmountInWarehouse(commodity.getAmountInWarehouse() + objecTmp.getInt("ilosc"));
		}
			commodityDAO.update(commodity);
	}

}
