package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the zamowienie_sprzedaz database table.
 * 
 */
@Entity
@Table(name="zamowienie_sprzedaz")
@NamedQuery(name="ZamowienieSprzedaz.findAll", query="SELECT z FROM ZamowienieSprzedaz z")
public class ZamowienieSprzedaz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_zamowienia_sprzedaz")
	private Integer idZamowieniaSprzedaz;

	@Column(name="adres_dostawy")
	private String adresDostawy;

	@Column(name="czy_faktura")
	private Boolean czyFaktura;

	@Column(name="dane_do_faktury")
	private String daneDoFaktury;

	@Column(name="data_zakonczenia")
	private Timestamp dataZakonczenia;

	@Column(name="data_zlozenia")
	private Timestamp dataZlozenia;

	@Column(name="lista_produktow")
	private String listaProduktow;

	@Column(name="numer_zamowienia")
	private String numerZamowienia;

	private String status;

	//bi-directional many-to-one association to FakturaSprzedazy
	@OneToMany(mappedBy="zamowienieSprzedaz", fetch=FetchType.EAGER)
	private List<FakturaSprzedazy> fakturaSprzedazies;

	//bi-directional many-to-one association to WiadomoscZamSprzedaz
	@OneToMany(mappedBy="zamowienieSprzedaz", fetch=FetchType.EAGER)
	private List<WiadomoscZamSprzedaz> wiadomoscZamSprzedazs;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_zamawiajacego")
	private Uzytkownik uzytkownik1;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_pracownika_obsl")
	private Uzytkownik uzytkownik2;

	public ZamowienieSprzedaz() {
	}

	public Integer getIdZamowieniaSprzedaz() {
		return this.idZamowieniaSprzedaz;
	}

	public void setIdZamowieniaSprzedaz(Integer idZamowieniaSprzedaz) {
		this.idZamowieniaSprzedaz = idZamowieniaSprzedaz;
	}

	public String getAdresDostawy() {
		return this.adresDostawy;
	}

	public void setAdresDostawy(String adresDostawy) {
		this.adresDostawy = adresDostawy;
	}

	public Boolean getCzyFaktura() {
		return this.czyFaktura;
	}

	public void setCzyFaktura(Boolean czyFaktura) {
		this.czyFaktura = czyFaktura;
	}

	public String getDaneDoFaktury() {
		return this.daneDoFaktury;
	}

	public void setDaneDoFaktury(String daneDoFaktury) {
		this.daneDoFaktury = daneDoFaktury;
	}

	public Timestamp getDataZakonczenia() {
		return this.dataZakonczenia;
	}

	public void setDataZakonczenia(Timestamp dataZakonczenia) {
		this.dataZakonczenia = dataZakonczenia;
	}

	public Timestamp getDataZlozenia() {
		return this.dataZlozenia;
	}

	public void setDataZlozenia(Timestamp dataZlozenia) {
		this.dataZlozenia = dataZlozenia;
	}

	public String getListaProduktow() {
		return this.listaProduktow;
	}

	public void setListaProduktow(String listaProduktow) {
		this.listaProduktow = listaProduktow;
	}

	public String getNumerZamowienia() {
		return this.numerZamowienia;
	}

	public void setNumerZamowienia(String numerZamowienia) {
		this.numerZamowienia = numerZamowienia;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FakturaSprzedazy> getFakturaSprzedazies() {
		return this.fakturaSprzedazies;
	}

	public void setFakturaSprzedazies(List<FakturaSprzedazy> fakturaSprzedazies) {
		this.fakturaSprzedazies = fakturaSprzedazies;
	}

	public FakturaSprzedazy addFakturaSprzedazy(FakturaSprzedazy fakturaSprzedazy) {
		getFakturaSprzedazies().add(fakturaSprzedazy);
		fakturaSprzedazy.setZamowienieSprzedaz(this);

		return fakturaSprzedazy;
	}

	public FakturaSprzedazy removeFakturaSprzedazy(FakturaSprzedazy fakturaSprzedazy) {
		getFakturaSprzedazies().remove(fakturaSprzedazy);
		fakturaSprzedazy.setZamowienieSprzedaz(null);

		return fakturaSprzedazy;
	}

	public List<WiadomoscZamSprzedaz> getWiadomoscZamSprzedazs() {
		return this.wiadomoscZamSprzedazs;
	}

	public void setWiadomoscZamSprzedazs(List<WiadomoscZamSprzedaz> wiadomoscZamSprzedazs) {
		this.wiadomoscZamSprzedazs = wiadomoscZamSprzedazs;
	}

	public WiadomoscZamSprzedaz addWiadomoscZamSprzedaz(WiadomoscZamSprzedaz wiadomoscZamSprzedaz) {
		getWiadomoscZamSprzedazs().add(wiadomoscZamSprzedaz);
		wiadomoscZamSprzedaz.setZamowienieSprzedaz(this);

		return wiadomoscZamSprzedaz;
	}

	public WiadomoscZamSprzedaz removeWiadomoscZamSprzedaz(WiadomoscZamSprzedaz wiadomoscZamSprzedaz) {
		getWiadomoscZamSprzedazs().remove(wiadomoscZamSprzedaz);
		wiadomoscZamSprzedaz.setZamowienieSprzedaz(null);

		return wiadomoscZamSprzedaz;
	}

	public Uzytkownik getUzytkownik1() {
		return this.uzytkownik1;
	}

	public void setUzytkownik1(Uzytkownik uzytkownik1) {
		this.uzytkownik1 = uzytkownik1;
	}

	public Uzytkownik getUzytkownik2() {
		return this.uzytkownik2;
	}

	public void setUzytkownik2(Uzytkownik uzytkownik2) {
		this.uzytkownik2 = uzytkownik2;
	}

}