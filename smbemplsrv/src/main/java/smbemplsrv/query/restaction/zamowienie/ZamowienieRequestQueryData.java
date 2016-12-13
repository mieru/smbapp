package smbemplsrv.query.restaction.zamowienie;

import javax.xml.bind.annotation.XmlElement;

public class ZamowienieRequestQueryData {
		@XmlElement(name="id_zamowienia")
	    public Integer idZamowienia;
		@XmlElement(name="id_uzytkownika")
	    public Integer idUser;
}
