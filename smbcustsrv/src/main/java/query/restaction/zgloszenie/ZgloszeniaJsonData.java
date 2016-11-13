package query.restaction.zgloszenie;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZgloszeniaJsonData{
    @XmlElement(name="id_uzytkownika")
    public String idUser;
    @XmlElement(name="id_zamowienia")
    public Integer idZamowienia;
    @XmlElement(name="status")
    public String status;
}