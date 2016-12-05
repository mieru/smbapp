package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the kategoria_towar database table.
 * 
 */
@Entity
@Table(name="kategoria_towar")
@NamedQuery(name="KategoriaTowar.findAll", query="SELECT k FROM KategoriaTowar k")
public class KategoriaTowar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_kat_towar")
	private Integer idKatTowar;

	private String nazwa;

	//bi-directional many-to-one association to Towar
	@OneToMany(mappedBy="kategoriaTowar", fetch=FetchType.EAGER)
	private List<Towar> towars;

	public KategoriaTowar() {
	}

	public Integer getIdKatTowar() {
		return this.idKatTowar;
	}

	public void setIdKatTowar(Integer idKatTowar) {
		this.idKatTowar = idKatTowar;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<Towar> getTowars() {
		return this.towars;
	}

	public void setTowars(List<Towar> towars) {
		this.towars = towars;
	}

	public Towar addTowar(Towar towar) {
		getTowars().add(towar);
		towar.setKategoriaTowar(this);

		return towar;
	}

	public Towar removeTowar(Towar towar) {
		getTowars().remove(towar);
		towar.setKategoriaTowar(null);

		return towar;
	}

}