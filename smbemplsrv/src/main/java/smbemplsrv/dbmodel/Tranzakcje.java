package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tranzakcje database table.
 * 
 */
@Entity
@NamedQuery(name="Tranzakcje.findAll", query="SELECT t FROM Tranzakcje t")
public class Tranzakcje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_tranzakcji")
	private Integer idTranzakcji;

	@Column(name="data_tanzakcji")
	private Timestamp dataTanzakcji;

	@Column(name="id_faktury_kup")
	private Integer idFakturyKup;

	private Double kwota;

	@Column(name="lista_produktow")
	private String listaProduktow;

	private String typ;

	//bi-directional many-to-one association to FakturaSprzedazy
	@ManyToOne
	@JoinColumn(name="id_faktury_sprzed")
	private FakturaSprzedazy fakturaSprzedazy;

	//bi-directional many-to-one association to Paragon
	@ManyToOne
	@JoinColumn(name="id_paragonu")
	private Paragon paragon;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_pracownika")
	private Uzytkownik uzytkownik;

	public Tranzakcje() {
	}

	public Integer getIdTranzakcji() {
		return this.idTranzakcji;
	}

	public void setIdTranzakcji(Integer idTranzakcji) {
		this.idTranzakcji = idTranzakcji;
	}

	public Timestamp getDataTanzakcji() {
		return this.dataTanzakcji;
	}

	public void setDataTanzakcji(Timestamp dataTanzakcji) {
		this.dataTanzakcji = dataTanzakcji;
	}

	public Integer getIdFakturyKup() {
		return this.idFakturyKup;
	}

	public void setIdFakturyKup(Integer idFakturyKup) {
		this.idFakturyKup = idFakturyKup;
	}

	public Double getKwota() {
		return this.kwota;
	}

	public void setKwota(Double kwota) {
		this.kwota = kwota;
	}

	public String getListaProduktow() {
		return this.listaProduktow;
	}

	public void setListaProduktow(String listaProduktow) {
		this.listaProduktow = listaProduktow;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public FakturaSprzedazy getFakturaSprzedazy() {
		return this.fakturaSprzedazy;
	}

	public void setFakturaSprzedazy(FakturaSprzedazy fakturaSprzedazy) {
		this.fakturaSprzedazy = fakturaSprzedazy;
	}

	public Paragon getParagon() {
		return this.paragon;
	}

	public void setParagon(Paragon paragon) {
		this.paragon = paragon;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}