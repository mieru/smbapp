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

import smbemplsrv.command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandController;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.Zgloszenie;
import smbemplsrv.query.ejbcontrol.katzgl.KatZglEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;
import utils.status.Status;

@Stateless
public class NotyficationEmplChooser {
	@EJB
	ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;
	
	@EJB
	ZgloszeniaEjbCommandController zgloszeniaEjbCommandController;
	
	@EJB
	KatZglEjbQueryController katZglEjbQueryController;
	
	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
	

	@Schedule(minute="*/1", hour="*", dayOfWeek="*",
      dayOfMonth="*", month="*", year="*")
    private void addEmplToNotification(final Timer t) {
        System.out.println("addEmplToNotification START: " + new Date());
        Zgloszenie zgloszenie = new Zgloszenie();
        StringBuilder additonalCondition = new StringBuilder();
        KategoiaZgloszenia kategoiaZgloszenia = new KategoiaZgloszenia();
        kategoiaZgloszenia.setCzyMagazyn(true);
        kategoiaZgloszenia = katZglEjbQueryController.findEntity(kategoiaZgloszenia).iterator().next();
        
        additonalCondition.append(" AND id_pracownika_obsl is null");
        additonalCondition.append(" AND id_kategorii != " + kategoiaZgloszenia.getIdKategrorii());
        
        List<Zgloszenie> zglList = zgloszeniaEjbQueryController.findEntity(zgloszenie, additonalCondition);
        if(!zglList.isEmpty()){
        	setEmplToNotyf(zglList);
        }
        System.out.println("addEmplToNotification END: " + zglList.size() + new java.util.Date());
    }


	private void setEmplToNotyf(List<Zgloszenie> zglList) {
		HashMap<Integer, Integer> emplNotyfCountMap = countNotyfEmpl();
		for (Zgloszenie zgloszenie : zglList) {
			Integer minCount = Collections.min(emplNotyfCountMap.values());
			Integer idUser = getKeyByValue(emplNotyfCountMap, minCount);
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(idUser);
			zgloszenie.setUzytkownik1(uzytkownik);
			zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.REALIZOWANE);
			zgloszeniaEjbCommandController.update(zgloszenie);
			minCount++;
			emplNotyfCountMap.put(idUser, minCount);
			System.out.println(emplNotyfCountMap.toString());
		}
		
	}


	private Integer getKeyByValue(HashMap<Integer, Integer> emplNotyfCountMap, Integer val) {
		Integer idUser = null;
		for (Map.Entry<Integer, Integer> entry : emplNotyfCountMap.entrySet()) {
			if(entry.getValue().equals(val)){
				idUser = entry.getKey();
				break;
			}
		}
		return idUser;
	}


	private HashMap<Integer, Integer> countNotyfEmpl() {
		List<Uzytkownik> userList = new ArrayList<Uzytkownik>();
		Uzytkownik uzytkownik = new Uzytkownik();
		StringBuilder builder = new StringBuilder();
		builder.append(" AND role not in ('C','A')");
		userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik,builder));
		System.out.println(userList.size());
		 HashMap<Integer, Integer> countMap = new  HashMap<Integer, Integer>();
		
		for (Uzytkownik uzyt : userList) {
			List<Zgloszenie> zglList = uzyt.getZgloszenies1();
			countMap.put(uzyt.getIdUser(),zglList.size());
		}
		 
		 
		return countMap;
	}

}