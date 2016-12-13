package smbemplsrv.threds.minimalstate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import smbemplsrv.command.ejbcontrol.zamowienie.ZamowienieEjbCommandController;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.ZamowienieSprzedaz;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.ejbcontrol.zamowienie.ZamowienieEjbQueryController;
import utils.status.Status;

@Stateless
public class OrderEmplChooser {
	@EJB
	ZamowienieEjbQueryController zamowienieEjbQueryController;
	
	@EJB
	ZamowienieEjbCommandController zamowienieEjbCommandController;
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	

	@Schedule(minute="*/1", hour="*", dayOfWeek="*",
      dayOfMonth="*", month="*", year="*")
    private void addEmplToOrder(final Timer t) {
        System.out.println("addEmplToOrder START: " + new Date());
        ZamowienieSprzedaz zamowienieSprzedaz = new ZamowienieSprzedaz();
        StringBuilder additonalCondition = new StringBuilder();
        
        additonalCondition.append(" AND id_pracownika_obsl is null");
        
        List<ZamowienieSprzedaz> zamList = zamowienieEjbQueryController.findEntity(zamowienieSprzedaz, additonalCondition);
        if(!zamList.isEmpty()){
        	setEmplToNotyf(zamList);
        }
        System.out.println("addEmplToOrder END: " + zamList.size() + new java.util.Date());
    }


	private void setEmplToNotyf(List<ZamowienieSprzedaz> zamList) {
		HashMap<Integer, Integer> emplOrderCountMap = countOrderEmpl();
		for (ZamowienieSprzedaz zamowienieSprzedaz : zamList) {
			Integer minCount = Collections.min(emplOrderCountMap.values());
			Integer idUser = getKeyByValue(emplOrderCountMap, minCount);
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(idUser);
			zamowienieSprzedaz.setUzytkownik2(uzytkownik);
			zamowienieSprzedaz.setStatus(Status.ZGLOSZENIE_STATE.REALIZOWANE);
			zamowienieEjbCommandController.update(zamowienieSprzedaz);
			minCount++;
			emplOrderCountMap.put(idUser, minCount);
			System.out.println(emplOrderCountMap.toString());
		}
		
	}


	private Integer getKeyByValue(HashMap<Integer, Integer> emplOrderCountMap, Integer val) {
		Integer idUser = null;
		for (Map.Entry<Integer, Integer> entry : emplOrderCountMap.entrySet()) {
			if(entry.getValue().equals(val)){
				idUser = entry.getKey();
				break;
			}
		}
		return idUser;
	}


	private HashMap<Integer, Integer> countOrderEmpl() {
		List<Uzytkownik> userList = new ArrayList<Uzytkownik>();
		Uzytkownik uzytkownik = new Uzytkownik();
		StringBuilder builder = new StringBuilder();
		builder.append(" AND role not in ('C','A')");
		userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik,builder));
		System.out.println(userList.size());
		 HashMap<Integer, Integer> countMap = new  HashMap<Integer, Integer>();
		
		for (Uzytkownik uzyt : userList) {
			List<ZamowienieSprzedaz> zamList = uzyt.getZamowienieSprzedazs1();
			countMap.put(uzyt.getIdUser(),zamList.size());
		}
		 
		return countMap;
	}

}