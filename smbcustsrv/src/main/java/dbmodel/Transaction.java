package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the transactions database table.
 * 
 */
@Entity
@Table(name="transactions")
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="commodity_list")
	private String commodityList;

	private double sum;

	@Column(name="tranzaction_date")
	private Timestamp tranzactionDate;

	private String type;

	//bi-directional many-to-one association to Invoice
	@ManyToOne
	private Invoice invoice;

	//bi-directional many-to-one association to Receipt
	@ManyToOne
	private Receipt receipt;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Users user;

	public Transaction() {
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

	public double getSum() {
		return this.sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public Timestamp getTranzactionDate() {
		return this.tranzactionDate;
	}

	public void setTranzactionDate(Timestamp tranzactionDate) {
		this.tranzactionDate = tranzactionDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Receipt getReceipt() {
		return this.receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}