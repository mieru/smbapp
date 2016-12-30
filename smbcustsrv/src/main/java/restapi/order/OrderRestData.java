package restapi.order;

import javax.xml.bind.annotation.XmlElement;

public class OrderRestData {
	    @XmlElement(name="adres_dostawy")
	    public String adresDostawy;
	    @XmlElement(name="czyFaktura")
	    public Boolean czyFaktura;
	    @XmlElement(name="dane_do_faktury")
	    public String daneDoFaktury;
	    @XmlElement(name="koszyk_info")
	    public String koszykInfo;
	    @XmlElement(name="lista")
	    public String listaPoduktow;
	    @XmlElement(name="wiad_do_sprz")
	    public String wiadDoSprzedawcy;
	    @XmlElement(name="id_zamawiajacego")
	    public Integer idZamawiajacego;
	    @XmlElement(name="tresc")
	    public String trescWiadomosci;
	    @XmlElement(name="id_uzytkownika")
	    public String idUser;
	    @XmlElement(name="id_zamowienia")
	    public Integer idZamowienia;
	    @XmlElement(name="status")
	    public String status;
	
}
