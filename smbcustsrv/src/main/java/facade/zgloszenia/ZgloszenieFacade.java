package facade.zgloszenia;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import command.ejbcontrol.zglkomentarz.ZgloszenieKomentarzEjbCommandContoller;
import command.ejbcontrol.zgloszenia.ZgloszeniaEjbCommandContoller;
import command.restaction.zgloszenie.ZgloszenieRequestData;
import dbmodel.KategoiaZgloszenia;
import dbmodel.Uzytkownik;
import dbmodel.Zgloszenie;
import dbmodel.ZgloszenieKomentarz;
import mailer.MailSender;
import query.ejbcontrol.kategoriazgloszenie.KategoriaZgloszenieEjbQueryController;
import query.ejbcontrol.uzytkownik.UzytkownikEjbQueryController;
import query.ejbcontrol.zgloszenia.ZgloszeniaEjbQueryController;
import utils.status.Status;

@Stateless
@LocalBean
public class ZgloszenieFacade {
	@EJB
	ZgloszeniaEjbQueryController zgloszeniaEjbQueryController;

	@EJB
	ZgloszeniaEjbCommandContoller zgloszeniaEjbCommandContoller;

	@EJB
	UzytkownikEjbQueryController uzytkownikEjbQueryController;

	@EJB
	KategoriaZgloszenieEjbQueryController kategoriaZgloszenieEjbQueryController;

	@EJB
	ZgloszenieKomentarzEjbCommandContoller zgloszenieKomentarzEjbCommandContoller;
	
	@EJB
	MailSender mailSender;

	public String getKategorieZglosznia()
			throws JSONException, AddressException, MessagingException {
		List<KategoiaZgloszenia> katCol = kategoriaZgloszenieEjbQueryController
				.findAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;

		for (KategoiaZgloszenia kategoiaZgloszenia : katCol) {
			if (kategoiaZgloszenia.getCzyKlient()!=null && kategoiaZgloszenia.getCzyKlient()) {
				jsonObject = new JSONObject();
				jsonObject.put("id", kategoiaZgloszenia.getIdKategrorii());
				jsonObject.put("name", kategoiaZgloszenia.getNazwa());

				jsonArray.put(jsonObject);
			}
		}
		return jsonArray.toString();
	}

