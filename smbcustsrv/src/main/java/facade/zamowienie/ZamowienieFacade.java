package facade.zamowienie;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.wiadomosczamsprz.WiadZamSprzedEjbCommandController;
import command.ejbcontrol.zamowieniesprzedaz.ZamowienieSprzedEjbCommandContoller;
import command.restaction.zamowieniesprzedaz.ZamowienieRequestCommandData;
import dbmodel.Uzytkownik;
import dbmodel.WiadomoscZamSprzedaz;
import dbmodel.ZamowienieSprzedaz;
import mailer.MailSender;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zamowieniasprzedarz.ZamowieniaSprzedEjbQueryController;
import query.restaction.zamowienia.ZamowieniaRequestQueryData;
import utils.status.Status;

@Stateless
@LocalBean
public class ZamowienieFacade {

	@EJB
	ZamowienieSprzedEjbCommandContoller zamowienieSprzedEjbCommandContoller;
	
	@EJB
	ZamowieniaSprzedEjbQueryController zamowieniaSprzedEjbQueryController;
	
	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;
	
	@EJB
	WiadZamSprzedEjbCommandController wiadZamSprzedEjbCommandController;
	
	@EJB
	MailSender mailSender;
	
	public String dodajNoweZamowienie(ZamowienieRequestCommandData zamowienieRequestCommandData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		
		ZamowienieSprzedaz zamowienieSprzedaz = new ZamowienieSprzedaz();
		zamowienieSprzedaz.setAdresDostawy(zamowienieRequestCommandData.adresDostawy);
		zamowienieSprzedaz.setCzyFaktura(zamowienieRequestCommandData.czyFaktura);
		if(zamowienieSprzedaz.getCzyFaktura() == null){
			zamowienieSprzedaz.setCzyFaktura(false);
		}else{
		    zamowienieSprzedaz.setDaneDoFaktury(zamowienieRequestCommandData.daneDoFaktury);
		}
		
		zamowienieSprzedaz.setDataZlozenia(new Timestamp(System.currentTimeMillis()));
		zamowienieSprzedaz.setListaProduktow(zamowienieRequestCommandData.listaPoduktow);
		zamowienieSprzedaz.setStatus(Status.USER_STATE.NEW);
		zamowienieSprzedaz.setNumerZamowienia(zamowieniaSprzedEjbQueryController.generujNumerZgloszenia());
		
		Uzytkownik uzytkownik  = uzytkownikEjbQueryController.findEntityByID(zamowienieRequestCommandData.idZamawiajacego);
		zamowienieSprzedaz.setUzytkownik1(uzytkownik);
		
		zamowienieSprzedaz = zamowienieSprzedEjbCommandContoller.insert(zamowienieSprzedaz);
		if(zamowienieRequestCommandData.trescWiadomosci != null)
			dodajWiadomosc(uzytkownik, zamowienieSprzedaz, zamowienieRequestCommandData);
		
		jsonObject.put("ok",true);
		 
		zamowienieRequestCommandData.idZamowienia = zamowienieSprzedaz.getIdZamowieniaSprzedaz();
		return jsonObject.toString();
		
	}
	
	public String dodajWiadomoscDoZamowienia(ZamowienieRequestCommandData zamowienieRequestCommandData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Uzytkownik uzytkownik  = uzytkownikEjbQueryController.findEntityByID(zamowienieRequestCommandData.idZamawiajacego);
		ZamowienieSprzedaz zamowienieSprzedaz = zamowieniaSprzedEjbQueryController.findEntityByID(zamowienieRequestCommandData.idZamowienia);
		
		if(uzytkownik.getIdUser().equals(zamowienieSprzedaz.getUzytkownik1().getIdUser())){
			dodajWiadomosc(uzytkownik, zamowienieSprzedaz, zamowienieRequestCommandData);
		}
		jsonObject.put("ok",true);
		
		return jsonObject.toString();
	}
	
	private void dodajWiadomosc(Uzytkownik uzytkownik,ZamowienieSprzedaz zamowienieSprzedaz, ZamowienieRequestCommandData zamowienieRequestData) throws AddressException, MessagingException{
		WiadomoscZamSprzedaz wiadomoscZamSprzedaz = new WiadomoscZamSprzedaz();
		wiadomoscZamSprzedaz.setTresc(zamowienieRequestData.trescWiadomosci);
		wiadomoscZamSprzedaz.setUzytkownik(uzytkownik);
		wiadomoscZamSprzedaz.setDataDodanie(new Timestamp(System.currentTimeMillis()));
		wiadomoscZamSprzedaz.setZamowienieSprzedaz(zamowienieSprzedaz);
		
		wiadZamSprzedEjbCommandController.insert(wiadomoscZamSprzedaz);
		mailSender.sendMail("Nowa wiadomosc do zamowienia nr : " + zamowienieSprzedaz.getNumerZamowienia(), "Dodanow wiadomosc do zamowienia..", zamowienieSprzedaz.getUzytkownik2().getMail());
	}
	
