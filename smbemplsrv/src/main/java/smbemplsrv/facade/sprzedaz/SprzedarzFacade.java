package smbemplsrv.facade.sprzedaz;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lowagie.text.DocumentException;

import smbemplsrv.command.ejbcontrol.faktua.FakturaEjbCommandController;
import smbemplsrv.command.ejbcontrol.paragon.ParagonEjbCommandController;
import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.command.ejbcontrol.tranzakcje.TranzakcjaEjbCommandController;
import smbemplsrv.command.restaction.tanzakcje.TanzakcjaRequestData;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.dbmodel.FakturaSprzedazy;
import smbemplsrv.dbmodel.Paragon;
import smbemplsrv.dbmodel.Towar;
import smbemplsrv.dbmodel.Tranzakcje;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;
import smbemplsrv.query.ejbcontrol.faktura.FakturaEjbQueryController;
import smbemplsrv.query.ejbcontrol.produkt.ProduktEjbQueryController;
import smbemplsrv.query.ejbcontrol.tranzakcje.TranzakcjeEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import utils.faktpdfgenerator.FaktPdfGenerator;

@Stateless
@LocalBean
public class SprzedarzFacade {

	@EJB
	ProduktEjbCommandController produktEjbCommandController;
	@EJB
	DaneFimyEjbQueryController daneFimyEjbQueryController;
	@EJB
	ProduktEjbQueryController produktEjbQueryController;
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	@EJB
	TranzakcjaEjbCommandController tranzakcjaEjbCommandController;
	@EJB
	TranzakcjeEjbQueryController tranzakcjeEjbQueryController;
	@EJB
	FakturaEjbCommandController fakturaEjbCommandController;
	@EJB
	FakturaEjbQueryController fakturaEjbQueryController;
	@EJB
	ParagonEjbCommandController paragonEjbCommandController;
 	
	private StringBuilder response = new StringBuilder();

	public String addNew(TanzakcjaRequestData tanzakcjaRequestData, Uzytkownik klient) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		Tranzakcje tranzakcje = new Tranzakcje();
		tranzakcje.setTyp(tanzakcjaRequestData.typ);
		tranzakcje.setDataTanzakcji(new Timestamp(System.currentTimeMillis()));
		tranzakcje.setListaProduktow(tanzakcjaRequestData.listaPoduktow);
		Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(tanzakcjaRequestData.idPracownika);
		tranzakcje.setUzytkownik(uzytkownik);
		Float wartoscAllNetto = 0f;
		Float wartoscAllBrutto = 0f;
		
