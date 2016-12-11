package smbemplsrv.facade.uzytkownicy;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.uzytkownicy.UzytkownicyEjbCommandController;
import smbemplsrv.command.restaction.uzytkownicy.UzytkownikRequestData;
import smbemplsrv.dbmodel.Uzytkownik;
import smbemplsrv.query.ejbcontrol.uzytkownicy.UzytkownicyEjbQueryController;
import smbemplsrv.query.restaction.uzytkownicy.UzytkwonikRequestQueryData;
import utils.SmbUtil;
import utils.status.Status;

@Stateless
@LocalBean
public class UzytkownicyFacade {

	@EJB
	UzytkownicyEjbQueryController uzytkownicyEjbQueryController;

	@EJB
	UzytkownicyEjbCommandController uzytkownicyEjbCommandController;

	public String getUsers(
			UzytkwonikRequestQueryData uzytkwonikRequestQueryData) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<Uzytkownik> userList = new ArrayList<Uzytkownik>();
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setRole(uzytkwonikRequestQueryData.type);
		
		
		try {
			if(uzytkwonikRequestQueryData.type.equals("E")){
				userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
				uzytkownik.setRole("E,M");
				userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
				uzytkownik.setRole("E,A,M");
				userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
				uzytkownik.setRole("E,A");
				userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
				uzytkownik.setRole("A");
				userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
			}else{
				userList.addAll(uzytkownicyEjbQueryController.findEntity(uzytkownik));
			}
			

			for (Uzytkownik u : userList) {
				jsonObject = new JSONObject();

				jsonObject.put("id_uzytkownika", u.getIdUser());
				jsonObject.put("name", u.getName());
				jsonObject.put("surname", u.getSurname());
				jsonObject.put("login", u.getLogin());
				jsonObject.put("email", u.getMail());
				jsonObject.put("phone", u.getPhone());
				jsonObject.put("street", u.getStreet());
				jsonObject.put("number_house", u.getBuildingNumber());
				jsonObject.put("number_flat", u.getRoomNumber());
				jsonObject.put("city", u.getCity());
				jsonObject.put("post_code", u.getPosCode());
				jsonObject.put("nip", u.getNip());
				jsonObject.put("rola", u.getCustomerType());
				jsonObject.put("upr", Status.USER_ROLE.getText(u.getRole()));
				jsonObject.put("uprKod", u.getRole());
				jsonObject.put("status",
						Status.USER_STATE.getText(u.getState()));
				jsonObject.put("company_name", u.getCompanyName());

				jsonArray.put(jsonObject);
			}

		} catch (Exception e) {
		}
		return jsonArray.toString();

	}

	public String getUserById(UzytkwonikRequestQueryData uzytkwonikRequestQueryData) {
	JSONObject jsonObject = new JSONObject();
	Uzytkownik u = uzytkownicyEjbQueryController.findEntityByID(uzytkwonikRequestQueryData.idUzytkownika);
		jsonObject.put("id_uzytkownika", u.getIdUser());
		jsonObject.put("name", u.getName());
		jsonObject.put("surname", u.getSurname());
		jsonObject.put("login", u.getLogin());
		jsonObject.put("email", u.getMail());
		jsonObject.put("phone", u.getPhone());
		jsonObject.put("street", u.getStreet());
		jsonObject.put("number_house", u.getBuildingNumber());
		jsonObject.put("number_flat", u.getRoomNumber());
		jsonObject.put("city", u.getCity());
		jsonObject.put("post_code", u.getPosCode());
		jsonObject.put("nip", u.getNip());
		jsonObject.put("rola", u.getCustomerType());
		jsonObject.put("upr", Status.USER_ROLE.getText(u.getRole()));
		jsonObject.put("uprKod", u.getRole());
		jsonObject.put("status",
				Status.USER_STATE.getText(u.getState()));
		jsonObject.put("company_name", u.getCompanyName());
		jsonObject.put("password", SmbUtil.decodeString(u.getPassword()));

		return jsonObject.toString();
	}

	public String edytujUzytkownika(UzytkownikRequestData uzytkownikRequestData) {
			JSONObject jsonObject = new JSONObject();
			Uzytkownik uzytkownik = null;
			if(uzytkownikRequestData.idUser != null){
				uzytkownik = uzytkownicyEjbQueryController.findEntityByID(uzytkownikRequestData.idUser);
			}
			
			if(uzytkownik == null){
				jsonObject.put("ERROR", "Nie znaleziono uzytkownika o id = " + uzytkownikRequestData.idUser);
			}else{
				fillUzytkwnik(uzytkownik, uzytkownikRequestData);
				uzytkownicyEjbCommandController.update(uzytkownik);
				jsonObject.put("OK", "OK");
			}
			return jsonObject.toString();
		}
		
		private void fillUzytkwnik(Uzytkownik uzytkownik, UzytkownikRequestData uzytkownikRequestData){
			uzytkownik.setName(uzytkownikRequestData.name);
			if(uzytkownikRequestData.status.length() > 1){
				uzytkownik.setState(Status.USER_STATE.ACTIVE);
			}else{
				uzytkownik.setState(uzytkownikRequestData.status);
			}
			uzytkownik.setSurname(uzytkownikRequestData.surname);
			uzytkownik.setCustomerType(uzytkownikRequestData.customerType);
			uzytkownik.setCompanyName(uzytkownikRequestData.companyName);
			uzytkownik.setNip(uzytkownikRequestData.nip);
			uzytkownik.setMail(uzytkownikRequestData.email);
			uzytkownik.setPhone(uzytkownikRequestData.phone);
			uzytkownik.setStreet(uzytkownikRequestData.street);
			uzytkownik.setBuildingNumber(uzytkownikRequestData.buildingNumber);
			uzytkownik.setRoomNumber(uzytkownikRequestData.flatNumber);
			uzytkownik.setCity(uzytkownikRequestData.city);
			uzytkownik.setPosCode(uzytkownikRequestData.postCode);
			uzytkownik.setRole(uzytkownikRequestData.uprKod);
			uzytkownik.setLogin(uzytkownikRequestData.login);
			uzytkownik.setPassword(SmbUtil.encodeString(uzytkownikRequestData.password));
		}

		public String usunUzytkownika(UzytkownikRequestData konfMailRequestData) {
			Uzytkownik uzytkownik = uzytkownicyEjbQueryController.findEntityByID(konfMailRequestData.idUser);
			uzytkownicyEjbCommandController.delete(uzytkownik);
			return "";
		}

		public String dodajUzytkownika(UzytkownikRequestData uzytkownikRequestData) {
			Uzytkownik uzytkownik = new Uzytkownik();
			fillUzytkwnik(uzytkownik,uzytkownikRequestData);
			uzytkownicyEjbCommandController.insert(uzytkownik);
			return "";
		}

}
