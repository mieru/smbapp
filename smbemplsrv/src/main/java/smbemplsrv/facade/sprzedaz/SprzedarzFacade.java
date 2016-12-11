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
import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.command.ejbcontrol.tranzakcje.TranzakcjaEjbCommandController;
import smbemplsrv.command.restaction.tanzakcje.TanzakcjaRequestData;
import smbemplsrv.dbmodel.DaneFirmy;
import smbemplsrv.dbmodel.FakturaSprzedazy;
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
	

	public String addNew(TanzakcjaRequestData tanzakcjaRequestData) throws AddressException, MessagingException, DocumentException, SQLException, IOException {
		Tranzakcje tranzakcje = new Tranzakcje();
		tranzakcje.setTyp("S");
		tranzakcje.setDataTanzakcji(new Timestamp(System.currentTimeMillis()));
		tranzakcje.setListaProduktow(tanzakcjaRequestData.listaPoduktow);

		Float wartoscAllNetto = 0f;
		Float wartoscAllBrutto = 0f;
		
		JSONArray jsonArray = new JSONArray(tanzakcjaRequestData.listaPoduktow);
		for(int i = 0; i < jsonArray.length(); i++){
			JSONObject objecTmp = new JSONObject(jsonArray.get(i).toString());
			wartoscAllNetto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cnetto").toString());
			wartoscAllBrutto += Float.valueOf(objecTmp.get("ilosc").toString()) * Float.valueOf(objecTmp.get("cbrutto").toString());
		}
		tranzakcje.setKwota(wartoscAllBrutto);
		
		
		if(tanzakcjaRequestData.czyFaktura != null && tanzakcjaRequestData.czyFaktura){
			FakturaSprzedazy fakturaSprzedazy = new FakturaSprzedazy();
			fakturaSprzedazy.setListaTowarow(tranzakcje.getListaProduktow());
			fakturaSprzedazy.setDaneKlienta(tanzakcjaRequestData.daneDoFaktury);
			fakturaSprzedazy.setStatus("S");
			
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
			
			FaktPdfGenerator pdfGenerator =new FaktPdfGenerator();
			fakturaSprzedazy.setFilePath(pdfGenerator.generatePdf(fakturaSprzedazy, wartoscAllNetto, wartoscAllBrutto));
			
			fakturaEjbCommandController.update(fakturaSprzedazy);
			tranzakcje.setFakturaSprzedazy(fakturaSprzedazy);
		}
		
		tranzakcjaEjbCommandController.insert(tranzakcje); 
		
		return "";
	}

}
