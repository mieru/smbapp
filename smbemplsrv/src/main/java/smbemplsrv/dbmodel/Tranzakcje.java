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

	@Column(name="id_faktury_sprzed")
	private Integer idFakturySprzed;

	@Column(name="id_paragonu")
	private Integer idParagonu;

	private double kwota;

	@Column(name="lista_produktow")
	private String listaProduktow;

	private String typ;

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

	public Integer getIdFakturySprzed() {
		return this.idFakturySprzed;
	}

	public void setIdFakturySprzed(Integer idFakturySprzed) {
		this.idFakturySprzed = idFakturySprzed;
	}

	public Integer getIdParagonu() {
		return this.idParagonu;
	}

	public void setIdParagonu(Integer idParagonu) {
		this.idParagonu = idParagonu;
	}

	public double getKwota() {
		return this.kwota;
	}

	public void setKwota(double kwota) {
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

}