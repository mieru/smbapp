package smbcustsrv.testimpl.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
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
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import command.ejbcontrol.abst.AbstractEjbCommandController;
import command.ejbcontrol.uzytkownik.UzytkownikEjbCommandController;
import command.restaction.register.RegisterRequestData;
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
import facade.logowanie.LogowanieFacade;
import facade.rejestracja.RejestracjaFacade;
import mailer.MailSender;
import query.ejbcontrol.abst.AbstractEjbQueryController;
import query.ejbcontrol.konfmail.KonfMailEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.restaction.login.LoginRequestData;
import smbcustsrv.testinteface.IntegrationTest;
import utils.SmbUtil;
import utils.status.Status;

@RunWith(Arquillian.class)
public class TestRejestracjaAktywacjaLogowanie implements IntegrationTest{
	
	 @Deployment
	   public static WebArchive createTestArchive() {
	      return ShrinkWrap.create(WebArchive.class, "test.war")
	    		  .addAsLibraries(new File("C:\\Users\\Czarek\\git\\smbapp\\smbcustsrv\\src\\lib\\json.jar"))
	    		  .addClasses(IntegrationTest.class,
	    				      RegisterRequestData.class,
	    				      LoginRequestData.class,
	    				      SmbUtil.class,
	    				      Status.class,
	    				      MailSender.class,
	    				      KonfMailEjbQueryController.class,
	    				      UzytkownikEjbQueryController.class,
	    				      UzytkownikEjbCommandController.class,
	    				      AbstractEjbCommandController.class,
	    				      RejestracjaFacade.class,
	    				      LogowanieFacade.class,
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
		LogowanieFacade logowanieFacade;
		
		@EJB
		UzytkownikEjbQueryController uzytkownikEjbQueryController ;
		
		@EJB
		UzytkownikEjbCommandController uzytkownikEjbCommandController;

	private RegisterRequestData registerRequestData = null;
	private LoginRequestData loginRequestData = null;
	private Uzytkownik uzytkownik = null;

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

		 //poprawne dane do logowania
			loginRequestData  = new LoginRequestData();
			loginRequestData.username = "test123";
			loginRequestData.password = "test123";
	
			uzytkownik = new Uzytkownik();
			uzytkownik.setLogin(registerRequestData.username);
			
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
	public void loginNotExist() throws AddressException, JSONException, MessagingException {
		assertEquals(uzytkownikEjbQueryController.isLoginExist(registerRequestData.username), false);
	}
	
	@Test
	@InSequence(3)
	public void registerUser() throws AddressException, JSONException, MessagingException {
		rejestracjaFacade.rejestrujUzytkownika(registerRequestData);
		
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
	@InSequence(4)
	public void loginExist() throws AddressException, JSONException, MessagingException {
		assertEquals(uzytkownikEjbQueryController.isLoginExist(registerRequestData.username), true);
	}
	
	@Test
	@InSequence(5)
	public void loginWithoutActivation() throws AddressException, JSONException, MessagingException {
		JSONObject json = new JSONObject(logowanieFacade.sprawdzDaneLogowania(loginRequestData));
		assertEquals(json.get("login_result").toString(), "not_active");
	}

	@Test
	@InSequence(6)
	public void activation() throws AddressException, JSONException, MessagingException {
		List<Uzytkownik> list = uzytkownikEjbQueryController.findEntity(uzytkownik);
		uzytkownik = list.iterator().next();
		
		registerRequestData.idEncoded = SmbUtil.encodeInteger(uzytkownik.getIdUser());
		JSONObject	json = new JSONObject(rejestracjaFacade.aktywujKonto(registerRequestData));
		
		assertEquals(json.get("response").toString(), "OK");
		
		uzytkownik = uzytkownikEjbQueryController.findEntityByID(uzytkownik.getIdUser());
		
		assertNotNull(uzytkownik);
		assertEquals(uzytkownik.getState(), Status.USER_STATE.ACTIVE);
	}
	
	@Test
	@InSequence(7)
	public void loginAfterActivation() {
		JSONObject json = new JSONObject(logowanieFacade.sprawdzDaneLogowania(loginRequestData));
		assertEquals(json.get("login_result").toString(), "true");
	}			
			
	@Test
	@InSequence(8)
	public void loginNoCorrectData() {
		loginRequestData.password = "niepoprawne";
		JSONObject json = new JSONObject(logowanieFacade.sprawdzDaneLogowania(loginRequestData));
		assertEquals(json.get("login_result").toString(), "false");
	}		
				
	
	@Test
	@InSequence(9)
	public void cleanAfterTest(){
		List<Uzytkownik> list = uzytkownikEjbQueryController.findEntity(uzytkownik);
		uzytkownik = list.iterator().next();
		uzytkownikEjbCommandController.delete(uzytkownik);
	}
	

}
