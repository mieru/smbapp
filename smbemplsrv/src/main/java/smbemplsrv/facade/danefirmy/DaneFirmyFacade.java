package smbemplsrv.facade.danefirmy;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.danefirmy.DaneFirmyEjbCommandController;
import smbemplsrv.command.restaction.danefirmy.DaneFirmyRequestData;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;

@Stateless
@LocalBean
public class DaneFirmyFacade {

	@EJB
	DaneFirmyEjbCommandController daneFirmyEjbCommandController;

	@EJB
	DaneFimyEjbQueryController daneFimyEjbQueryController;

	public String getDaneFirmy() {
		JSONObject jsonObject = new JSONObject();
		DaneFirmy dane = null;
		try {
			dane = daneFimyEjbQueryController.findAll().iterator().next();
			jsonObject.put("id", dane.getIdDanychFimy());
			jsonObject.put("name", dane.getNazwa());
			jsonObject.put("nip", dane.getNip());
			jsonObject.put("regon", dane.getRegon());
			jsonObject.put("krs", dane.getKrs());
			jsonObject.put("ulica", dane.getUlica());
			jsonObject.put("nr_bud", dane.getNrBudynku());
			jsonObject.put("nr_lok", dane.getNrLokalu());
			jsonObject.put("kod_pocz", dane.getKodPocztowy());
			jsonObject.put("miasto", dane.getMiasto());
			jsonObject.put("mail", dane.getMail());
			jsonObject.put("telefon", dane.getTelefon());
		} catch (Exception e) {
		}
		return jsonObject.toString();

	}

	public String zapiszDaneFirmy(DaneFirmyRequestData daneFirmyRequestData) {
		DaneFirmy dane = new DaneFirmy();

		try {
			dane = daneFimyEjbQueryController.findAll().iterator().next();
			fillDaneFirmy(dane, daneFirmyRequestData);
		} catch (Exception e) {
			fillDaneFirmy(dane, daneFirmyRequestData);
			daneFirmyEjbCommandController.insert(dane);
		}
		return "";
	}
	private void fillDaneFirmy(DaneFirmy dane,
			DaneFirmyRequestData daneFirmyRequestData) {
		dane.setNazwa(daneFirmyRequestData.name);
		dane.setNip(daneFirmyRequestData.nip);
		dane.setRegon(daneFirmyRequestData.regon);
		dane.setKrs(daneFirmyRequestData.krs);
		dane.setMail(daneFirmyRequestData.mail);
		dane.setTelefon(daneFirmyRequestData.telefon);
		dane.setUlica(daneFirmyRequestData.ulica);
		dane.setNrBudynku(daneFirmyRequestData.nrBud);
		dane.setNrLokalu(daneFirmyRequestData.nrLok);
		dane.setKodPocztowy(daneFirmyRequestData.kodPocztowy);
		dane.setMiasto(daneFirmyRequestData.miasto);
	}

}
