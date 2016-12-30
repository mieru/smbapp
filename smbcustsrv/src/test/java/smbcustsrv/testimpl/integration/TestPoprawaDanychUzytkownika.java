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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import businesslogic.register.RegisterLogicController;
import businesslogic.user.UserLogicController;
import dbmenager.abstr.AbstractDAO;
import dbmenager.mailconfiguration.SystemMailCongifurationDAO;
import dbmenager.user.UserDAO;
import dbmodel.CommoditiesImage;
import dbmodel.Commodity;
import dbmodel.CommodityCategory;
import dbmodel.CompanyInfo;
import dbmodel.Invoice;
import dbmodel.NotificationCategory;
import dbmodel.NotificationMessage;
import dbmodel.Notyfication;
import dbmodel.Order;
import dbmodel.OrderMessage;
import dbmodel.Receipt;
import dbmodel.SysEmailConfiguration;
import dbmodel.Users;
import dbmodel.Warehouse;
import mailmenager.MailSender;
import restapi.register.RegisterRestData;
import restapi.user.UserRestData;
import smbcustsrv.testinteface.IntegrationTest;
import utils.SmallSmbUtils;
import utils.Status;

@RunWith(Arquillian.class)
public class TestPoprawaDanychUzytkownika implements IntegrationTest{
	
	 @Deployment
	   public static WebArchive createTestArchive() {
	      return ShrinkWrap.create(WebArchive.class, "test.war")
	    		  .addAsLibraries(new File("C:\\Users\\Czarek\\git\\smbapp\\smbcustsrv\\src\\lib\\json.jar"))
	    		  .addClasses(IntegrationTest.class,
	    				      RegisterRestData.class,
	    				      UserRestData.class,
	    				      SmallSmbUtils.class,
	    				      Status.class,
	    				      MailSender.class,
	    				      SystemMailCongifurationDAO.class,
	    				      UserDAO.class,
	    				      AbstractDAO.class,
	    				      RegisterLogicController.class,
	    				      UserLogicController.class,
	    				  	  Users.class,
	    				  	  SysEmailConfiguration.class,
	    				      CompanyInfo.class,
		        		 	  Invoice.class,
		        			  NotificationCategory.class,
		        			  CommodityCategory.class,
		        			  Warehouse.class,
		        			  Receipt.class,
		        			  Commodity.class,
		        			  CommoditiesImage.class,
		        			  OrderMessage.class,
		        			  Order.class,
		        			  Notyfication.class,
		        			  NotificationMessage.class)
	    		  .addAsResource("META-INF/persistence.xml");
	   }
	
		@EJB
		RegisterLogicController registerLogicController;
		
		@EJB
		UserLogicController userLogicController;
		
		@EJB
		UserDAO userDAO;

	private RegisterRestData registerRestData = null;
	private UserRestData userRestData = null;
	private  Users user = null;

	@Before
	public void initDataToTest(){
	//inicjalizacja danych testowego klienta
		 registerRestData = new RegisterRestData();
		 registerRestData.url = "http://localhost:8080/smbfront/#/rejestracja_osoba";
		 registerRestData.username = "test123";
		 registerRestData.password = "test123";
		 registerRestData.name = "Test";
		 registerRestData.surname = "Testowy";
		 registerRestData.email = "smbpracinz@gmail.com";
		 registerRestData.phone = "777777777";
		 registerRestData.street = "Testowa";
		 registerRestData.buildingNumber = "1";
		 registerRestData.city = "Testowe";
		 registerRestData.postCode = "11-111";
	
	//nowe dane do poprawienia
			userRestData  = new UserRestData();
			userRestData.name = "TestNEW";
			userRestData.surname = "TestowyNEW";
			userRestData.email = "smbpracinz@gmail.com";
			userRestData.phone = "22222222";
			userRestData.street = "TestowaNEW";
			userRestData.buildingNumber = "11";
			userRestData.city = "TestoweNEW";
			userRestData.postCode = "21-111";
	
	}
	
	
	@Test
	@InSequence(1)
	public void cleanBeforeTest() throws AddressException, JSONException, MessagingException {
		 user = new Users();
		 user.setLogin(registerRestData.username);
		 List<Users> resultList = userDAO.findEntity(user);
			if(resultList.size() > 0){
				for (Users uzyt : resultList) {
					userDAO.delete(uzyt);
				}
			}
	}
		
	@Test
	@InSequence(2)
	public void registerUser() throws AddressException, JSONException, MessagingException {
		registerLogicController.registerUser(registerRestData);
		
		 user = new Users();
		 user.setLogin(registerRestData.username);
		List<Users> resultList = userDAO.findEntity(user);
		
		assertNotNull(resultList);
		assertEquals(resultList.size(), 1);
			
		user = resultList.iterator().next();
		 
		assertNotNull(user);
		assertNotNull(user.getId());
	    assertEquals(user.getLogin(), registerRestData.username);
		assertEquals(SmallSmbUtils.decodeString(user.getPassword()), registerRestData.password);
		assertEquals(user.getName(), registerRestData.name);
		assertEquals(user.getSurname(), registerRestData.surname);
		assertEquals(user.getEmail(), registerRestData.email);
		assertEquals(user.getPhone(), registerRestData.phone);
		assertEquals(user.getStreet(), registerRestData.street);
		assertEquals(user.getBuildingNumber(), registerRestData.buildingNumber);
		assertEquals(user.getCity(), registerRestData.city);
		assertEquals(user.getPostCode(), registerRestData.postCode);
		assertEquals(user.getState(), Status.USER_STATE.NEW);
		assertEquals(user.getRole(), Status.USER_ROLE.CUSTOMER);
		
		
	}
	
	
	@Test
	@InSequence(3)
	public void changeUserData() throws AddressException, JSONException, MessagingException {
		 user = new Users();
		 user.setLogin(registerRestData.username);
		 List<Users> resultList = userDAO.findEntity(user);
		 user = resultList.iterator().next();
		 
		userRestData.idUser = user.getId();
		userLogicController.saveUsersData(userRestData);
		
		user = userDAO.findEntityByID(user.getId());
		
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(user.getName(), userRestData.name);
		assertEquals(user.getSurname(), userRestData.surname);
		assertEquals(user.getEmail(), userRestData.email);
		assertEquals(user.getPhone(), userRestData.phone);
		assertEquals(user.getStreet(), userRestData.street);
		assertEquals(user.getBuildingNumber(), userRestData.buildingNumber);
		assertEquals(user.getCity(), userRestData.city);
		assertEquals(user.getPostCode(), userRestData.postCode);
		assertEquals(user.getState(), Status.USER_STATE.NEW);
		assertEquals(user.getRole(), Status.USER_ROLE.CUSTOMER);
		
	}
	
	@Test
	@InSequence(4)
	public void cleanAfterTest(){
		userDAO.delete(user);
	}
	

}
