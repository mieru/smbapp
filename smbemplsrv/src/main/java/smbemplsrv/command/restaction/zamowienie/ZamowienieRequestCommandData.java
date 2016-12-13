package smbemplsrv.command.restaction.zamowienie;


import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlElement;

public class ZamowienieRequestCommandData {
	    @XmlElement(name="adresDostawy")
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
	    @XmlElement(name="id")
	    public Integer idZamowienia;
	    @XmlElement(name="tresc")
	    public String trescWiadomosci;
	    @XmlElement(name="id_uzytkownika")
	    public Integer idUzytkownika;
	    @XmlElement(name="sumaPodatkuVat")
	    public Double sumaPodatkuVat;
	    @XmlElement(name="wartoscAllBrutto")
	    public Double wartoscAllBrutto;
	    @XmlElement(name="wartoscAllNetto")
	    public Double wartoscAllNetto;
	    @XmlElement(name="dataZlozenia")
	    public Timestamp dataZlozenia;
	    @XmlElement(name="klient")
	    public String klient;
	    @XmlElement(name="aktywnosci")
	    public String aktywnosci;
}
