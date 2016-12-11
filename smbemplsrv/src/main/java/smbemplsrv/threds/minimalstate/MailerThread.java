package smbemplsrv.threds.minimalstate;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import smbemplsrv.command.ejbcontrol.produkt.ProduktEjbCommandController;
import smbemplsrv.command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandController;
import smbemplsrv.dbmodel.KategoiaZgloszenia;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.dbmodel.Zgloszenie;
import smbemplsrv.query.ejbcontrol.katzgl.KatZglEjbQueryController;
import smbemplsrv.query.ejbcontrol.produkt.ProduktEjbQueryController;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;
import utils.MailSender;
import utils.status.Status;

@Stateless
public class MailerThread {
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
   
    @EJB
    UzytkownicyEjbQueryController uzytkownicyEjbQueryController;
    
    @EJB
    MailSender mailSender;
    
    
    public MailerThread() {}
	
	@Schedule(second="*/20", minute="*", hour="*", dayOfWeek="*",
      dayOfMonth="*", month="*", year="*")
    private void sentMailForNotyfication(final Timer t) throws AddressException, MessagingException {
		sendMailForZglMagazyn();
		sendMailForZgl();
	}

	private void sendMailForZglMagazyn() throws AddressException, MessagingException {
		KategoiaZgloszenia kategoiaZgloszenia = new KategoiaZgloszenia();
        kategoiaZgloszenia.setCzyMagazyn(true);
        kategoiaZgloszenia = katZglEjbQueryController.findEntity(kategoiaZgloszenia).iterator().next();
        List<Uzytkownik> userList = new ArrayList<Uzytkownik>();
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setRole("E,M");
		userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
		uzytkownik.setRole("E,A,M");
		userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
		System.out.println("userList : " + userList.size());
       List<Zgloszenie> listZgloszenie =  kategoiaZgloszenia.getZgloszenies();
       System.out.println("listZgloszenie : " +listZgloszenie.size());
		for (Zgloszenie  zgloszenie : listZgloszenie) {
			if(!(zgloszenie.getPowiadomiono() != null && !zgloszenie.getPowiadomiono())) continue;
			zgloszenie.setPowiadomiono(true);
			zgloszeniaEjbCommandController.update(zgloszenie); 
			for (Uzytkownik u : userList) {
				mailSender.sendMail("Nowe zgłoszenie magazynowe", "Braki w magazynnie. Prosze o uzupelnienie zapasow. Zgloszenie nr : " + zgloszenie.getNumerZgloszenia(), u.getMail());
			}
		}
	}
	
	private void sendMailForZgl() throws AddressException, MessagingException {
		KategoiaZgloszenia kategoiaZgloszenia = new KategoiaZgloszenia();
        kategoiaZgloszenia.setCzyMagazyn(true);
        kategoiaZgloszenia = katZglEjbQueryController.findEntity(kategoiaZgloszenia).iterator().next();
        Zgloszenie zgloszenie = new Zgloszenie();
        zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.REALIZOWANE);
        zgloszenie.setPowiadomiono(false);
        StringBuilder additonalCondition = new StringBuilder();
        additonalCondition.append(" AND id_pracownika_obsl is not null");
        additonalCondition.append(" AND id_kategorii NOT IN (" + kategoiaZgloszenia.getIdKategrorii() + ")");
        Uzytkownik u = null;
        List<Zgloszenie> listZgloszenie = new ArrayList<Zgloszenie>();
        listZgloszenie.addAll(zgloszeniaEjbQueryController.findEntity(zgloszenie, additonalCondition));
    	System.out.println(listZgloszenie.size());
		for (Zgloszenie zgl : listZgloszenie) {
				u = zgl.getUzytkownik1();
				System.out.println(u.getMail());
				mailSender.sendMail("Nowe zgłoszenie od klienta. Zgloszenie nr : " + zgl.getNumerZgloszenia(),"\n Temat zgloszenia : " + zgl.getTemat(), u.getMail());
				zgl.setPowiadomiono(true);
				zgloszeniaEjbCommandController.update(zgl); 
			}
		}
	}
