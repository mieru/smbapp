package smbemplsrv.query.restaction.zgloszenia;

import javax.xml.bind.annotation.XmlElement;

public class ZgloszeniaRequestQueryData {
	 	@XmlElement(name="id_kategorii")
	    public Integer idKategorii;
		@XmlElement(name="id_zgloszenia")
	    public Integer idZgloszenia;
		@XmlElement(name="id_uzytkownika")
	    public Integer idUser;
}
