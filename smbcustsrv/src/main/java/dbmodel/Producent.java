package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the producent database table.
 * 
 */
@Entity
@NamedQuery(name="Producent.findAll", query="SELECT p FROM Producent p")
public class Producent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producena")
	private Integer idProducena;

	private String nazwa;

	private String opis;

	//bi-directional many-to-one association to Towar
	@OneToMany(mappedBy="producent", fetch=FetchType.EAGER)
	private List<Towar> towars;

	public Producent() {
	}

	public Integer getIdProducena() {
		return this.idProducena;
	}

	public void setIdProducena(Integer idProducena) {
		this.idProducena = idProducena;
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
		towar.setProducent(this);

		return towar;
	}

	public Towar removeTowar(Towar towar) {
		getTowars().remove(towar);
		towar.setProducent(null);

		return towar;
	}

}