	public String getZgloszeniaUzytkownika(ZgloszenieRequestData zgloszenieRequestData)
			throws JSONException, AddressException, MessagingException {
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		List<Zgloszenie> colZgl = null;
		
		if(zgloszenieRequestData.idUser != null){
			Uzytkownik uzytkownik = uzytkownikEjbQueryController.findEntityByID(zgloszenieRequestData.idUser);
			colZgl = uzytkownik.getZgloszenies2();
		}else{
			colZgl = zgloszeniaEjbQueryController.findAll();
		}
		

		for (Zgloszenie zgloszenie : colZgl) {
			jsonObject = new JSONObject();
			jsonObject.put("id", zgloszenie.getIdZgloszenia());
			jsonObject.put("numer_zgl", zgloszenie.getNumerZgloszenia());
			jsonObject.put("data_zgloszenia",
					zgloszenie.getDataZgloszenia().getTime());
			if (zgloszenie.getDataZamkniecia() != null)
				jsonObject.put("data_zamkniecia",
						zgloszenie.getDataZamkniecia().getTime());
			jsonObject.put("kategoria",
					zgloszenie.getKategoiaZgloszenia().getNazwa());
			jsonObject.put("temat", zgloszenie.getTemat());
			if (zgloszenie.getUzytkownik1() != null) {
				jsonObject.put("pracownik_obsl",
						zgloszenie.getUzytkownik1().getName() + " "
								+ zgloszenie.getUzytkownik1().getSurname());
			}
			jsonObject.put("status",
					Status.ZGLOSZENIE_STATE.getText(zgloszenie.getStatus()));

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	public String getZgloszenieById(ZgloszenieRequestData zgloszenieRequestData)
			throws JSONException, AddressException, MessagingException {
		Zgloszenie zgloszenie = zgloszeniaEjbQueryController
				.findEntityByID(zgloszenieRequestData.idZgloszenia);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", zgloszenie.getIdZgloszenia());
		jsonObject.put("numer_zgl", zgloszenie.getNumerZgloszenia());
		jsonObject.put("data_zgloszenia",
				zgloszenie.getDataZgloszenia().getTime());
		if (zgloszenie.getDataZamkniecia() != null)
			jsonObject.put("data_zamkniecia",
					zgloszenie.getDataZamkniecia().getTime());
		jsonObject.put("kategoria",
				zgloszenie.getKategoiaZgloszenia().getNazwa());
		jsonObject.put("temat", zgloszenie.getTemat());
		jsonObject.put("tresc", zgloszenie.getTresc());
		jsonObject.put("aktywnosci",
				getJsonArray(zgloszenie.getZgloszenieKomentarzs()));

		if (zgloszenie.getUzytkownik1() != null) {
			jsonObject.put("pracownik_obsl",
					zgloszenie.getUzytkownik1().getName() + " "
							+ zgloszenie.getUzytkownik1().getSurname());
		}
		jsonObject.put("status",
				Status.ZGLOSZENIE_STATE.getText(zgloszenie.getStatus()));

		return jsonObject.toString();
	}

	private JSONArray getJsonArray(
			List<ZgloszenieKomentarz> zgloszenieKomentarze)
			throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;

		for (ZgloszenieKomentarz zglKom : zgloszenieKomentarze) {
			jsonObject = new JSONObject();
			jsonObject.put("id", zglKom.getIdKomentarzaZgloszenia());
			jsonObject.put("data", zglKom.getDataDodania().getTime());
			jsonObject.put("tresc", zglKom.getTresc());
			String uzytkownikName = "";
			if (zglKom.getUzytkownik().getCompanyName() != null) {
				uzytkownikName = zglKom.getUzytkownik().getCompanyName();
			} else {
				uzytkownikName = zglKom.getUzytkownik().getName() + " "
						+ zglKom.getUzytkownik().getSurname();
			}
			jsonObject.put("uzytkownik", uzytkownikName);
			// jsonObject.put('typ', );

			jsonArray.put(jsonObject);
		}
		return jsonArray;
	}

	public Integer dodajZgloszenie(
			ZgloszenieRequestData zgloszenieRequestData) {
		try {
			KategoiaZgloszenia kategoiaZgloszenia = null;
			Uzytkownik uzytkownik = null;
			if (zgloszenieRequestData.idUser != null) {
				uzytkownik = uzytkownikEjbQueryController
						.findEntityByID(zgloszenieRequestData.idUser);
			}

			if (zgloszenieRequestData.idKategoria != null) {
				kategoiaZgloszenia = kategoriaZgloszenieEjbQueryController
						.findEntityByID(zgloszenieRequestData.idKategoria);
			}

			Zgloszenie zgloszenie = null;

			if (uzytkownik != null && kategoiaZgloszenia != null) {
				zgloszenie = new Zgloszenie();
				zgloszenie.setUzytkownik2(uzytkownik);
				zgloszenie.setTresc(zgloszenieRequestData.tresc);
				zgloszenie.setTemat(zgloszenieRequestData.temat);
				zgloszenie.setStatus(Status.ZGLOSZENIE_STATE.NOWE);
				zgloszenie.setNumerZgloszenia(
						zgloszeniaEjbQueryController.generujNumerZgloszenia());
				zgloszenie.setDataZgloszenia(
						new Timestamp(System.currentTimeMillis()));
				zgloszenie.setKategoiaZgloszenia(kategoiaZgloszenia);

				zgloszenie = zgloszeniaEjbCommandContoller.insert(zgloszenie);
			}
			return zgloszenie.getIdZgloszenia();
		} catch (Exception e) {
			return null;
		}
	}

	public void dodajWiadomoscDoZgloszenia(
			ZgloszenieRequestData zgloszenieRequestData) throws AddressException, MessagingException {
		Zgloszenie zgloszenie = null;
		if (zgloszenieRequestData.idZgloszenia != null) {
			zgloszenie = zgloszeniaEjbQueryController
					.findEntityByID(zgloszenieRequestData.idZgloszenia);
		}
		Uzytkownik uzytkownik = null;
		if (zgloszenieRequestData.idUser != null) {
			uzytkownik = uzytkownikEjbQueryController
					.findEntityByID(zgloszenieRequestData.idUser);
		}

		ZgloszenieKomentarz zgloszenieKomentarz = new ZgloszenieKomentarz();
		zgloszenieKomentarz.setTresc(zgloszenieRequestData.tresc);
		zgloszenieKomentarz.setTyp("K");
		zgloszenieKomentarz
				.setDataDodania(new Timestamp(System.currentTimeMillis()));
		zgloszenieKomentarz.setUzytkownik(uzytkownik);
		zgloszenieKomentarz.setZgloszenie(zgloszenie);

		zgloszenieKomentarzEjbCommandContoller.insert(zgloszenieKomentarz);
		
		mailSender.sendMail("Nowa wiadomosc do zgloszenia nr : " + zgloszenie.getNumerZgloszenia(), "Dodanow wiadomosc do zgłoszenia..", zgloszenie.getUzytkownik1().getMail());

	}

}
