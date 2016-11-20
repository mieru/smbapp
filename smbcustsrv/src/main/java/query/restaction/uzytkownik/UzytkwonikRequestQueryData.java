package query.restaction.uzytkownik;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UzytkwonikJsonData{
    @XmlElement(name="username")
    public String username;
    @XmlElement(name="password")
    public String password;
    @XmlElement(name="id_uzytkownika")
    public Integer idUser;
}