		JSONArray jsonArray = new JSONArray(tanzakcjaRequestData.listaPoduktow);
		if("S".equals(tanzakcjaRequestData.typ)){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				sprawdzIlosciMagazynowe(objecTmp);
			}
		}
		if(response.toString().isEmpty()){
			for(int i = 0; i < jsonArray.length(); i++){
				JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
				zmienStanMagazynowy(objecTmp, tanzakcjaRequestData.typ);
				Float wNetto = Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
				wartoscAllNetto += wNetto;
				Float vat = 1.00f + Float.parseFloat(objecTmp.getString("stawka_vat")) * 0.01f;
				wartoscAllBrutto += wNetto * vat;
			}
			
			tranzakcje.setKwota(wartoscAllBrutto.doubleValue());
			
			
			if((tanzakcjaRequestData.czyFaktura != null && tanzakcjaRequestData.czyFaktura) || "K".equals(tanzakcjaRequestData.typ)){
				creteFV(tanzakcjaRequestData, tranzakcje, wartoscAllNetto, wartoscAllBrutto, tanzakcjaRequestData.typ, klient);
			}else{
				createParagon(tanzakcjaRequestData, tranzakcje, wartoscAllNetto, wartoscAllBrutto, tanzakcjaRequestData.typ);
			}
			
			tranzakcjaEjbCommandController.insert(tranzakcje); 
		}
		JSONObject resp = new JSONObject();
		resp.put("resp", response.toString());
		return resp.toString();
	}


	private void createParagon(TanzakcjaRequestData tanzakcjaRequestData, Tranzakcje tranzakcje, Float wartoscAllNetto, Float wartoscAllBrutto, String typ) {
		Paragon paragon = new Paragon();
		paragon.setDataWystawienia(new Timestamp(System.currentTimeMillis()));
		paragon.setListaTowarow(tanzakcjaRequestData.listaPoduktow);
		
		DaneFirmy daneFirmy = daneFimyEjbQueryController.findAll().iterator().next();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
		.append(daneFirmy.getNazwa()+"\n")
		.append("NIP: " + daneFirmy.getNip()+"\n")
		.append(daneFirmy.getUlica() + " " +daneFirmy.getNrBudynku() + "/" + daneFirmy.getNrLokalu()+"\n")
		.append(daneFirmy.getKodPocztowy() + " " + daneFirmy.getMiasto()+"\n");
		
		paragon.setDaneWystawiajacego(stringBuilder.toString());
		Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(tanzakcjaRequestData.idPracownika);
		paragon.setUzytkownik(uzytkownik);
		
		paragon = paragonEjbCommandController.insert(paragon);
		tranzakcje.setParagon(paragon);
	}


	private void sprawdzIlosciMagazynowe(JSONObject objecTmp) {
		Towar towar = produktEjbQueryController.findEntityByID(objecTmp.getInt("id"));
		if(towar.getIloscWMagazynie() < objecTmp.getInt("ilosc")){
			response.append("Za mała ilosc " + towar.getNazwa() + "\n");
		}
	}


	private void creteFV(TanzakcjaRequestData tanzakcjaRequestData, Tranzakcje tranzakcje, Float wartoscAllNetto, Float wartoscAllBrutto, String typ, Uzytkownik klient) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		FakturaSprzedazy fakturaSprzedazy = new FakturaSprzedazy();
		fakturaSprzedazy.setListaTowarow(tranzakcje.getListaProduktow());
		fakturaSprzedazy.setDaneKlienta(tanzakcjaRequestData.daneDoFaktury);
		fakturaSprzedazy.setStatus(typ);
		if(klient != null)
			fakturaSprzedazy.setUzytkownik1(klient);
		
		Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(tanzakcjaRequestData.idPracownika);
		fakturaSprzedazy.setUzytkownik2(uzytkownik);
		fakturaSprzedazy.setDataWystawienia(new Timestamp(System.currentTimeMillis()));
		fakturaSprzedazy.setNumerFaktury(fakturaEjbQueryController.generujNumerZgloszenia());
		
		DaneFirmy daneFirmy = daneFimyEjbQueryController.findAll().iterator().next();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
		.append(daneFirmy.getNazwa()+"\n")
		.append("NIP: " + daneFirmy.getNip()+"\n")
		.append(daneFirmy.getUlica() + " " +daneFirmy.getNrBudynku() + "/" + daneFirmy.getNrLokalu()+"\n")
		.append(daneFirmy.getKodPocztowy() + " " + daneFirmy.getMiasto()+"\n");
		
		fakturaSprzedazy.setDaneWystawiajacego(stringBuilder.toString());
		
		fakturaSprzedazy = fakturaEjbCommandController.insert(fakturaSprzedazy);
		
		if("S".equals(typ)){
			FaktPdfGenerator pdfGenerator =new FaktPdfGenerator();
			fakturaSprzedazy.setFilePath(pdfGenerator.generatePdf(fakturaSprzedazy, wartoscAllNetto, wartoscAllBrutto));
			fakturaEjbCommandController.update(fakturaSprzedazy);
		}
		
		tranzakcje.setFakturaSprzedazy(fakturaSprzedazy);
		
	}


	private void zmienStanMagazynowy(JSONObject objecTmp, String typ) {
		Towar towar = produktEjbQueryController.findEntityByID(objecTmp.getInt("id"));
		if("S".equals(typ)){
			towar.setIloscWMagazynie(towar.getIloscWMagazynie() - objecTmp.getInt("ilosc"));
		}else{
			towar.setIloscWMagazynie(towar.getIloscWMagazynie() + objecTmp.getInt("ilosc"));
		}
			produktEjbCommandController.update(towar);
	}

}