	public String getZamowienia(ZamowieniaRequestQueryData zamowieniaRequestQueryData) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<ZamowienieSprzedaz> zamCol = null;
		if(zamowieniaRequestQueryData.idUser != null){
			ZamowienieSprzedaz zamowienieSprzedaz = new ZamowienieSprzedaz();
			Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(Integer.parseInt(zamowieniaRequestQueryData.idUser));
			zamowienieSprzedaz.setUzytkownik1(uzytkownik);
			zamCol = uzytkownik.getZamowienieSprzedazs1();
		}else{
			zamCol = zamowieniaSprzedEjbQueryController.findAll();
		}
		
		for (ZamowienieSprzedaz zamowienieSprzedaz : zamCol) {
			jsonObject = new JSONObject();
			
			jsonObject.put("id", zamowienieSprzedaz.getIdZamowieniaSprzedaz());
			jsonObject.put("dataZlozenia", zamowienieSprzedaz.getDataZlozenia().getTime());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(zamowienieSprzedaz.getStatus()));
			if(zamowienieSprzedaz.getUzytkownik2() != null)
				jsonObject.put("pracownik", zamowienieSprzedaz.getUzytkownik2().getName() + " " +  zamowienieSprzedaz.getUzytkownik2().getSurname());
			jsonObject.put("numer_zam", zamowienieSprzedaz.getNumerZamowienia());
			
			jsonArray.put(jsonObject);
		}
		
		
		return jsonArray.toString();
	}
	
	public String getZamowienieById(ZamowieniaRequestQueryData zamowieniaRequestQueryData) throws JSONException {
			ZamowienieSprzedaz zamowienieSprzedaz = zamowieniaSprzedEjbQueryController.findEntityByID(zamowieniaRequestQueryData.idZamowienia);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", zamowienieSprzedaz.getIdZamowieniaSprzedaz());
			jsonObject.put("czyFaktura", zamowienieSprzedaz.getCzyFaktura());
			jsonObject.put("daneDoFaktury", zamowienieSprzedaz.getDaneDoFaktury());
			jsonObject.put("listaProd", zamowienieSprzedaz.getListaProduktow());
			if(zamowienieSprzedaz.getDataZakonczenia() != null)
			jsonObject.put("dataZakonczenia", zamowienieSprzedaz.getDataZakonczenia().getTime());
			jsonObject.put("dataZlozenia", zamowienieSprzedaz.getDataZlozenia().getTime());
			jsonObject.put("adresDostawy", zamowienieSprzedaz.getAdresDostawy());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(zamowienieSprzedaz.getStatus()));
			jsonObject.put("aktywnosci", getJsonArray(zamowienieSprzedaz.getWiadomoscZamSprzedazs()));
			
			Float wartoscAllNetto = 0f;
			Float sumaPodatkuVat = 0f;
			Float wartoscAllBrutto = 0f;
			
			JSONArray jsonArray = new JSONArray(zamowienieSprzedaz.getListaProduktow());
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				wartoscAllNetto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
				wartoscAllBrutto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cbrutto").toString());
			}
			
			sumaPodatkuVat += wartoscAllBrutto - wartoscAllNetto;
			
			jsonObject.put("wartoscAllNetto", wartoscAllNetto);
			jsonObject.put("sumaPodatkuVat", sumaPodatkuVat);
			jsonObject.put("wartoscAllBrutto", wartoscAllBrutto);
			
			
			
		return jsonObject.toString();
	}

	private JSONArray getJsonArray(List<WiadomoscZamSprzedaz> wiadomoscZamSprzedazs) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (WiadomoscZamSprzedaz wiadomoscZamSprzedaz : wiadomoscZamSprzedazs) {
			jsonObject = new JSONObject();
			jsonObject.put("id", wiadomoscZamSprzedaz.getIdWiadZamSprzed());
			jsonObject.put("data", wiadomoscZamSprzedaz.getDataDodanie().getTime());
			jsonObject.put("tresc", wiadomoscZamSprzedaz.getTresc());
			String uzytkownikName = "";
			if(wiadomoscZamSprzedaz.getUzytkownik().getCompanyName() != null){
				uzytkownikName = wiadomoscZamSprzedaz.getUzytkownik().getCompanyName();
			}else{
				uzytkownikName = wiadomoscZamSprzedaz.getUzytkownik().getName() + " " + wiadomoscZamSprzedaz.getUzytkownik().getSurname();
			}
			jsonObject.put("uzytkownik", uzytkownikName);
			
			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}
	
	
}
