package facade.danefirmy;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

import dbmodel.DaneFirmy;
import query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;

@Stateless
@LocalBean
public class DaneFirmyFacade {

	@EJB
	DaneFimyEjbQueryController daneFimyEjbQueryController; 
	
	public String getDaneFirmy() throws JSONException {
			DaneFirmy dane = daneFimyEjbQueryController.findAll().iterator().next();
		
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", dane.getIdDanychFimy());
			jsonObject.put("nazwa", dane.getNazwa());
			jsonObject.put("nip", dane.getNip());
			jsonObject.put("regon", dane.getRegon());
			jsonObject.put("krs", dane.getKrs());
			jsonObject.put("ulica", dane.getUlica());
			jsonObject.put("nr_budynku", dane.getNrBudynku());
			jsonObject.put("nr_lokalu", dane.getNrLokalu());
			jsonObject.put("kod_pocztowy", dane.getKodPocztowy());
			jsonObject.put("miasto", dane.getMiasto());
			jsonObject.put("email", dane.getMail());
			jsonObject.put("telefon", dane.getTelefon());
		
		return jsonObject.toString();
	}
	
}
