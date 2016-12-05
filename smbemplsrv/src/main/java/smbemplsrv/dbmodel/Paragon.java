package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the paragon database table.
 * 
 */
@Entity
@NamedQuery(name="Paragon.findAll", query="SELECT p FROM Paragon p")
public class Paragon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_paragonu")
	private Integer idParagonu;

	@Column(name="dane_wystawiajacego")
	private String daneWystawiajacego;

	@Column(name="data_wystawienia")
	private String dataWystawienia;

	@Column(name="lista_towarow")
	private String listaTowarow;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_pacownika_wyst")
	private Uzytkownik uzytkownik;

	public Paragon() {
	}

	public Integer getIdParagonu() {
		return this.idParagonu;
	}

	public void setIdParagonu(Integer idParagonu) {
		this.idParagonu = idParagonu;
	}

	public String getDaneWystawiajacego() {
		return this.daneWystawiajacego;
	}

	public void setDaneWystawiajacego(String daneWystawiajacego) {
		this.daneWystawiajacego = daneWystawiajacego;
	}

	public String getDataWystawienia() {
		return this.dataWystawienia;
	}

	public void setDataWystawienia(String dataWystawienia) {
		this.dataWystawienia = dataWystawienia;
	}

	public String getListaTowarow() {
		return this.listaTowarow;
	}

	public void setListaTowarow(String listaTowarow) {
		this.listaTowarow = listaTowarow;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}