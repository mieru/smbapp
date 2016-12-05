package smbemplsrv.facade.zgloszenia;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandController;
import smbemplsrv.command.ejbcontrol.zgloszeniekomentarz.ZgloszeniaKomentarzEjbCommandController;
import smbemplsrv.command.restaction.zgloszenie.ZgloszenieRequestData;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.Zgloszenie;
import smbemplsrv.dbmodel.ZgloszenieKomentarz;
import smbemplsrv.query.ejbcontrol.katzgl.KatZglEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;
import smbemplsrv.query.restaction.zgloszenia.ZgloszeniaRequestQueryData;
import utils.status.Status;

@Stateless
@LocalBean
public class ZgloszeniaFacade {

	@EJB
	ZgloszeniaEjbCommandController zgloszeniaEjbCommandController;
	
	@EJB
	ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;
	@EJB
	ZgloszeniaKomentarzEjbCommandController  zgloszeniaKomentarzEjbCommandController;
	
	@EJB
	KatZglEjbQueryController katZglEjbQueryController;
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	
	
	public String getZgloszenia(ZgloszeniaRequestQueryData zgloszeniaRequestQueryData) throws JSONException, IOException {
		KategoiaZgloszenia kategoiaZgloszenia = katZglEjbQueryController.findEntityByID(zgloszeniaRequestQueryData.idKategorii);
		Uzytkownik uzytkownik = null;
		List<Zgloszenie> colZgl = null;
		if(kategoiaZgloszenia.getCzyMagazyn() != null && kategoiaZgloszenia.getCzyMagazyn()){
			 colZgl = kategoiaZgloszenia.getZgloszenies();
		}else{
			 uzytkownik = uzytkownicyEjbQueryController.findEntityByID(zgloszeniaRequestQueryData.idUser);
			 colZgl = uzytkownik.getZgloszenies1();
		}
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (Zgloszenie zgloszenie : colZgl) {
			if(zgloszeniaRequestQueryData.idKategorii != null && !zgloszenie.getKategoiaZgloszenia().getIdKategrorii().equals(zgloszeniaRequestQueryData.idKategorii)) continue;
			jsonObject = new JSONObject();
			jsonObject.put("id", zgloszenie.getIdZgloszenia());
			jsonObject.put("numer_zgl", zgloszenie.getNumerZgloszenia());
			jsonObject.put("data_zgloszenia", zgloszenie.getDataZgloszenia().getTime());
			if(zgloszenie.getDataZamkniecia() != null)
			jsonObject.put("data_zamkniecia", zgloszenie.getDataZamkniecia().getTime());
			jsonObject.put("kategoria", zgloszenie.getKategoiaZgloszenia().getNazwa());
			jsonObject.put("temat", zgloszenie.getTemat());
			if( zgloszenie.getUzytkownik2() != null){
				jsonObject.put("klient", zgloszenie.getUzytkownik2().getName() +" "+  zgloszenie.getUzytkownik2().getSurname());
			}
			jsonObject.put("status", Status.ZGLOSZENIE_STATE.getText(zgloszenie.getStatus()));
			
			jsonArray.put(jsonObject);
		}
		
		return jsonArray.toString();
	}


	public String getZgloszeniaById(ZgloszeniaRequestQueryData zgloszeniaRequestQueryData) {
		JSONObject jsonObject = new JSONObject();
		Zgloszenie zgloszenie = zgloszeniaEjbQueryController.findEntityByID(zgloszeniaRequestQueryData.idZgloszenia);
		
			jsonObject.put("id", zgloszenie.getIdZgloszenia());
			jsonObject.put("numer_zgl", zgloszenie.getNumerZgloszenia());
			jsonObject.put("data_zgloszenia", zgloszenie.getDataZgloszenia().getTime());
			if(zgloszenie.getDataZamkniecia() != null)
			jsonObject.put("data_zamkniecia", zgloszenie.getDataZamkniecia().getTime());
			jsonObject.put("kategoria", zgloszenie.getKategoiaZgloszenia().getNazwa());
			jsonObject.put("temat", zgloszenie.getTemat());
			jsonObject.put("tresc", zgloszenie.getTresc());
			jsonObject.put("aktywnosci", getJsonArray(zgloszenie.getZgloszenieKomentarzs()));
			
			if( zgloszenie.getUzytkownik2() != null){
				jsonObject.put("klient", zgloszenie.getUzytkownik2().getName() +" "+  zgloszenie.getUzytkownik2().getSurname());
			}
			jsonObject.put("status", Status.ZGLOSZENIE_STATE.getText(zgloszenie.getStatus()));
			
		
		return jsonObject.toString();
	}
	
	public String dodajWiadomoscDoZgloszenia(ZgloszenieRequestData zgloszenieRequestData){
		Zgloszenie zgloszenie = null;
		if(zgloszenieRequestData.idZgloszenia != null){
			 zgloszenie = zgloszeniaEjbQueryController.findEntityByID(zgloszenieRequestData.idZgloszenia);
		}
		Uzytkownik uzytkownik = null;
		if(zgloszenieRequestData.idUser != null){
			 uzytkownik = uzytkownicyEjbQueryController.findEntityByID(zgloszenieRequestData.idUser);
		}
		
		ZgloszenieKomentarz zgloszenieKomentarz = new ZgloszenieKomentarz();
		zgloszenieKomentarz.setTresc(zgloszenieRequestData.tresc);
		zgloszenieKomentarz.setTyp("K");
		zgloszenieKomentarz.setDataDodania(new Timestamp(System.currentTimeMillis()));
		zgloszenieKomentarz.setUzytkownik(uzytkownik);
		zgloszenieKomentarz.setZgloszenie(zgloszenie);
		
		zgloszeniaKomentarzEjbCommandController.insert(zgloszenieKomentarz);
		return "";
	}
	
	private JSONArray getJsonArray(
			List<ZgloszenieKomentarz> zgloszenieKomentarze)
			throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;

		for (ZgloszenieKomentarz zglKom : zgloszenieKomentarze) {
			jsonObject = new JSONObject();
			jsonObject.put("id", zglKom.getIdKomentarzaZgloszenia());
			jsonObject.put("data", zglKom.getDataDodania().getTime());
			jsonObject.put("tresc", zglKom.getTresc());
			String uzytkownikName = "";
			if (zglKom.getUzytkownik().getCompanyName() != null) {
				uzytkownikName = zglKom.getUzytkownik().getCompanyName();
			} else {
				uzytkownikName = zglKom.getUzytkownik().getName() + " "
						+ zglKom.getUzytkownik().getSurname();
			}
			jsonObject.put("uzytkownik", uzytkownikName);
			// jsonObject.put('typ', );

			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}


	public String zamknijZgloszenie(ZgloszenieRequestData zgloszenieRequestData) {
		Zgloszenie zgloszenie = zgloszeniaEjbQueryController.findEntityByID(zgloszenieRequestData.idZgloszenia);
		zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.ZAKONCZONE);
		zgloszenie.setDataZamkniecia(new Timestamp(System.currentTimeMillis()));
		zgloszeniaEjbCommandController.update(zgloszenie);
		
		return "";
	}
	
	
}
