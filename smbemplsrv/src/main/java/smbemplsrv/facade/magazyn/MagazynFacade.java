package smbemplsrv.facade.magazyn;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.magazyn.MagazynEjbCommandController;
import smbemplsrv.command.restaction.katzgl.KatZglRequestData;
import smbemplsrv.command.restaction.magazyn.MagazynRequestData;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.dbmodel.Magazyn;
import smbemplsrv.query.ejbcontrol.magazyn.MagazynEjbQueryController;

@Stateless
@LocalBean
public class MagazynFacade {

	@EJB
	MagazynEjbCommandController magazynEjbCommandController;
	@EJB
	MagazynEjbQueryController magazynEjbQueryController;
	
	
	public String getMagzyny() throws JSONException, AddressException, MessagingException {
		List<Magazyn> magCol = magazynEjbQueryController.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (Magazyn mag : magCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", mag.getIdMagazynu());
			jsonObject.put("name", mag.getNazwa());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}


	public String dodajMagazyn(MagazynRequestData magazynRequestData) {
		JSONObject jsonObject = new JSONObject();
		Magazyn magazyn = new Magazyn();
		magazyn.setNazwa(magazynRequestData.name);
		
		magazyn = magazynEjbCommandController.insert(magazyn);
		
		jsonObject.put("id", magazyn.getIdMagazynu());
		jsonObject.put("name", magazyn.getNazwa());
		
		return jsonObject.toString();
	}



	
	public String usunMagazyn(MagazynRequestData magazynRequestData) throws JSONException, AddressException, MessagingException {
		Magazyn magazyn = magazynEjbQueryController.findEntityByID(magazynRequestData.id);
		magazynEjbCommandController.delete(magazyn);
		return "";
	}


	public String getMagazynById(smbemplsrv.query.restaction.magazyn.MagazynRequestData magazynRequestData) {
		JSONObject jsonObject = new JSONObject();
		Magazyn magazyn = magazynEjbQueryController.findEntityByID(magazynRequestData.idMagazynu);
		
		jsonObject.put("id", magazyn.getIdMagazynu());
		jsonObject.put("name", magazyn.getNazwa());
		
		return jsonObject.toString();
	}


	
}
