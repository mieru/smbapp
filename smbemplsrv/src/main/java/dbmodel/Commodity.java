package dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the commodities database table.
 * 
 */
@Entity
@Table(name="commodities")
@NamedQuery(name="Commodity.findAll", query="SELECT c FROM Commodity c")
public class Commodity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer is;

	@Column(name="amount_in_warehouse")
	private Integer amountInWarehouse;

	private String description;

	@Column(name="is_user_min_amount")
	private Boolean isUserMinAmount;

	@Column(name="min_amount")
	private Integer minAmount;

	private String name;

	@Column(name="net_price")
	private double netPrice;

	private Boolean notified;

	@Column(name="sys_min_amoint")
	private Integer sysMinAmoint;

	@Column(name="tax_rate")
	private Integer taxRate;

	private String unit;

	@Column(name="user_min_amount")
	private Integer userMinAmount;

	//bi-directional many-to-one association to CommoditiesImage
	@ManyToOne
	@JoinColumn(name="commodity_image_id")
	private CommoditiesImage commoditiesImage;

	//bi-directional many-to-one association to CommodityCategory
	@ManyToOne
	@JoinColumn(name="commodity_category_id")
	private CommodityCategory commodityCategory;

	//bi-directional many-to-one association to Warehouse
	@ManyToOne
	private Warehouse warehouse;

	public Commodity() {
	}

	public Integer getIs() {
		return this.is;
	}

	public void setIs(Integer is) {
		this.is = is;
	}

	public Integer getAmountInWarehouse() {
		return this.amountInWarehouse;
	}

	public void setAmountInWarehouse(Integer amountInWarehouse) {
		this.amountInWarehouse = amountInWarehouse;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsUserMinAmount() {
		return this.isUserMinAmount;
	}

	public void setIsUserMinAmount(Boolean isUserMinAmount) {
		this.isUserMinAmount = isUserMinAmount;
	}

	public Integer getMinAmount() {
		return this.minAmount;
	}

	public void setMinAmount(Integer minAmount) {
		this.minAmount = minAmount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getNetPrice() {
		return this.netPrice;
	}

	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public Boolean getNotified() {
		return this.notified;
	}

	public void setNotified(Boolean notified) {
		this.notified = notified;
	}

	public Integer getSysMinAmoint() {
		return this.sysMinAmoint;
	}

	public void setSysMinAmoint(Integer sysMinAmoint) {
		this.sysMinAmoint = sysMinAmoint;
	}

	public Integer getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(Integer taxRate) {
		this.taxRate = taxRate;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getUserMinAmount() {
		return this.userMinAmount;
	}

	public void setUserMinAmount(Integer userMinAmount) {
		this.userMinAmount = userMinAmount;
	}

	public CommoditiesImage getCommoditiesImage() {
		return this.commoditiesImage;
	}

	public void setCommoditiesImage(CommoditiesImage commoditiesImage) {
		this.commoditiesImage = commoditiesImage;
	}

	public CommodityCategory getCommodityCategory() {
		return this.commodityCategory;
	}

	public void setCommodityCategory(CommodityCategory commodityCategory) {
		this.commodityCategory = commodityCategory;
	}

	public Warehouse getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}