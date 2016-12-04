package smbemplsrv.facade.katprod;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.danefirmy.DaneFirmyEjbCommandController;
import smbemplsrv.command.ejbcontrol.katprod.KatProdEjbCommandController;
import smbemplsrv.command.restaction.danefirmy.DaneFirmyRequestData;
import smbemplsrv.command.restaction.katprod.KatProdRequestData;
import smbemplsrv.command.restaction.katzgl.KatZglRequestData;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.dbmodel.KategoriaTowar;
import smbemplsrv.query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;
import smbemplsrv.query.ejbcontrol.katprod.KatProdEjbQueryController;
import smbemplsrv.query.ejbcontrol.katzgl.KatZglEjbQueryController;

@Stateless
@LocalBean
public class KatProdFacade {

	@EJB
	KatProdEjbCommandController katProdEjbCommandController;

	@EJB
	KatProdEjbQueryController katProdEjbQueryController;

	
	public String getAll() {
		List<KategoriaTowar> katCol = katProdEjbQueryController.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (KategoriaTowar kategoriaTowar : katCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", kategoriaTowar.getIdKatTowar());
			jsonObject.put("name", kategoriaTowar.getNazwa());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	public String dodajNowaKategorie(KatProdRequestData katProdRequestData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		KategoriaTowar kategoriaTowar = new KategoriaTowar();
		kategoriaTowar.setNazwa(katProdRequestData.name);
		
		kategoriaTowar = katProdEjbCommandController.insert(kategoriaTowar);
		
		jsonObject.put("id", kategoriaTowar.getIdKatTowar());
		jsonObject.put("name", kategoriaTowar.getNazwa());
		
		return jsonObject.toString();
	}
	
	public String usunKategorie(KatProdRequestData katProdRequestData) throws JSONException, AddressException, MessagingException {
		KategoriaTowar kategoriaTowar = katProdEjbQueryController.findEntityByID(katProdRequestData.id);
		
		katProdEjbCommandController.delete(kategoriaTowar);
		return "";
	}


}
