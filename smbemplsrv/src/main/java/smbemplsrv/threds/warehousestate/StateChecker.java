package smbemplsrv.threds.warehousestate;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

import smbemplsrv.command.ejbcontrol.katzgl.KatZglEjbCommandController;
import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandController;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.dbmodel.KategoriaTowar;
import smbemplsrv.dbmodel.Towar;
import smbemplsrv.dbmodel.Zgloszenie;
import smbemplsrv.facade.zgloszenia.ZgloszeniaFacade;
import smbemplsrv.query.ejbcontrol.katzgl.KatZglEjbQueryController;
import smbemplsrv.query.ejbcontrol.produkt.ProduktEjbQueryController;
import smbemplsrv.query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;
import utils.status.Status;

@Stateless
public class StateChecker {
    @EJB
    ProduktEjbQueryController produktEjbQueryController;
    
    @EJB
    ProduktEjbCommandController produktEjbCommandController;
    
    @EJB
    KatZglEjbQueryController katZglEjbQueryController;
    
    @EJB
    ZgloszeniaEjbCommandController zgloszeniaEjbCommandController;
    
    @EJB
    ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;
    
    public StateChecker() {}
	
	@Schedule(second="*/30", minute="*", hour="*", dayOfWeek="*",
      dayOfMonth="*", month="*", year="*")
    private void checkProductState(final Timer t) {
		HashSet<Towar> towarListToSend = new HashSet<Towar>();
        System.out.println("checkProductState START: " + new java.util.Date());
       List<Towar> towarList = produktEjbQueryController.findAll();
       for (Towar towar : towarList) {
		if(towar.getStanMinimalny() != null 
			&& towar.getStanMinimalny() != null
			&& towar.getPowiadomiono() != null
			&& towar.getIloscWMagazynie().intValue() < towar.getStanMinimalny().intValue()
			&& !towar.getPowiadomiono()){
			towarListToSend.add(towar);
		}
	}
       if(!towarListToSend.isEmpty())
    	   generatNewNotyfiation(towarListToSend);
       
        System.out.println("checkProductState STOP: " + new java.util.Date());
    }

	private void generatNewNotyfiation(HashSet<Towar> towarListToSend) {
		KategoiaZgloszenia kategoiaZgloszenia = new KategoiaZgloszenia();
		kategoiaZgloszenia.setCzyMagazyn(true);
		kategoiaZgloszenia = katZglEjbQueryController.findEntity(kategoiaZgloszenia).iterator().next();
		
		Zgloszenie zgloszenie = new Zgloszenie();
		zgloszenie.setKategoiaZgloszenia(kategoiaZgloszenia);
		zgloszenie.setDataZgloszenia(new Timestamp(System.currentTimeMillis()));
		zgloszenie.setNumerZgloszenia(zgloszeniaEjbQueryController.generujNumerZgloszenia());
		zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.REALIZOWANE);
		zgloszenie.setTemat("Braki w magazynie. Trzeba uzupełnic zapasy.");
		zgloszenie.setTresc(getTrescByList(towarListToSend));
		
		zgloszeniaEjbCommandController.insert(zgloszenie);
	}

	private String getTrescByList(HashSet<Towar> towarListToSend) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Lista brakujących produktów:\n");
		for (Towar towar : towarListToSend) {
			stringBuffer.append(towar.getNazwa() + "		 ilosc:	" + towar.getIloscWMagazynie() + "	 stan min.: " + towar.getStanMinimalny()+"\n");
			towar.setPowiadomiono(true);
			produktEjbCommandController.update(towar);
		}
		return stringBuffer.toString();
	}
}