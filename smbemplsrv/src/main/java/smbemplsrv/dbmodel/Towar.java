package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the towar database table.
 * 
 */
@Entity
@NamedQuery(name="Towar.findAll", query="SELECT t FROM Towar t")
public class Towar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_towaru")
	private Integer idTowaru;

	@Column(name="cena_netto")
	private double cenaNetto;

	@Column(name="ilosc_w_magazynie")
	private Integer iloscWMagazynie;

	private String jednostka;

	private String nazwa;

	private String opis;

	@Column(name="stan_min_sys")
	private Integer stanMinSys;

	@Column(name="stan_min_uzyt")
	private Integer stanMinUzyt;

	@Column(name="stan_minimalny")
	private Integer stanMinimalny;

	@Column(name="stawka_vat")
	private Integer stawkaVat;

	//bi-directional many-to-one association to KategoriaTowar
	@ManyToOne
	@JoinColumn(name="id_kategorii")
	private KategoriaTowar kategoriaTowar;

	//bi-directional many-to-one association to Magazyn
	@ManyToOne
	@JoinColumn(name="id_magazynu")
	private Magazyn magazyn;

	//bi-directional many-to-one association to TowarImage
	@ManyToOne
	@JoinColumn(name="id_towaru_image")
	private TowarImage towarImage;

	public Towar() {
	}

	public Integer getIdTowaru() {
		return this.idTowaru;
	}

	public void setIdTowaru(Integer idTowaru) {
		this.idTowaru = idTowaru;
	}

	public double getCenaNetto() {
		return this.cenaNetto;
	}

	public void setCenaNetto(double cenaNetto) {
		this.cenaNetto = cenaNetto;
	}

	public Integer getIloscWMagazynie() {
		return this.iloscWMagazynie;
	}

	public void setIloscWMagazynie(Integer iloscWMagazynie) {
		this.iloscWMagazynie = iloscWMagazynie;
	}

	public String getJednostka() {
		return this.jednostka;
	}

	public void setJednostka(String jednostka) {
		this.jednostka = jednostka;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getStanMinSys() {
		return this.stanMinSys;
	}

	public void setStanMinSys(Integer stanMinSys) {
		this.stanMinSys = stanMinSys;
	}

	public Integer getStanMinUzyt() {
		return this.stanMinUzyt;
	}

	public void setStanMinUzyt(Integer stanMinUzyt) {
		this.stanMinUzyt = stanMinUzyt;
	}

	public Integer getStanMinimalny() {
		return this.stanMinimalny;
	}

	public void setStanMinimalny(Integer stanMinimalny) {
		this.stanMinimalny = stanMinimalny;
	}

	public Integer getStawkaVat() {
		return this.stawkaVat;
	}

	public void setStawkaVat(Integer stawkaVat) {
		this.stawkaVat = stawkaVat;
	}

	public KategoriaTowar getKategoriaTowar() {
		return this.kategoriaTowar;
	}

	public void setKategoriaTowar(KategoriaTowar kategoriaTowar) {
		this.kategoriaTowar = kategoriaTowar;
	}

	public Magazyn getMagazyn() {
		return this.magazyn;
	}

	public void setMagazyn(Magazyn magazyn) {
		this.magazyn = magazyn;
	}

	public TowarImage getTowarImage() {
		return this.towarImage;
	}

	public void setTowarImage(TowarImage towarImage) {
		this.towarImage = towarImage;
	}

}