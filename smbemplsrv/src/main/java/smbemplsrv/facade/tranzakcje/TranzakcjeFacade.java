package smbemplsrv.facade.tranzakcje;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.faktua.FakturaEjbCommandController;
import smbemplsrv.command.ejbcontrol.paragon.ParagonEjbCommandController;
import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.command.ejbcontrol.tranzakcje.TranzakcjaEjbCommandController;
import smbemplsrv.dbmodel.Tranzakcje;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.danefirmy.DaneFimyEjbQueryController;
import smbemplsrv.query.ejbcontrol.faktura.FakturaEjbQueryController;
import smbemplsrv.query.ejbcontrol.produkt.ProduktEjbQueryController;
import smbemplsrv.query.ejbcontrol.tranzakcje.TranzakcjeEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.restaction.tranzakcje.TranzakcjeRequestQueryData;
import utils.status.Status;

@Stateless
@LocalBean
public class TranzakcjeFacade {

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
 	
	public String getTranzakcje(TranzakcjeRequestQueryData tranzakcjeRequestQueryData) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Tranzakcje> parCol = null;
		if(tranzakcjeRequestQueryData.idUser != null){
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(tranzakcjeRequestQueryData.idUser);
			parCol = uzytkownik.getTranzakcjes();
		}else{
			parCol = tranzakcjeEjbQueryController.findAll();
		}
		for (Tranzakcje tranzakcja : parCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", tranzakcja.getIdTranzakcji());
			jsonObject.put("typ", Status.FAKTURA_STATE.getText(tranzakcja.getTyp()));
			if(tranzakcja.getFakturaSprzedazy() != null)
				jsonObject.put("nr_faktury", tranzakcja.getFakturaSprzedazy().getNumerFaktury());
			jsonObject.put("dataWystawienia", tranzakcja.getDataTanzakcji().getTime());
			jsonObject.put("kwota", tranzakcja.getKwota());
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}


	public String getTranzakcjaById(TranzakcjeRequestQueryData tranzakcjeRequestQueryData) {
		JSONObject jsonObject = null;
		
		Tranzakcje tranzakcja = tranzakcjeEjbQueryController.findEntityByID(tranzakcjeRequestQueryData.idTranzakcji);
			jsonObject = new JSONObject();
			jsonObject.put("id", tranzakcja.getIdTranzakcji());
			jsonObject.put("dataWystawienia", tranzakcja.getDataTanzakcji().getTime());
			jsonObject.put("kwota", tranzakcja.getKwota());
			
		return jsonObject.toString();
	}



}
