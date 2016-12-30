package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the notification_messages database table.
 * 
 */
@Entity
@Table(name="notification_messages")
@NamedQuery(name="NotificationMessage.findAll", query="SELECT n FROM NotificationMessage n")
public class NotificationMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="add_date")
	private Timestamp addDate;

	private String content;

	private String type;

	//bi-directional many-to-one association to Notyfication
	@ManyToOne
	@JoinColumn(name="notification_id")
	private Notyfication notyfication;

	//bi-directional many-to-one association to User
	@ManyToOne
	private Users user;

	public NotificationMessage() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getAddDate() {
		return this.addDate;
	}

	public void setAddDate(Timestamp addDate) {
		this.addDate = addDate;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Notyfication getNotyfication() {
		return this.notyfication;
	}

	public void setNotyfication(Notyfication notyfication) {
		this.notyfication = notyfication;
	}

	public Users getUser() {
		return this.user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}