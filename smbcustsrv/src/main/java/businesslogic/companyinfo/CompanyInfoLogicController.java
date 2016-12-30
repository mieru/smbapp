package businesslogic.companyinfo;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import dbmenager.companyinfo.CompanyInfoDAO;
import dbmodel.CompanyInfo;

@Stateless
@LocalBean
public class CompanyInfoLogicController {

	@EJB
	CompanyInfoDAO companyInfoDAO; 
	
	public String getCompanyInfo() throws JSONException {
			CompanyInfo companyInfo = companyInfoDAO.findAll().iterator().next();
		
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", companyInfo.getId());
			jsonObject.put("nazwa", companyInfo.getName());
			jsonObject.put("nip", companyInfo.getNip());
			jsonObject.put("ulica", companyInfo.getStreet());
			jsonObject.put("nr_budynku", companyInfo.getBuildingNumber());
			jsonObject.put("nr_lokalu", companyInfo.getApartamentNumber());
			jsonObject.put("kod_pocztowy", companyInfo.getPostCode());
			jsonObject.put("miasto", companyInfo.getCity());
			jsonObject.put("email", companyInfo.getEmail());
			jsonObject.put("telefon", companyInfo.getPhone());
		
		return jsonObject.toString();
	}
	
}
