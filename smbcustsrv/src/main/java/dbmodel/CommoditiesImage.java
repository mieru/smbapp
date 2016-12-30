package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the commodities_images database table.
 * 
 */
@Entity
@Table(name="commodities_images")
@NamedQuery(name="CommoditiesImage.findAll", query="SELECT c FROM CommoditiesImage c")
public class CommoditiesImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="image_path")
	private String imagePath;

	//bi-directional many-to-one association to Commodity
	@OneToMany(mappedBy="commoditiesImage", fetch=FetchType.EAGER)
	private List<Commodity> commodities;

	public CommoditiesImage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<Commodity> getCommodities() {
		return this.commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	public Commodity addCommodity(Commodity commodity) {
		getCommodities().add(commodity);
		commodity.setCommoditiesImage(this);

		return commodity;
	}

	public Commodity removeCommodity(Commodity commodity) {
		getCommodities().remove(commodity);
		commodity.setCommoditiesImage(null);

		return commodity;
	}

}