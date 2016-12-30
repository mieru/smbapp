package dbmodel;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the warehouse database table.
 * 
 */
@Entity
@NamedQuery(name="Warehouse.findAll", query="SELECT w FROM Warehouse w")
public class Warehouse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	//bi-directional many-to-one association to Commodity
	@OneToMany(mappedBy="warehouse", fetch=FetchType.EAGER)
	private List<Commodity> commodities;

	public Warehouse() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		commodity.setWarehouse(this);

		return commodity;
	}

	public Commodity removeCommodity(Commodity commodity) {
		getCommodities().remove(commodity);
		commodity.setWarehouse(null);

		return commodity;
	}

}