package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="Users.findAll", query="SELECT u FROM Users u")
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="apartament_number")
	private String apartamentNumber;

	@Column(name="building_number")
	private String buildingNumber;

	private String city;

	@Column(name="company_name")
	private String companyName;

	private String email;

	private String login;

	private String name;

	private String nip;

	private String password;

	private String phone;

	@Column(name="post_code")
	private String postCode;

	private String role;

	private String state;

	private String street;

	private String surname;

	@Column(name="user_type")
	private String userType;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="user1", fetch=FetchType.EAGER)
	private List<Invoice> invoices1;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="user2", fetch=FetchType.EAGER)
	private List<Invoice> invoices2;

	//bi-directional many-to-one association to NotificationMessage
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<NotificationMessage> notificationMessages;

	//bi-directional many-to-one association to Notyfication
	@OneToMany(mappedBy="user1", fetch=FetchType.EAGER)
	private List<Notyfication> notyfications1;

	//bi-directional many-to-one association to Notyfication
	@OneToMany(mappedBy="user2", fetch=FetchType.EAGER)
	private List<Notyfication> notyfications2;

	//bi-directional many-to-one association to OrderMessage
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<OrderMessage> orderMessages;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="user1", fetch=FetchType.EAGER)
	private List<Order> orders1;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="user2", fetch=FetchType.EAGER)
	private List<Order> orders2;

	//bi-directional many-to-one association to Receipt
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Receipt> receipts;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Transaction> transactions;

	public Users() {
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

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Invoice> getInvoices1() {
		return this.invoices1;
	}

	public void setInvoices1(List<Invoice> invoices1) {
		this.invoices1 = invoices1;
	}

	public Invoice addInvoices1(Invoice invoices1) {
		getInvoices1().add(invoices1);
		invoices1.setUser1(this);

		return invoices1;
	}

	public Invoice removeInvoices1(Invoice invoices1) {
		getInvoices1().remove(invoices1);
		invoices1.setUser1(null);

		return invoices1;
	}

	public List<Invoice> getInvoices2() {
		return this.invoices2;
	}

	public void setInvoices2(List<Invoice> invoices2) {
		this.invoices2 = invoices2;
	}

	public Invoice addInvoices2(Invoice invoices2) {
		getInvoices2().add(invoices2);
		invoices2.setUser2(this);

		return invoices2;
	}

	public Invoice removeInvoices2(Invoice invoices2) {
		getInvoices2().remove(invoices2);
		invoices2.setUser2(null);

		return invoices2;
	}

	public List<NotificationMessage> getNotificationMessages() {
		return this.notificationMessages;
	}

	public void setNotificationMessages(List<NotificationMessage> notificationMessages) {
		this.notificationMessages = notificationMessages;
	}

	public NotificationMessage addNotificationMessage(NotificationMessage notificationMessage) {
		getNotificationMessages().add(notificationMessage);
		notificationMessage.setUser(this);

		return notificationMessage;
	}

	public NotificationMessage removeNotificationMessage(NotificationMessage notificationMessage) {
		getNotificationMessages().remove(notificationMessage);
		notificationMessage.setUser(null);

		return notificationMessage;
	}

	public List<Notyfication> getNotyfications1() {
		return this.notyfications1;
	}

	public void setNotyfications1(List<Notyfication> notyfications1) {
		this.notyfications1 = notyfications1;
	}

	public Notyfication addNotyfications1(Notyfication notyfications1) {
		getNotyfications1().add(notyfications1);
		notyfications1.setUser1(this);

		return notyfications1;
	}

	public Notyfication removeNotyfications1(Notyfication notyfications1) {
		getNotyfications1().remove(notyfications1);
		notyfications1.setUser1(null);

		return notyfications1;
	}

	public List<Notyfication> getNotyfications2() {
		return this.notyfications2;
	}

	public void setNotyfications2(List<Notyfication> notyfications2) {
		this.notyfications2 = notyfications2;
	}

	public Notyfication addNotyfications2(Notyfication notyfications2) {
		getNotyfications2().add(notyfications2);
		notyfications2.setUser2(this);

		return notyfications2;
	}

	public Notyfication removeNotyfications2(Notyfication notyfications2) {
		getNotyfications2().remove(notyfications2);
		notyfications2.setUser2(null);

		return notyfications2;
	}

	public List<OrderMessage> getOrderMessages() {
		return this.orderMessages;
	}

	public void setOrderMessages(List<OrderMessage> orderMessages) {
		this.orderMessages = orderMessages;
	}

	public OrderMessage addOrderMessage(OrderMessage orderMessage) {
		getOrderMessages().add(orderMessage);
		orderMessage.setUser(this);

		return orderMessage;
	}

	public OrderMessage removeOrderMessage(OrderMessage orderMessage) {
		getOrderMessages().remove(orderMessage);
		orderMessage.setUser(null);

		return orderMessage;
	}

	public List<Order> getOrders1() {
		return this.orders1;
	}

	public void setOrders1(List<Order> orders1) {
		this.orders1 = orders1;
	}

	public Order addOrders1(Order orders1) {
		getOrders1().add(orders1);
		orders1.setUser1(this);

		return orders1;
	}

	public Order removeOrders1(Order orders1) {
		getOrders1().remove(orders1);
		orders1.setUser1(null);

		return orders1;
	}

	public List<Order> getOrders2() {
		return this.orders2;
	}

	public void setOrders2(List<Order> orders2) {
		this.orders2 = orders2;
	}

	public Order addOrders2(Order orders2) {
		getOrders2().add(orders2);
		orders2.setUser2(this);

		return orders2;
	}

	public Order removeOrders2(Order orders2) {
		getOrders2().remove(orders2);
		orders2.setUser2(null);

		return orders2;
	}

	public List<Receipt> getReceipts() {
		return this.receipts;
	}

	public void setReceipts(List<Receipt> receipts) {
		this.receipts = receipts;
	}

	public Receipt addReceipt(Receipt receipt) {
		getReceipts().add(receipt);
		receipt.setUser(this);

		return receipt;
	}

	public Receipt removeReceipt(Receipt receipt) {
		getReceipts().remove(receipt);
		receipt.setUser(null);

		return receipt;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setUser(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setUser(null);

		return transaction;
	}

}