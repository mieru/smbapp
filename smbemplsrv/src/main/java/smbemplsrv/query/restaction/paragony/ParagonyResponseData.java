package smbemplsrv.query.restaction.paragony;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ParagonyResponseData{
	@XmlElement(name="id_uzytkownika")
    public String idUser;
    @XmlElement(name="id_paragonu")
    public Integer idParagonu;
}
