package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the faktura_sprzedazy database table.
 * 
 */
@Entity
@Table(name="faktura_sprzedazy")
@NamedQuery(name="FakturaSprzedazy.findAll", query="SELECT f FROM FakturaSprzedazy f")
public class FakturaSprzedazy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_faktury")
	private Integer idFaktury;

	private String adnotacja;

	@Column(name="dane_klienta")
	private String daneKlienta;

	@Column(name="dane_wystawiajacego")
	private String daneWystawiajacego;

	@Column(name="data_wystawienia")
	private Timestamp dataWystawienia;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_path")
	private String filePath;

	@Column(name="lista_towarow")
	private String listaTowarow;

	@Column(name="numer_faktury")
	private String numerFaktury;

	private String status;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_klienta")
	private Uzytkownik uzytkownik1;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_pracownika_wystaw")
	private Uzytkownik uzytkownik2;

	//bi-directional many-to-one association to ZamowienieSprzedaz
	@ManyToOne
	@JoinColumn(name="id_zamowienia")
	private ZamowienieSprzedaz zamowienieSprzedaz;

	public FakturaSprzedazy() {
	}

	public Integer getIdFaktury() {
		return this.idFaktury;
	}

	public void setIdFaktury(Integer idFaktury) {
		this.idFaktury = idFaktury;
	}

	public String getAdnotacja() {
		return this.adnotacja;
	}

	public void setAdnotacja(String adnotacja) {
		this.adnotacja = adnotacja;
	}

	public String getDaneKlienta() {
		return this.daneKlienta;
	}

	public void setDaneKlienta(String daneKlienta) {
		this.daneKlienta = daneKlienta;
	}

	public String getDaneWystawiajacego() {
		return this.daneWystawiajacego;
	}

	public void setDaneWystawiajacego(String daneWystawiajacego) {
		this.daneWystawiajacego = daneWystawiajacego;
	}

	public Timestamp getDataWystawienia() {
		return this.dataWystawienia;
	}

	public void setDataWystawienia(Timestamp dataWystawienia) {
		this.dataWystawienia = dataWystawienia;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getListaTowarow() {
		return this.listaTowarow;
	}

	public void setListaTowarow(String listaTowarow) {
		this.listaTowarow = listaTowarow;
	}

	public String getNumerFaktury() {
		return this.numerFaktury;
	}

	public void setNumerFaktury(String numerFaktury) {
		this.numerFaktury = numerFaktury;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public ZamowienieSprzedaz getZamowienieSprzedaz() {
		return this.zamowienieSprzedaz;
	}

	public void setZamowienieSprzedaz(ZamowienieSprzedaz zamowienieSprzedaz) {
		this.zamowienieSprzedaz = zamowienieSprzedaz;
	}

}