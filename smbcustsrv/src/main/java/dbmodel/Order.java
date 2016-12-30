package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
@Table(name="orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="commodity_list")
	private String commodityList;

	@Column(name="created_data")
	private Timestamp createdData;

	@Column(name="data_to_invoice")
	private String dataToInvoice;

	@Column(name="delivery_address")
	private String deliveryAddress;

	@Column(name="end_date")
	private Timestamp endDate;

	@Column(name="is_invoice")
	private Boolean isInvoice;

	@Column(name="order_number")
	private String orderNumber;

	private String status;

	//bi-directional many-to-one association to Invoice
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER)
	private List<Invoice> invoices;

	//bi-directional many-to-one association to OrderMessage
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER)
	private List<OrderMessage> orderMessages;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
	private Users user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Users user2;

	public Order() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommodityList() {
		return this.commodityList;
	}

	public void setCommodityList(String commodityList) {
		this.commodityList = commodityList;
	}

	public Timestamp getCreatedData() {
		return this.createdData;
	}

	public void setCreatedData(Timestamp createdData) {
		this.createdData = createdData;
	}

	public String getDataToInvoice() {
		return this.dataToInvoice;
	}

	public void setDataToInvoice(String dataToInvoice) {
		this.dataToInvoice = dataToInvoice;
	}

	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsInvoice() {
		return this.isInvoice;
	}

	public void setIsInvoice(Boolean isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Invoice> getInvoices() {
		return this.invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Invoice addInvoice(Invoice invoice) {
		getInvoices().add(invoice);
		invoice.setOrder(this);

		return invoice;
	}

	public Invoice removeInvoice(Invoice invoice) {
		getInvoices().remove(invoice);
		invoice.setOrder(null);

		return invoice;
	}

	public List<OrderMessage> getOrderMessages() {
		return this.orderMessages;
	}

	public void setOrderMessages(List<OrderMessage> orderMessages) {
		this.orderMessages = orderMessages;
	}

	public OrderMessage addOrderMessage(OrderMessage orderMessage) {
		getOrderMessages().add(orderMessage);
		orderMessage.setOrder(this);

		return orderMessage;
	}

	public OrderMessage removeOrderMessage(OrderMessage orderMessage) {
		getOrderMessages().remove(orderMessage);
		orderMessage.setOrder(null);

		return orderMessage;
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