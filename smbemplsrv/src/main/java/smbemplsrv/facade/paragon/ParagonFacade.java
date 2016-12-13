package smbemplsrv.facade.paragon;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.paragon.ParagonEjbCommandController;
import smbemplsrv.dbmodel.Paragon;
import smbemplsrv.dbmodel.Tranzakcje;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.paragon.ParagonEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.restaction.paragony.ParagonyResponseData;


@Stateless
@LocalBean
public class ParagonFacade {

	@EJB
	ParagonEjbQueryController paragonEjbQueryController;
	
	@EJB
	ParagonEjbCommandController paragonEjbCommandController;
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	
	public String getParagonyUzytkownika(ParagonyResponseData paragonResponseData){
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		List<Paragon> parCol = null;
		if(paragonResponseData.idUser != null){
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(Integer.parseInt(paragonResponseData.idUser));
			parCol = uzytkownik.getParagons();
		}else{
			parCol = paragonEjbQueryController.findAll();
		}
		for (Paragon paragon : parCol) {
			jsonObject = new JSONObject();
			jsonObject.put("id", paragon.getIdParagonu());
			jsonObject.put("daneWystawiajacego", paragon.getDaneWystawiajacego());
			jsonObject.put("dataWystawienia", paragon.getDataWystawienia().getTime());
			jsonObject.put("listaProd", paragon.getListaTowarow());
			List<Tranzakcje> colTranz = paragon.getTranzakcjes();
			if(colTranz!= null && !colTranz.isEmpty()){
				jsonObject.put("kwota", colTranz.iterator().next().getKwota()); 
			}
				
			
			
			
			jsonArray.put(jsonObject);
		}
		return jsonArray.toString();
	}

	public String getParagonById(ParagonyResponseData paragonResponseData) throws JSONException {
		Paragon paragon = paragonEjbQueryController.findEntityByID(paragonResponseData.idParagonu);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", paragon.getIdParagonu());
		jsonObject.put("daneWystawiajacego", paragon.getDaneWystawiajacego());
		jsonObject.put("dataWystawienia", paragon.getDataWystawienia().getTime());
		jsonObject.put("listaProd", paragon.getListaTowarow());
		
		Float wartoscAllNetto = 0f;
		Float sumaPodatkuVat = 0f;
		Float wartoscAllBrutto = 0f;
		
		JSONArray jsonArray = new JSONArray(paragon.getListaTowarow());
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
}
