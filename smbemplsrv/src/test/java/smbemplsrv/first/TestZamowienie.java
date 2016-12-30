//package smbemplsrv.first;
//
//import static org.junit.Assert.*;
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
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import smbemplsrv.dbmodel.FakturaSprzedazy;
//import smbemplsrv.dbmodel.Paragon;
//import smbemplsrv.dbmodel.Tranzakcje;
//import smbemplsrv.dbmodel.Uzytkownik;
//import smbemplsrv.dbmodel.WiadomoscZamSprzedaz;
//import smbemplsrv.dbmodel.ZamowienieSprzedaz;
//import smbemplsrv.query.ejbcontrol.abstr.AbstractEjbQueryController;
//import smbemplsrv.query.ejbcontrol.tranzakcje.TranzakcjeEjbQueryController;
//
//@RunWith(Arquillian.class)
//public class TestZamowienie{
//	
//	 @Deployment
//	   public static WebArchive createTestArchive() {
//	      return ShrinkWrap.create(WebArchive.class, "test.war")
//	    		  .addAsLibraries(new File("C:\\Users\\Czarek\\git\\smbapp\\smbcustsrv\\src\\lib\\json.jar"))
//	    		  .addClasses(TranzakcjeEjbQueryController.class,
//	    				  	  AbstractEjbQueryController.class,
//	    				  	  Tranzakcje.class,
//	    				  	  FakturaSprzedazy.class,
//	    				  	  ZamowienieSprzedaz.class,
//	    				  	  Uzytkownik.class,
//	    				  	  WiadomoscZamSprzedaz.class,
//	    				  	  Paragon.class)
//	    		  .addAsResource("META-INF/persistence.xml");
//	   }
//	
//	@EJB
//	TranzakcjeEjbQueryController tranzakcjeEjbQueryController;
//	 
//	@Test
//	public void addNewOrder() throws AddressException, JSONException, MessagingException {
//		List<Tranzakcje> col = tranzakcjeEjbQueryController.findAll();
//		assertNotNull(col);
//	}
//	
//
//}
