package smbemplsrv.query.restaction.tranzakcje;

import javax.xml.bind.annotation.XmlElement;

public class TranzakcjeRequestQueryData {
	 	@XmlElement(name="id_tranzakcji")
	    public Integer idTranzakcji;
		@XmlElement(name="id_uzytkownika")
	    public Integer idUser;
}
