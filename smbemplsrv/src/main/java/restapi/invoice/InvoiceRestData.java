package restapi.invoice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvoiceRestData{
	 	@XmlElement(name="id_uzytkownika")
	    public String idUser;
	    @XmlElement(name="id_faktury")
	    public Integer idFaktury;
	    @XmlElement(name="status")
	    public String status;
}
