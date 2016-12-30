package businesslogic.companyinfo;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONObject;

import dbmenager.companyinfo.CompanyInfoDAO;
import dbmodel.CompanyInfo;
import restapi.companyinfo.CompanyInfoRestData;

@Stateless
@LocalBean
public class CompanyInfoLogicController {

	@EJB
	CompanyInfoDAO companyInfoDAO;


	public String getCompanyInfo() {
		JSONObject jsonObject = new JSONObject();
		CompanyInfo companyInfo = null;
		try {
			companyInfo = companyInfoDAO.findAll().iterator().next();
			jsonObject.put("id", companyInfo.getId());
			jsonObject.put("name", companyInfo.getName());
			jsonObject.put("nip", companyInfo.getNip());
			jsonObject.put("ulica", companyInfo.getStreet());
			jsonObject.put("nr_bud", companyInfo.getBuildingNumber());
			jsonObject.put("nr_lok", companyInfo.getApartamentNumber());
			jsonObject.put("kod_pocz", companyInfo.getPostCode());
			jsonObject.put("miasto", companyInfo.getStreet());
			jsonObject.put("mail", companyInfo.getEmail());
			jsonObject.put("telefon", companyInfo.getPhone());
		} catch (Exception e) {
		}
		return jsonObject.toString();

	}

	public String saveCompanyInfo(CompanyInfoRestData companyInfoRestData) {
		CompanyInfo dane = new CompanyInfo();

		try {
			dane = companyInfoDAO.findAll().iterator().next();
			fillCompanyInfo(dane, companyInfoRestData);
		} catch (Exception e) {
			fillCompanyInfo(dane, companyInfoRestData);
			companyInfoDAO.insert(dane);
		}
		return "";
	}
	private void fillCompanyInfo(CompanyInfo companyInfo, CompanyInfoRestData companyInfoRestData) {
		companyInfo.setName(companyInfoRestData.name);
		companyInfo.setNip(companyInfoRestData.nip);
		companyInfo.setEmail(companyInfoRestData.mail);
		companyInfo.setPhone(companyInfoRestData.telefon);
		companyInfo.setStreet(companyInfoRestData.ulica);
		companyInfo.setBuildingNumber(companyInfoRestData.nrBud);
		companyInfo.setApartamentNumber(companyInfoRestData.nrLok);
		companyInfo.setPostCode(companyInfoRestData.kodPocztowy);
		companyInfo.setCity(companyInfoRestData.miasto);
	}

}
