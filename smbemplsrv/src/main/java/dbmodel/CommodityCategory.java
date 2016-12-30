package dbmodel;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the commodity_category database table.
 * 
 */
@Entity
@Table(name="commodity_category")
@NamedQuery(name="CommodityCategory.findAll", query="SELECT c FROM CommodityCategory c")
public class CommodityCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to Commodity
	@OneToMany(mappedBy="commodityCategory", fetch=FetchType.EAGER)
	private List<Commodity> commodities;

	public CommodityCategory() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Commodity> getCommodities() {
		return this.commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

	public Commodity addCommodity(Commodity commodity) {
		getCommodities().add(commodity);
		commodity.setCommodityCategory(this);

		return commodity;
	}

	public Commodity removeCommodity(Commodity commodity) {
		getCommodities().remove(commodity);
		commodity.setCommodityCategory(null);

		return commodity;
	}

}