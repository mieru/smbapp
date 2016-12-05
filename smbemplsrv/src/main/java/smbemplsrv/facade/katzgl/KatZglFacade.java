package smbemplsrv.facade.katzgl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.danefirmy.DaneFirmyEjbCommandController;
import smbemplsrv.command.ejbcontrol.katzgl.KatZglEjbCommandController;
import smbemplsrv.command.restaction.danefirmy.DaneFirmyRequestData;
import smbemplsrv.command.restaction.katzgl.KatZglRequestData;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;
import smbemplsrv.query.ejbcontrol.katzgl.KatZglEjbQueryController;

@Stateless
@LocalBean
public class KatZglFacade {

	@EJB
	KatZglEjbQueryController katZglEjbQueryController;

	@EJB
	KatZglEjbCommandController katZglEjbCommandController;

	public String getKategorieZglosznia() throws JSONException, AddressException, MessagingException {
		List<KategoiaZgloszenia> katCol = katZglEjbQueryController.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (KategoiaZgloszenia kategoiaZgloszenia : katCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", kategoiaZgloszenia.getIdKategrorii());
			jsonObject.put("name", kategoiaZgloszenia.getNazwa());
			jsonObject.put("czyKlient", kategoiaZgloszenia.getCzyKlient());
			jsonObject.put("czyMagazyn", kategoiaZgloszenia.getCzyMagazyn());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}
	
	public String dodajNowaKategorie(KatZglRequestData katZglRequestData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		KategoiaZgloszenia kategoiaZgloszenia = new KategoiaZgloszenia();
		kategoiaZgloszenia.setNazwa(katZglRequestData.name);
		if(katZglRequestData.czyKlient == null){
			kategoiaZgloszenia.setCzyKlient(false);
		}else{
			kategoiaZgloszenia.setCzyKlient(katZglRequestData.czyKlient);
		}
		if(katZglRequestData.czyMagazyn == null){
			kategoiaZgloszenia.setCzyMagazyn(false);
		}else{
			kategoiaZgloszenia.setCzyMagazyn(katZglRequestData.czyMagazyn);
		}
		
		
		kategoiaZgloszenia = katZglEjbCommandController.insert(kategoiaZgloszenia);
		
		jsonObject.put("id", kategoiaZgloszenia.getIdKategrorii());
		jsonObject.put("name", kategoiaZgloszenia.getNazwa());
		jsonObject.put("czyKlient", kategoiaZgloszenia.getCzyKlient());
		jsonObject.put("czyMagazyn", kategoiaZgloszenia.getCzyMagazyn());
		
		return jsonObject.toString();
	}
	
	public String usunKategorie(KatZglRequestData katZglRequestData) throws JSONException, AddressException, MessagingException {
		KategoiaZgloszenia kategoiaZgloszenia =katZglEjbQueryController.findEntityByID(katZglRequestData.id);
		
		katZglEjbCommandController.delete(kategoiaZgloszenia);
		return "";
	}


}
