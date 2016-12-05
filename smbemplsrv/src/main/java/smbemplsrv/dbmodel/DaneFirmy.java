package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dane_firmy database table.
 * 
 */
@Entity
@Table(name="dane_firmy")
@NamedQuery(name="DaneFirmy.findAll", query="SELECT d FROM DaneFirmy d")
public class DaneFirmy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_danych_fimy")
	private Integer idDanychFimy;

	@Column(name="kod_pocztowy")
	private String kodPocztowy;

	private String krs;

	private String mail;

	private String miasto;

	private String nazwa;

	private String nip;

	@Column(name="nr_budynku")
	private String nrBudynku;

	@Column(name="nr_lokalu")
	private String nrLokalu;

	private String regon;

	private String telefon;

	private String ulica;

	public DaneFirmy() {
	}

	public Integer getIdDanychFimy() {
		return this.idDanychFimy;
	}

	public void setIdDanychFimy(Integer idDanychFimy) {
		this.idDanychFimy = idDanychFimy;
	}

	public String getKodPocztowy() {
		return this.kodPocztowy;
	}

	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}

	public String getKrs() {
		return this.krs;
	}

	public void setKrs(String krs) {
		this.krs = krs;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMiasto() {
		return this.miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNrBudynku() {
		return this.nrBudynku;
	}

	public void setNrBudynku(String nrBudynku) {
		this.nrBudynku = nrBudynku;
	}

	public String getNrLokalu() {
		return this.nrLokalu;
	}

	public void setNrLokalu(String nrLokalu) {
		this.nrLokalu = nrLokalu;
	}

	public String getRegon() {
		return this.regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getUlica() {
		return this.ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

}