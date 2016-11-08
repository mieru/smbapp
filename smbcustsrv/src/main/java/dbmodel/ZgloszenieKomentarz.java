package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the zgloszenie_komentarz database table.
 * 
 */
@Entity
@Table(name="zgloszenie_komentarz")
@NamedQuery(name="ZgloszenieKomentarz.findAll", query="SELECT z FROM ZgloszenieKomentarz z")
public class ZgloszenieKomentarz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_komentarza_zgloszenia")
	private Integer idKomentarzaZgloszenia;

	@Temporal(TemporalType.DATE)
	@Column(name="data_dodania")
	private Date dataDodania;

	private String tresc;

	private String typ;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_uzytkownika_dodajacego")
	private Uzytkownik uzytkownik;

	//bi-directional many-to-one association to Zgloszenie
	@ManyToOne
	@JoinColumn(name="id_zgloszenia")
	private Zgloszenie zgloszenie;

	public ZgloszenieKomentarz() {
	}

	public Integer getIdKomentarzaZgloszenia() {
		return this.idKomentarzaZgloszenia;
	}

	public void setIdKomentarzaZgloszenia(Integer idKomentarzaZgloszenia) {
		this.idKomentarzaZgloszenia = idKomentarzaZgloszenia;
	}

	public Date getDataDodania() {
		return this.dataDodania;
	}

	public void setDataDodania(Date dataDodania) {
		this.dataDodania = dataDodania;
	}

	public String getTresc() {
		return this.tresc;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public String getTyp() {
		return this.typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

	public Zgloszenie getZgloszenie() {
		return this.zgloszenie;
	}

	public void setZgloszenie(Zgloszenie zgloszenie) {
		this.zgloszenie = zgloszenie;
	}

}