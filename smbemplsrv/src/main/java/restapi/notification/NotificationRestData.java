package restapi.notification;

import javax.xml.bind.annotation.XmlElement;

public class NotificationRestData {
	 	@XmlElement(name="id_kategorii")
	    public Integer idKategorii;
		@XmlElement(name="id_uzytkownika")
	    public Integer idUser;
	    @XmlElement(name="id_kategoria")
	    public Integer idKategoria;
	    @XmlElement(name="temat")
	    public String temat;
	    @XmlElement(name="tresc")
	    public String tresc;
	    @XmlElement(name="id_zgloszenia")
	    public Integer idZgloszenia;
}
