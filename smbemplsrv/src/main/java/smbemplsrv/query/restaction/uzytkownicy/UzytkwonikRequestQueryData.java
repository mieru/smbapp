package smbemplsrv.query.restaction.uzytkownicy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UzytkwonikRequestQueryData {
	@XmlElement(name = "id")
	public Integer idUzytkownika;
	@XmlElement(name = "type")
	public String type;
}
