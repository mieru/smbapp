package smbcustsrv.testimpl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import command.ejbcontrol.kategoriazgloszenie.KategoraiZgloszenieEjbCommandController;
import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import command.ejbcontrol.zglkomentarz.ZgloszenieKomentarzEjbCommandContoller;
import command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandContoller;
import command.restaction.zgloszenie.ZgloszenieRequestData;
import dbmodel.DaneFirmy;
import dbmodel.FakturaSprzedazy;
import dbmodel.KategoiaZgloszenia;
import dbmodel.KategoriaTowar;
import dbmodel.Magazyn;
import dbmodel.Paragon;
import dbmodel.Producent;
import dbmodel.Towar;
import dbmodel.TowarImage;
import dbmodel.Uzytkownik;
import dbmodel.WiadomoscZamSprzedaz;
import dbmodel.ZamowienieSprzedaz;
import dbmodel.Zgloszenie;
import dbmodel.ZgloszenieKomentarz;
import facade.zgloszenia.ZgloszenieFacade;
import query.ejbcontrol.abst.AbstractEjbQueryController;
import query.ejbcontrol.kategoriazgloszenie.KategoriaZgloszenieEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;
import smbcustsrv.testinteface.IntegrationTest;
import utils.status.Status;

@RunWith(Arquillian.class)
public class TestZgloszenie implements IntegrationTest{
	
	 @Deployment
	   public static WebArchive createTestArchive() {
	      return ShrinkWrap.create(WebArchive.class, "test.war")
	    		  .addAsLibraries(new File("C:\\Users\\Czarek\\git\\smbapp\\smbcustsrv\\src\\lib\\json.jar"))
	    		  .addClasses(IntegrationTest.class,
	    				      Status.class,
	    				      ZgloszenieRequestData.class,
	    				      ZgloszenieFacade.class,
	    				      ZgloszeniaEjbQueryController.class,
	    				      ZgloszeniaEjbCommandContoller.class,
	    				      ZgloszenieKomentarzEjbCommandContoller.class,
	    				      KategoraiZgloszenieEjbCommandController.class,
	    				      KategoriaZgloszenieEjbQueryController.class,
	    				      UzytkownikEjbQueryController.class,
	    				      UzytkownikEjbCommandController.class,
	    				      AbstractEjbCommandController.class,
	    				      AbstractEjbQueryController.class,
	    				  	  Uzytkownik.class,
	    				      DaneFirmy.class,
		        		 	  FakturaSprzedazy.class,
		        			  KategoiaZgloszenia.class,
		        			  KategoriaTowar.class,
		        			  Magazyn.class,
		        			  Paragon.class,
		        			  Producent.class,
		        			  Towar.class,
		        			  TowarImage.class,
		        			  Uzytkownik.class,
		        			  WiadomoscZamSprzedaz.class,
		        			  ZamowienieSprzedaz.class,
		        			  Zgloszenie.class,
		        			  ZgloszenieKomentarz.class)
	    		  .addAsResource("META-INF/persistence.xml");
	   }
	
	
		@EJB
		ZgloszenieFacade zgloszenieFacade;
		
		@EJB
		ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;
		
		@EJB
		ZgloszeniaEjbCommandContoller zgloszeniaEjbCommandContoller;
		
		@EJB
		ZgloszenieKomentarzEjbCommandContoller zgloszenieKomentarzEjbCommandContoller;

		private ZgloszenieRequestData zgloszenieRequestData = null;
		private Zgloszenie zgloszenie = null;
		private ZgloszenieKomentarz komentarz = null;
		
	@Before
	public void initDataToTest(){
		zgloszenieRequestData = new ZgloszenieRequestData();
		zgloszenieRequestData.idKategoria = 1;
		zgloszenieRequestData.idUser = 45;
		zgloszenieRequestData.temat = "Testowe zgloszenie";
		zgloszenieRequestData.tresc = "Testowa trezc zgloszenia";
	}
	
	
	@Test
	public void addNewNotification() throws AddressException, JSONException, MessagingException {
		String numer_zgl= zgloszeniaEjbQueryController.generujNumerZgloszenia();
		zgloszenieRequestData.idZgloszenia = zgloszenieFacade.dodajZgloszenie(zgloszenieRequestData);
		
		zgloszenie = zgloszeniaEjbQueryController.findEntityByID(zgloszenieRequestData.idZgloszenia);

		assertNotNull(zgloszenie);
		assertNotNull(zgloszenie.getIdZgloszenia());
		assertNotNull(zgloszenie.getDataZgloszenia());
		assertNull(zgloszenie.getUzytkownik1());
		assertEquals(zgloszenie.getStatus(), Status.ZGLOSZENIE_STATE.NOWE);
	    assertEquals(zgloszenie.getNumerZgloszenia(), numer_zgl);
	    assertEquals(zgloszenie.getTemat(), zgloszenieRequestData.temat);
	    assertEquals(zgloszenie.getTresc(), zgloszenieRequestData.tresc);
	    assertEquals(zgloszenie.getUzytkownik2().getIdUser(), zgloszenieRequestData.idUser);
	    
	    List<ZgloszenieKomentarz> listaWiad = zgloszenie.getZgloszenieKomentarzs();
	   if(!listaWiad.isEmpty()){
		   fail("Fail");
	   }
	   zgloszenieRequestData.tresc = "test wiadomosci";
	   zgloszenieFacade.dodajWiadomoscDoZgloszenia(zgloszenieRequestData);
	   
	   zgloszenie = zgloszeniaEjbQueryController.findEntityByID(zgloszenieRequestData.idZgloszenia);
	    listaWiad = zgloszenie.getZgloszenieKomentarzs();
	    assertNotNull(listaWiad);
	    
	    komentarz = listaWiad.iterator().next();
	    
	    assertNotNull(komentarz);
		assertNotNull(komentarz.getIdKomentarzaZgloszenia());
		assertNotNull(komentarz.getDataDodania());
	    assertEquals(komentarz.getTresc(),  zgloszenieRequestData.tresc);
	    assertEquals(komentarz.getUzytkownik().getIdUser(), zgloszenieRequestData.idUser);
	    
	}
	
	@After
	public void cleanAfterTest(){
		zgloszenieKomentarzEjbCommandContoller.delete(komentarz);
		zgloszeniaEjbCommandContoller.delete(zgloszenie);
	}
	

}
