package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the receipts database table.
 * 
 */
@Entity
@Table(name="receipts")
@NamedQuery(name="Receipt.findAll", query="SELECT r FROM Receipt r")
public class Receipt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="commodity_list")
	private String commodityList;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Column(name="issuing_entity")
	private String issuingEntity;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Users user;

	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="receipt", fetch=FetchType.EAGER)
	private List<Transaction> transactions;

	public Receipt() {
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

	public String getIssuingEntity() {
		return this.issuingEntity;
	}

	public void setIssuingEntity(String issuingEntity) {
		this.issuingEntity = issuingEntity;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setReceipt(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setReceipt(null);

		return transaction;
	}

}