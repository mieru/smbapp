package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the notyfications database table.
 * 
 */
@Entity
@Table(name="notyfications")
@NamedQuery(name="Notyfication.findAll", query="SELECT n FROM Notyfication n")
public class Notyfication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="commodities_to_suplement")
	private String commoditiesToSuplement;

	private String content;

	@Column(name="creat_date")
	private Timestamp creatDate;

	@Column(name="end_date")
	private Timestamp endDate;

	@Column(name="notification_number")
	private String notificationNumber;

	private Boolean notified;

	private String status;

	private String subject;

	//bi-directional many-to-one association to NotificationMessage
	@OneToMany(mappedBy="notyfication", fetch=FetchType.EAGER)
	private List<NotificationMessage> notificationMessages;

	//bi-directional many-to-one association to NotificationCategory
	@ManyToOne
	@JoinColumn(name="notification_category_id")
	private NotificationCategory notificationCategory;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Users user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Users user2;

	public Notyfication() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommoditiesToSuplement() {
		return this.commoditiesToSuplement;
	}

	public void setCommoditiesToSuplement(String commoditiesToSuplement) {
		this.commoditiesToSuplement = commoditiesToSuplement;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreatDate() {
		return this.creatDate;
	}

	public void setCreatDate(Timestamp creatDate) {
		this.creatDate = creatDate;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getNotificationNumber() {
		return this.notificationNumber;
	}

	public void setNotificationNumber(String notificationNumber) {
		this.notificationNumber = notificationNumber;
	}

	public Boolean getNotified() {
		return this.notified;
	}

	public void setNotified(Boolean notified) {
		this.notified = notified;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<NotificationMessage> getNotificationMessages() {
		return this.notificationMessages;
	}

	public void setNotificationMessages(List<NotificationMessage> notificationMessages) {
		this.notificationMessages = notificationMessages;
	}

	public NotificationMessage addNotificationMessage(NotificationMessage notificationMessage) {
		getNotificationMessages().add(notificationMessage);
		notificationMessage.setNotyfication(this);

		return notificationMessage;
	}

	public NotificationMessage removeNotificationMessage(NotificationMessage notificationMessage) {
		getNotificationMessages().remove(notificationMessage);
		notificationMessage.setNotyfication(null);

		return notificationMessage;
	}

	public NotificationCategory getNotificationCategory() {
		return this.notificationCategory;
	}

	public void setNotificationCategory(NotificationCategory notificationCategory) {
		this.notificationCategory = notificationCategory;
	}

	public Users getUser1() {
		return this.user1;
	}

	public void setUser1(Users user1) {
		this.user1 = user1;
	}

	public Users getUser2() {
		return this.user2;
	}

	public void setUser2(Users user2) {
		this.user2 = user2;
	}

}