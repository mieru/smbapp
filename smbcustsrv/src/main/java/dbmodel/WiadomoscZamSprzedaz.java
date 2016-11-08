package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the wiadomosc_zam_sprzedaz database table.
 * 
 */
@Entity
@Table(name="wiadomosc_zam_sprzedaz")
@NamedQuery(name="WiadomoscZamSprzedaz.findAll", query="SELECT w FROM WiadomoscZamSprzedaz w")
public class WiadomoscZamSprzedaz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_wiad_zam_sprzed")
	private Integer idWiadZamSprzed;

	@Temporal(TemporalType.DATE)
	@Column(name="data_dodanie")
	private Date dataDodanie;

	private String tresc;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_dodajacego")
	private Uzytkownik uzytkownik;

	//bi-directional many-to-one association to ZamowienieSprzedaz
	@ManyToOne
	@JoinColumn(name="id_zamowienia")
	private ZamowienieSprzedaz zamowienieSprzedaz;

	public WiadomoscZamSprzedaz() {
	}

	public Integer getIdWiadZamSprzed() {
		return this.idWiadZamSprzed;
	}

	public void setIdWiadZamSprzed(Integer idWiadZamSprzed) {
		this.idWiadZamSprzed = idWiadZamSprzed;
	}

	public Date getDataDodanie() {
		return this.dataDodanie;
	}

	public void setDataDodanie(Date dataDodanie) {
		this.dataDodanie = dataDodanie;
	}

	public String getTresc() {
		return this.tresc;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

	public ZamowienieSprzedaz getZamowienieSprzedaz() {
		return this.zamowienieSprzedaz;
	}

	public void setZamowienieSprzedaz(ZamowienieSprzedaz zamowienieSprzedaz) {
		this.zamowienieSprzedaz = zamowienieSprzedaz;
	}

}