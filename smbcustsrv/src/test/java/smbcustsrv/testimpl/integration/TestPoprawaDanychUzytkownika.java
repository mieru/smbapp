package smbcustsrv.testimpl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import command.restaction.register.RegisterRequestData;
import command.restaction.uzytkownik.UzytkownikRequestCommandData;
import dbmodel.DaneFirmy;
import dbmodel.FakturaSprzedazy;
import dbmodel.KategoiaZgloszenia;
import dbmodel.KategoriaTowar;
import dbmodel.KonfMail;
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
import facade.rejestracja.RejestracjaFacade;
import facade.uzytkownik.UzytkownikFacade;
import mailer.MailSender;
import query.ejbcontrol.abst.AbstractEjbQueryController;
import query.ejbcontrol.konfmail.KonfMailEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.restaction.uzytkownik.UzytkwonikRequestQueryData;
import smbcustsrv.testinteface.IntegrationTest;
import utils.SmbUtil;
import utils.status.Status;

@RunWith(Arquillian.class)
public class TestPoprawaDanychUzytkownika implements IntegrationTest{
	
	 @Deployment
	   public static WebArchive createTestArchive() {
	      return ShrinkWrap.create(WebArchive.class, "test.war")
	    		  .addAsLibraries(new File("C:\\Users\\Czarek\\git\\smbapp\\smbcustsrv\\src\\lib\\json.jar"))
	    		  .addClasses(IntegrationTest.class,
	    				      RegisterRequestData.class,
	    				      UzytkownikRequestCommandData.class,
	    				      UzytkwonikRequestQueryData.class,
	    				      SmbUtil.class,
	    				      Status.class,
	    				      MailSender.class,
	    				      KonfMailEjbQueryController.class,
	    				      UzytkownikEjbQueryController.class,
	    				      UzytkownikEjbCommandController.class,
	    				      AbstractEjbCommandController.class,
	    				      RejestracjaFacade.class,
	    				      UzytkownikFacade.class,
	    				      AbstractEjbQueryController.class,
	    				  	  Uzytkownik.class,
	    				  	  KonfMail.class,
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
		RejestracjaFacade rejestracjaFacade;
		
		@EJB
		UzytkownikFacade uzytkownikFacade;
		
		@EJB
		UzytkownikEjbQueryController uzytkownikEjbQueryController ;
		
		@EJB
		UzytkownikEjbCommandController uzytkownikEjbCommandController;

	private RegisterRequestData registerRequestData = null;
	private UzytkownikRequestCommandData uzytkownikRequestData = null;
	private  Uzytkownik uzytkownik = null;

	@Before
	public void initDataToTest(){
	//inicjalizacja danych testowego klienta
		 registerRequestData = new RegisterRequestData();
		 registerRequestData.url = "http://localhost:8080/smbfront/#/rejestracja_osoba";
		 registerRequestData.username = "test123";
		 registerRequestData.password = "test123";
		 registerRequestData.name = "Test";
		 registerRequestData.surname = "Testowy";
		 registerRequestData.email = "smbpracinz@gmail.com";
		 registerRequestData.phone = "777777777";
		 registerRequestData.street = "Testowa";
		 registerRequestData.buildingNumber = "1";
		 registerRequestData.city = "Testowe";
		 registerRequestData.postCode = "11-111";
	
	//nowe dane do poprawienia
			uzytkownikRequestData  = new UzytkownikRequestCommandData();
			uzytkownikRequestData.name = "TestNEW";
			uzytkownikRequestData.surname = "TestowyNEW";
			uzytkownikRequestData.email = "smbpracinz@gmail.com";
			uzytkownikRequestData.phone = "22222222";
			uzytkownikRequestData.street = "TestowaNEW";
			uzytkownikRequestData.buildingNumber = "11";
			uzytkownikRequestData.city = "TestoweNEW";
			uzytkownikRequestData.postCode = "21-111";
	
	}
	
	
	@Test
	@InSequence(1)
	public void cleanBeforeTest() throws AddressException, JSONException, MessagingException {
		 uzytkownik = new Uzytkownik();
		 uzytkownik.setLogin(registerRequestData.username);
		 List<Uzytkownik> resultList = uzytkownikEjbQueryController.findEntity(uzytkownik);
			if(resultList.size() > 0){
				for (Uzytkownik uzyt : resultList) {
					uzytkownikEjbCommandController.delete(uzyt);
				}
			}
	}
		
	@Test
	@InSequence(2)
	public void registerUser() throws AddressException, JSONException, MessagingException {
		rejestracjaFacade.rejestrujUzytkownika(registerRequestData);
		
		 uzytkownik = new Uzytkownik();
		 uzytkownik.setLogin(registerRequestData.username);
		List<Uzytkownik> resultList = uzytkownikEjbQueryController.findEntity(uzytkownik);
		
		assertNotNull(resultList);
		assertEquals(resultList.size(), 1);
			
		uzytkownik = resultList.iterator().next();
		 
		assertNotNull(uzytkownik);
		assertNotNull(uzytkownik.getIdUser());
	    assertEquals(uzytkownik.getLogin(), registerRequestData.username);
		assertEquals(SmbUtil.decodeString(uzytkownik.getPassword()), registerRequestData.password);
		assertEquals(uzytkownik.getName(), registerRequestData.name);
		assertEquals(uzytkownik.getSurname(), registerRequestData.surname);
		assertEquals(uzytkownik.getMail(), registerRequestData.email);
		assertEquals(uzytkownik.getPhone(), registerRequestData.phone);
		assertEquals(uzytkownik.getStreet(), registerRequestData.street);
		assertEquals(uzytkownik.getBuildingNumber(), registerRequestData.buildingNumber);
		assertEquals(uzytkownik.getCity(), registerRequestData.city);
		assertEquals(uzytkownik.getPosCode(), registerRequestData.postCode);
		assertEquals(uzytkownik.getState(), Status.USER_STATE.NEW);
		assertEquals(uzytkownik.getRole(), Status.USER_ROLE.CUSTOMER);
		
		
	}
	
	
	@Test
	@InSequence(3)
	public void changeUserData() throws AddressException, JSONException, MessagingException {
		 uzytkownik = new Uzytkownik();
		 uzytkownik.setLogin(registerRequestData.username);
		 List<Uzytkownik> resultList = uzytkownikEjbQueryController.findEntity(uzytkownik);
		 uzytkownik = resultList.iterator().next();
		 
		uzytkownikRequestData.idUser = uzytkownik.getIdUser();
		uzytkownikFacade.poprawDaneUzytkownika(uzytkownikRequestData);
		
		uzytkownik = uzytkownikEjbQueryController.findEntityByID(uzytkownik.getIdUser());
		
		assertNotNull(uzytkownik);
		assertNotNull(uzytkownik.getIdUser());
		assertEquals(uzytkownik.getName(), uzytkownikRequestData.name);
		assertEquals(uzytkownik.getSurname(), uzytkownikRequestData.surname);
		assertEquals(uzytkownik.getMail(), uzytkownikRequestData.email);
		assertEquals(uzytkownik.getPhone(), uzytkownikRequestData.phone);
		assertEquals(uzytkownik.getStreet(), uzytkownikRequestData.street);
		assertEquals(uzytkownik.getBuildingNumber(), uzytkownikRequestData.buildingNumber);
		assertEquals(uzytkownik.getCity(), uzytkownikRequestData.city);
		assertEquals(uzytkownik.getPosCode(), uzytkownikRequestData.postCode);
		assertEquals(uzytkownik.getState(), Status.USER_STATE.NEW);
		assertEquals(uzytkownik.getRole(), Status.USER_ROLE.CUSTOMER);
		
	}
	
	@Test
	@InSequence(4)
	public void cleanAfterTest(){
		uzytkownikEjbCommandController.delete(uzytkownik);
	}
	

}
