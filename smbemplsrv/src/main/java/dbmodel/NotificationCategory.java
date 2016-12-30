package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the notification_categories database table.
 * 
 */
@Entity
@Table(name="notification_categories")
@NamedQuery(name="NotificationCategory.findAll", query="SELECT n FROM NotificationCategory n")
public class NotificationCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="can_customer_see")
	private Boolean canCustomerSee;

	@Column(name="is_warehouse")
	private Boolean isWarehouse;

	private String name;

	//bi-directional many-to-one association to Notyfication
	@OneToMany(mappedBy="notificationCategory", fetch=FetchType.EAGER)
	private List<Notyfication> notyfications;

	public NotificationCategory() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getCanCustomerSee() {
		return this.canCustomerSee;
	}

	public void setCanCustomerSee(Boolean canCustomerSee) {
		this.canCustomerSee = canCustomerSee;
	}

	public Boolean getIsWarehouse() {
		return this.isWarehouse;
	}

	public void setIsWarehouse(Boolean isWarehouse) {
		this.isWarehouse = isWarehouse;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Notyfication> getNotyfications() {
		return this.notyfications;
	}

	public void setNotyfications(List<Notyfication> notyfications) {
		this.notyfications = notyfications;
	}

	public Notyfication addNotyfication(Notyfication notyfication) {
		getNotyfications().add(notyfication);
		notyfication.setNotificationCategory(this);

		return notyfication;
	}

	public Notyfication removeNotyfication(Notyfication notyfication) {
		getNotyfications().remove(notyfication);
		notyfication.setNotificationCategory(null);

		return notyfication;
	}

}