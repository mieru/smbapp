package dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the company_info database table.
 * 
 */
@Entity
@Table(name="company_info")
@NamedQuery(name="CompanyInfo.findAll", query="SELECT c FROM CompanyInfo c")
public class CompanyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="apartament_number")
	private String apartamentNumber;

	@Column(name="building_number")
	private String buildingNumber;

	private String city;

	private String email;

	private String name;

	private String nip;

	private String phone;

	@Column(name="post_code")
	private String postCode;

	private String street;

	public CompanyInfo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApartamentNumber() {
		return this.apartamentNumber;
	}

	public void setApartamentNumber(String apartamentNumber) {
		this.apartamentNumber = apartamentNumber;
	}

	public String getBuildingNumber() {
		return this.buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNip() {
		return this.nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

}