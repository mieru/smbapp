//package smbcustsrv.testimpl.integration;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.fail;
//
//import java.io.File;
//import java.util.List;
//
//import javax.ejb.EJB;
//import javax.mail.MessagingException;
//import javax.mail.internet.AddressException;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.json.JSONException;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import businesslogic.order.OrderLogicController;
//import dbmenager.abstr.AbstractDAO;
//import dbmenager.mailconfiguration.SystemMailCongifurationDAO;
//import dbmenager.notificationmessage.WiadZamSprzedEjbCommandController;
//import dbmenager.order.ZamowienieSprzedEjbCommandContoller;
//import dbmenager.user.UserDAO;
//import dbmodel.DaneFirmy;
//import dbmodel.FakturaSprzedazy;
//import dbmodel.KategoiaZgloszenia;
//import dbmodel.KategoriaTowar;
//import dbmodel.Magazyn;
//import dbmodel.MailKonf;
//import dbmodel.Paragon;
//import dbmodel.Producent;
//import dbmodel.Towar;
//import dbmodel.TowarImage;
//import dbmodel.Uzytkownik;
//import dbmodel.WiadomoscZamSprzedaz;
//import dbmodel.ZamowienieSprzedaz;
//import dbmodel.Zgloszenie;
//import dbmodel.ZgloszenieKomentarz;
//import mailmenager.MailSender;
//import query.ejbcontrol.abst.AbstractEjbQueryController;
//import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
//import query.ejbcontrol.zamowieniasprzedarz.ZamowieniaSprzedEjbQueryController;
//import query.restaction.zamowienia.ZamowieniaRequestQueryData;
//import restapi.order.OrderRestData;
//import smbcustsrv.testinteface.IntegrationTest;
//import utils.Status;
//
//@RunWith(Arquillian.class)
//public class TestZamowienie implements IntegrationTest{
//	
//	 @Deployment
//	   public static WebArchive createTestArchive() {
//	      return ShrinkWrap.create(WebArchive.class, "test.war")
//	    		  .addAsLibraries(new File("C:\\Users\\Czarek\\git\\smbapp\\smbcustsrv\\src\\lib\\json.jar"))
//	    		  .addClasses(IntegrationTest.class,
//	    				      Status.class,
//	    				      OrderRestData.class,
//	    				      ZamowieniaRequestQueryData.class,
//	    				      ZamowienieSprzedaz.class,
//	    				      OrderLogicController.class,
//	    				      WiadomoscZamSprzedaz.class,
//	    				      ZamowieniaSprzedEjbQueryController.class,
//	    				      ZamowienieSprzedEjbCommandContoller.class,
//	    				      WiadZamSprzedEjbCommandController.class,
//	    				      UzytkownikEjbQueryController.class,
//	    				      UserDAO.class,
//	    				      AbstractDAO.class,
//	    				      AbstractEjbQueryController.class,
//	    				      SystemMailCongifurationDAO.class,
//	    				      MailSender.class,
//	    				  	  Uzytkownik.class,
//	    				      DaneFirmy.class,
//		        		 	  FakturaSprzedazy.class,
//		        			  KategoiaZgloszenia.class,
//		        			  KategoriaTowar.class,
//		        			  Magazyn.class,
//		        			  Paragon.class,
//		        			  Producent.class,
//		        			  MailKonf.class,
//		        			  Towar.class,
//		        			  TowarImage.class,
//		        			  Uzytkownik.class,
//		        			  WiadomoscZamSprzedaz.class,
//		        			  ZamowienieSprzedaz.class,
//		        			  Zgloszenie.class,
//		        			  ZgloszenieKomentarz.class)
//	    		  .addAsResource("META-INF/persistence.xml");
//	   }
//	
//	
//		@EJB
//		OrderLogicController zamowienieFacade;
//		
//		@EJB
//		ZamowieniaSprzedEjbQueryController zamowieniaSprzedEjbQueryController;
//		
//		@EJB
//		ZamowienieSprzedEjbCommandContoller zamowienieSprzedEjbCommandContoller;
//		
//		@EJB
//		WiadZamSprzedEjbCommandController wiadZamSprzedEjbCommandController;
//		
//
//		private OrderRestData zamowienieRequestData = null;
//		private ZamowienieSprzedaz zamowienieSprzedaz = null;
//		private WiadomoscZamSprzedaz wiadomosc = null;
//		
//	@Before
//	public void initDataToTest(){
//		zamowienieRequestData = new OrderRestData();
//		zamowienieRequestData.adresDostawy = "Testoy adres";
//		zamowienieRequestData.czyFaktura = true;
//		zamowienieRequestData.daneDoFaktury = "Testowe dane fo faktury";
//		zamowienieRequestData.idZamawiajacego = 259;
//		zamowienieRequestData.listaPoduktow = "[{\"id\":4,\"ilosc\":3,\"nazwa\":\"Bloczek betonowy  38x24x12 cm \",\"cbrutto\":2.46,\"cnetto\":2,\"stawka_vat\":23,\"$$hashKey\":\"object:57\"}]";;
//		zamowienieRequestData.trescWiadomosci =null;
//	}
//	
//	
//	@Test
//	public void addNewOrder() throws AddressException, JSONException, MessagingException {
//		String numer_zgl= zamowieniaSprzedEjbQueryController.generujNumerZgloszenia();
//		zamowienieFacade.addNewOrder(zamowienieRequestData);
//		
//	    zamowienieSprzedaz = zamowieniaSprzedEjbQueryController.findEntityByID(zamowienieRequestData.idZamowienia);
//		
//		assertNotNull(zamowienieSprzedaz);
//		assertNotNull(zamowienieSprzedaz.getIdZamowieniaSprzedaz());
//		assertNotNull(zamowienieSprzedaz.getDataZlozenia());
//		assertNull(zamowienieSprzedaz.getUzytkownik2());
//		assertEquals(zamowienieSprzedaz.getStatus(), Status.ZAMOWIENIE_STATE.NOWE);
//		assertEquals(zamowienieSprzedaz.getNumerZamowienia(), numer_zgl);
//	    assertEquals(zamowienieSprzedaz.getAdresDostawy(), zamowienieRequestData.adresDostawy);
//	    assertEquals(zamowienieSprzedaz.getCzyFaktura(), zamowienieRequestData.czyFaktura);
//	    assertEquals(zamowienieSprzedaz.getDaneDoFaktury(), zamowienieRequestData.daneDoFaktury);
//	    assertEquals(zamowienieSprzedaz.getUzytkownik1().getIdUser(), zamowienieRequestData.idZamawiajacego);
//	    assertEquals(zamowienieSprzedaz.getListaProduktow(), zamowienieRequestData.listaPoduktow);
//	    
//	    List<WiadomoscZamSprzedaz> listaWiad = zamowienieSprzedaz.getWiadomoscZamSprzedazs();
//	   if(!listaWiad.isEmpty()){
//		   fail("Fail");
//	   }
//	    zamowienieRequestData.idZamowienia = zamowienieSprzedaz.getIdZamowieniaSprzedaz();
//	    zamowienieRequestData.trescWiadomosci = "test wiadomosci";
//	    zamowienieFacade.addMessageToOrder(zamowienieRequestData);
//	   
//	    zamowienieSprzedaz = zamowieniaSprzedEjbQueryController.findEntityByID(zamowienieRequestData.idZamowienia);
//	    listaWiad = zamowienieSprzedaz.getWiadomoscZamSprzedazs();
//	    assertNotNull(listaWiad);
//	    
//	    wiadomosc = listaWiad.iterator().next();
//	    
//	    assertNotNull(wiadomosc);
//		assertNotNull(wiadomosc.getIdWiadZamSprzed());
//		assertNotNull(wiadomosc.getDataDodanie());
//	    assertEquals(wiadomosc.getTresc(), zamowienieRequestData.trescWiadomosci);
//	    assertEquals(wiadomosc.getUzytkownik().getIdUser(), zamowienieRequestData.idZamawiajacego);
//	    
//	}
//	
//	@After
//	public void cleanAfterTest(){
//		wiadZamSprzedEjbCommandController.delete(wiadomosc);
//		zamowienieSprzedEjbCommandContoller.delete(zamowienieSprzedaz);
//	}
//	
//
//}
