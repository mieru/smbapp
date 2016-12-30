package restapi.receipt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReceiptRestData{
	@XmlElement(name="id_uzytkownika")
    public String idUser;
    @XmlElement(name="id_paragonu")
    public Integer idParagonu;
}
