package smbemplsrv.facade.zamowienie;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lowagie.text.DocumentException;

import smbemplsrv.command.ejbcontrol.zamowienie.ZamowienieEjbCommandController;
import smbemplsrv.command.ejbcontrol.zamowieniekomentarz.ZamowienieKomentarzEjbCommandController;
import smbemplsrv.command.restaction.tanzakcje.TanzakcjaRequestData;
import smbemplsrv.command.restaction.zamowienie.ZamowienieRequestCommandData;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.WiadomoscZamSprzedaz;
import smbemplsrv.dbmodel.ZamowienieSprzedaz;
import smbemplsrv.facade.sprzedaz.SprzedarzFacade;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.ejbcontrol.zamowienie.ZamowienieEjbQueryController;
import smbemplsrv.query.restaction.zamowienie.ZamowienieRequestQueryData;
import utils.MailSender;
import utils.status.Status;


@Stateless
@LocalBean
public class ZamowienieFacade {

	@EJB
	ZamowienieEjbCommandController zamowienieEjbCommandController;
	
	@EJB
	ZamowienieEjbQueryController zamowienieEjbQueryController;
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	
	@EJB
	ZamowienieKomentarzEjbCommandController zamowienieKomentarzEjbCommandController;
	
	@EJB
	SprzedarzFacade sprzedarzFacade;
	
	@EJB
	MailSender mailSender;
	
	public String dodajWiadomoscDoZamowienia(ZamowienieRequestCommandData zamowienieRequestCommandData) throws JSONException, AddressException, MessagingException {
		JSONObject jsonObject = new JSONObject();
		Uzytkownik uzytkownik  = uzytkownicyEjbQueryController.findEntityByID(zamowienieRequestCommandData.idZamawiajacego);
		ZamowienieSprzedaz zamowienieSprzedaz = zamowienieEjbQueryController.findEntityByID(zamowienieRequestCommandData.idZamowienia);
		
		if(uzytkownik.getIdUser().equals(zamowienieSprzedaz.getUzytkownik2().getIdUser())){
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
		
		zamowienieKomentarzEjbCommandController.insert(wiadomoscZamSprzedaz);
		mailSender.sendMail("Nowa wiadomosc do zamowienia nr : " + zamowienieSprzedaz.getNumerZamowienia(), "Dodanow wiadomosc do zamowienia..", zamowienieSprzedaz.getUzytkownik1().getMail());
	}
	
	public String getZamowienia(ZamowienieRequestQueryData zamowienieRequestQueryData) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<ZamowienieSprzedaz> zamCol = null;
		if(zamowienieRequestQueryData.idUser != null){
			ZamowienieSprzedaz zamowienieSprzedaz = new ZamowienieSprzedaz();
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(zamowienieRequestQueryData.idUser);
			zamowienieSprzedaz.setUzytkownik1(uzytkownik);
			zamCol = uzytkownik.getZamowienieSprzedazs2();
		}else{
			zamCol = zamowienieEjbQueryController.findAll();
		}
		
		for (ZamowienieSprzedaz zamowienieSprzedaz : zamCol) {
			jsonObject = new JSONObject();
			
			jsonObject.put("id", zamowienieSprzedaz.getIdZamowieniaSprzedaz());
			jsonObject.put("dataZlozenia", zamowienieSprzedaz.getDataZlozenia().getTime());
			jsonObject.put("status", Status.ZAMOWIENIE_STATE.getText(zamowienieSprzedaz.getStatus()));
			if(zamowienieSprzedaz.getUzytkownik1() != null)
				jsonObject.put("klient", zamowienieSprzedaz.getUzytkownik1().getName() + " " +  zamowienieSprzedaz.getUzytkownik1().getSurname());
			jsonObject.put("numer_zam", zamowienieSprzedaz.getNumerZamowienia());
			jsonObject.put("dataZamkniecia",zamowienieSprzedaz.getDataZakonczenia());
			
			jsonArray.put(jsonObject);
		}
		
		
		return jsonArray.toString();
	}
	
	public String getZamowienieById(ZamowienieRequestQueryData zamowieniaRequestQueryData) throws JSONException {
			ZamowienieSprzedaz zamowienieSprzedaz = zamowienieEjbQueryController.findEntityByID(zamowieniaRequestQueryData.idZamowienia);
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
			
			if(zamowienieSprzedaz.getUzytkownik1() != null)
				jsonObject.put("klient", zamowienieSprzedaz.getUzytkownik1().getName() + " " +  zamowienieSprzedaz.getUzytkownik1().getSurname());
			
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

	public String zamknijZamowienie(ZamowienieRequestCommandData zamowienieRequestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		TanzakcjaRequestData tanzakcjaRequestData = new TanzakcjaRequestData();
		tanzakcjaRequestData.typ = "S";
		tanzakcjaRequestData.czyFaktura = zamowienieRequestData.czyFaktura;
		tanzakcjaRequestData.daneDoFaktury = zamowienieRequestData.daneDoFaktury;
		tanzakcjaRequestData.listaPoduktow = zamowienieRequestData.listaPoduktow;
		tanzakcjaRequestData.idPracownika = zamowienieRequestData.idUzytkownika;
		
		ZamowienieSprzedaz zamowienieSprzedaz = zamowienieEjbQueryController.findEntityByID(zamowienieRequestData.idZamowienia);
		sprzedarzFacade.addNew(tanzakcjaRequestData, zamowienieSprzedaz.getUzytkownik1());
		zamowienieSprzedaz.setDataZakonczenia(new Timestamp(System.currentTimeMillis()));
		zamowienieSprzedaz.setStatus(Status.ZAMOWIENIE_STATE.ZREALIZOWANE);
		zamowienieEjbCommandController.update(zamowienieSprzedaz);
		return "";
	}

	public String anulujZam(ZamowienieRequestCommandData zamowienieRequestData) {
		ZamowienieSprzedaz zamowienieSprzedaz = zamowienieEjbQueryController.findEntityByID(zamowienieRequestData.idZamowienia);
		zamowienieSprzedaz.setDataZakonczenia(new Timestamp(System.currentTimeMillis()));
		zamowienieSprzedaz.setStatus(Status.ZAMOWIENIE_STATE.ANULOWANE);
		zamowienieEjbCommandController.update(zamowienieSprzedaz);
		return "";
	}
	
	
}
