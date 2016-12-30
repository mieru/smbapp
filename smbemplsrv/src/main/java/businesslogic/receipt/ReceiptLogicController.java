package businesslogic.receipt;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.receipt.ReceiptDAO;
import dbmenager.users.UsersDAO;
import dbmodel.Receipt;
import dbmodel.Transaction;
import dbmodel.Users;
import restapi.receipt.ReceiptRestData;


@Stateless
@LocalBean
public class ReceiptLogicController {

	@EJB
	ReceiptDAO receiptDAO;
	
	@EJB
	UsersDAO usersDAO;
	
	public String getUserReceipts(ReceiptRestData receiptRestData){
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Receipt> receipts = null;
		if(receiptRestData.idUser != null){
			Users user = usersDAO.findEntityByID(Integer.parseInt(receiptRestData.idUser));
			receipts = user.getReceipts();
		}else{
			receipts = receiptDAO.findAll();
		}
		for (Receipt receipt : receipts) {
			jsonObject = new JSONObject();
			jsonObject.put("id", receipt.getId());
			jsonObject.put("daneWystawiajacego", receipt.getIssuingEntity());
			jsonObject.put("dataWystawienia", receipt.getCreationDate().getTime());
			jsonObject.put("listaProd", receipt.getCommodityList());
			List<Transaction> transactions = receipt.getTransactions();
			if(transactions!= null && !transactions.isEmpty()){
				jsonObject.put("kwota", transactions.iterator().next().getSum()); 
			}
				
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}

	public String getReceiptById(ReceiptRestData receiptRestData) throws JSONException {
		Receipt receipt = receiptDAO.findEntityByID(receiptRestData.idParagonu);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", receipt.getId());
		jsonObject.put("daneWystawiajacego", receipt.getIssuingEntity());
		jsonObject.put("dataWystawienia", receipt.getCreationDate().getTime());
		jsonObject.put("listaProd", receipt.getCommodityList());
		
		Float netPriceSum = 0f;
		Float taxSum = 0f;
		Float grossProceSum = 0f;
		
		JSONArray jsonArray = new JSONArray(receipt.getCommodityList());
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
			netPriceSum += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
			grossProceSum += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cbrutto").toString());
		}
		
		taxSum += grossProceSum - netPriceSum;
		
		jsonObject.put("wartoscAllNetto", netPriceSum);
		jsonObject.put("sumaPodatkuVat", taxSum);
		jsonObject.put("wartoscAllBrutto", grossProceSum);
		
		
		
	return jsonObject.toString();
}
}
