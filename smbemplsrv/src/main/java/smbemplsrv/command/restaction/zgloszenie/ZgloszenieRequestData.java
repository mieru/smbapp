package smbemplsrv.command.restaction.zgloszenie;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ZgloszenieRequestData{
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

