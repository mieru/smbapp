package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the mail_konf database table.
 * 
 */
@Entity
@Table(name="mail_konf")
@NamedQuery(name="MailKonf.findAll", query="SELECT m FROM MailKonf m")
public class MailKonf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_mail_konf")
	private Integer idMailKonf;

	private String host;

	private String mail;

	private String password;

	private Integer port;

	public MailKonf() {
	}

	public Integer getIdMailKonf() {
		return this.idMailKonf;
	}

	public void setIdMailKonf(Integer idMailKonf) {
		this.idMailKonf = idMailKonf;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

}