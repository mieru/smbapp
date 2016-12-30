package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the invoices database table.
 * 
 */
@Entity
@Table(name="invoices")
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="commodity_list")
	private String commodityList;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Column(name="customer_data")
	private String customerData;

	@Column(name="file_path")
	private String filePath;

	@Column(name="invoice_number")
	private String invoiceNumber;

	@Column(name="issuing_entity")
	private String issuingEntity;

	private String status;

	//bi-directional many-to-one association to Order
	@ManyToOne
	private Order order;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Users user1;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Users user2;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="invoice", fetch=FetchType.EAGER)
	private List<Transaction> transactions;

	public Invoice() {
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

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getCustomerData() {
		return this.customerData;
	}

	public void setCustomerData(String customerData) {
		this.customerData = customerData;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getIssuingEntity() {
		return this.issuingEntity;
	}

	public void setIssuingEntity(String issuingEntity) {
		this.issuingEntity = issuingEntity;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setInvoice(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setInvoice(null);

		return transaction;
	}

}