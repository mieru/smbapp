package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the towar_image database table.
 * 
 */
@Entity
@Table(name="towar_image")
@NamedQuery(name="TowarImage.findAll", query="SELECT t FROM TowarImage t")
public class TowarImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_towar_image")
	private Integer idTowarImage;

	private String image;

	//bi-directional many-to-one association to Towar
	@OneToMany(mappedBy="towarImage", fetch=FetchType.EAGER)
	private List<Towar> towars;

	public TowarImage() {
	}

	public Integer getIdTowarImage() {
		return this.idTowarImage;
	}

	public void setIdTowarImage(Integer idTowarImage) {
		this.idTowarImage = idTowarImage;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Towar> getTowars() {
		return this.towars;
	}

	public void setTowars(List<Towar> towars) {
		this.towars = towars;
	}

	public Towar addTowar(Towar towar) {
		getTowars().add(towar);
		towar.setTowarImage(this);

		return towar;
	}

	public Towar removeTowar(Towar towar) {
		getTowars().remove(towar);
		towar.setTowarImage(null);

		return towar;
	}

}