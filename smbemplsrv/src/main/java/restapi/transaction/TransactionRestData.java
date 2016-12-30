package restapi.transaction;

import javax.xml.bind.annotation.XmlElement;

public class TransactionRestData {
	 	@XmlElement(name="id_tranzakcji")
	    public Integer idTranzakcji;
		@XmlElement(name="id_uzytkownika")
	    public Integer idUser;
	 	@XmlElement(name="czyFaktura")
	    public Boolean czyFaktura;
	    @XmlElement(name="dane_do_faktury")
	    public String daneDoFaktury;
	    @XmlElement(name="koszyk_info")
	    public String koszykInfo;
	    @XmlElement(name="lista")
	    public String listaPoduktow;
	    @XmlElement(name="id_zamowienia")
	    public Integer idZamowienia;
	    @XmlElement(name="id_pracownika")
	    public Integer idPracownika;
	    @XmlElement(name="typ")
	    public String typ;
}
