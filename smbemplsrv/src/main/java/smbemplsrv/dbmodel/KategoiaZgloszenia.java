package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the kategoia_zgloszenia database table.
 * 
 */
@Entity
@Table(name="kategoia_zgloszenia")
@NamedQuery(name="KategoiaZgloszenia.findAll", query="SELECT k FROM KategoiaZgloszenia k")
public class KategoiaZgloszenia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_kategrorii")
	private Integer idKategrorii;

	private String nazwa;

	//bi-directional many-to-one association to Zgloszenie
	@OneToMany(mappedBy="kategoiaZgloszenia", fetch=FetchType.EAGER)
	private List<Zgloszenie> zgloszenies;

	public KategoiaZgloszenia() {
	}

	public Integer getIdKategrorii() {
		return this.idKategrorii;
	}

	public void setIdKategrorii(Integer idKategrorii) {
		this.idKategrorii = idKategrorii;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public List<Zgloszenie> getZgloszenies() {
		return this.zgloszenies;
	}

	public void setZgloszenies(List<Zgloszenie> zgloszenies) {
		this.zgloszenies = zgloszenies;
	}

	public Zgloszenie addZgloszeny(Zgloszenie zgloszeny) {
		getZgloszenies().add(zgloszeny);
		zgloszeny.setKategoiaZgloszenia(this);

		return zgloszeny;
	}

	public Zgloszenie removeZgloszeny(Zgloszenie zgloszeny) {
		getZgloszenies().remove(zgloszeny);
		zgloszeny.setKategoiaZgloszenia(null);

		return zgloszeny;
	}

}