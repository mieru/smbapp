package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the magazyn database table.
 * 
 */
@Entity
@NamedQuery(name="Magazyn.findAll", query="SELECT m FROM Magazyn m")
public class Magazyn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_magazynu")
	private Integer idMagazynu;

	private String kod;

	private String nazwa;

	private String opis;

	//bi-directional many-to-one association to Towar
	@OneToMany(mappedBy="magazyn", fetch=FetchType.EAGER)
	private List<Towar> towars;

	public Magazyn() {
	}

	public Integer getIdMagazynu() {
		return this.idMagazynu;
	}

	public void setIdMagazynu(Integer idMagazynu) {
		this.idMagazynu = idMagazynu;
	}

	public String getKod() {
		return this.kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
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

	public List<Towar> getTowars() {
		return this.towars;
	}

	public void setTowars(List<Towar> towars) {
		this.towars = towars;
	}

	public Towar addTowar(Towar towar) {
		getTowars().add(towar);
		towar.setMagazyn(this);

		return towar;
	}

	public Towar removeTowar(Towar towar) {
		getTowars().remove(towar);
		towar.setMagazyn(null);

		return towar;
	}

}