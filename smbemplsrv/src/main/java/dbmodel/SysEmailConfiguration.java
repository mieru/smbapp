package dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_email_configuration database table.
 * 
 */
@Entity
@Table(name="sys_email_configuration")
@NamedQuery(name="SysEmailConfiguration.findAll", query="SELECT s FROM SysEmailConfiguration s")
public class SysEmailConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;

	private String host;

	private String password;

	private Integer port;

	public SysEmailConfiguration() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
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