package utils;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SmbUtil {

	@PersistenceContext(unitName = "smbcustsrv")
	private  EntityManager entityManager;

	public String generujNumerZgloszenia() {

		Integer id = (Integer) entityManager.createQuery("SELECT MAX(Zamowienie.id_zamowienia) FROM Zamowienie Zamowienie;")
				.getSingleResult();
		Date date = new Date(System.currentTimeMillis());
		return date.getYear() + "/" + date.getMonth() + "/" + id;
	}

}
