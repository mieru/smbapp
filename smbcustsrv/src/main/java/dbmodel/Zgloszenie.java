package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the zgloszenie database table.
 * 
 */
@Entity
@NamedQuery(name="Zgloszenie.findAll", query="SELECT z FROM Zgloszenie z")
public class Zgloszenie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_zgloszenia")
	private Integer idZgloszenia;

	@Temporal(TemporalType.DATE)
	@Column(name="data_zamkniecia")
	private Date dataZamkniecia;

	@Temporal(TemporalType.DATE)
	@Column(name="data_zgloszenia")
	private Date dataZgloszenia;

	@Column(name="numer_zgloszenia")
	private String numerZgloszenia;

	private String status;

	private String temat;

	private String tresc;

	//bi-directional many-to-one association to KategoiaZgloszenia
	@ManyToOne
	@JoinColumn(name="id_kategorii")
	private KategoiaZgloszenia kategoiaZgloszenia;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_pracownika_obsl")
	private Uzytkownik uzytkownik1;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="id_zglaszajacego")
	private Uzytkownik uzytkownik2;

	//bi-directional many-to-one association to ZgloszenieKomentarz
	@OneToMany(mappedBy="zgloszenie", fetch=FetchType.EAGER)
	private List<ZgloszenieKomentarz> zgloszenieKomentarzs;

	public Zgloszenie() {
	}

	public Integer getIdZgloszenia() {
		return this.idZgloszenia;
	}

	public void setIdZgloszenia(Integer idZgloszenia) {
		this.idZgloszenia = idZgloszenia;
	}

	public Date getDataZamkniecia() {
		return this.dataZamkniecia;
	}

	public void setDataZamkniecia(Date dataZamkniecia) {
		this.dataZamkniecia = dataZamkniecia;
	}

	public Date getDataZgloszenia() {
		return this.dataZgloszenia;
	}

	public void setDataZgloszenia(Date dataZgloszenia) {
		this.dataZgloszenia = dataZgloszenia;
	}

	public String getNumerZgloszenia() {
		return this.numerZgloszenia;
	}

	public void setNumerZgloszenia(String numerZgloszenia) {
		this.numerZgloszenia = numerZgloszenia;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTemat() {
		return this.temat;
	}

	public void setTemat(String temat) {
		this.temat = temat;
	}

	public String getTresc() {
		return this.tresc;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public KategoiaZgloszenia getKategoiaZgloszenia() {
		return this.kategoiaZgloszenia;
	}

	public void setKategoiaZgloszenia(KategoiaZgloszenia kategoiaZgloszenia) {
		this.kategoiaZgloszenia = kategoiaZgloszenia;
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

	public List<ZgloszenieKomentarz> getZgloszenieKomentarzs() {
		return this.zgloszenieKomentarzs;
	}

	public void setZgloszenieKomentarzs(List<ZgloszenieKomentarz> zgloszenieKomentarzs) {
		this.zgloszenieKomentarzs = zgloszenieKomentarzs;
	}

	public ZgloszenieKomentarz addZgloszenieKomentarz(ZgloszenieKomentarz zgloszenieKomentarz) {
		getZgloszenieKomentarzs().add(zgloszenieKomentarz);
		zgloszenieKomentarz.setZgloszenie(this);

		return zgloszenieKomentarz;
	}

	public ZgloszenieKomentarz removeZgloszenieKomentarz(ZgloszenieKomentarz zgloszenieKomentarz) {
		getZgloszenieKomentarzs().remove(zgloszenieKomentarz);
		zgloszenieKomentarz.setZgloszenie(null);

		return zgloszenieKomentarz;
	}

}