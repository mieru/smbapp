package smbemplsrv.dbmodel;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the producent database table.
 * 
 */
@Entity
@NamedQuery(name="Producent.findAll", query="SELECT p FROM Producent p")
public class Producent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producena")
	private Integer idProducena;

	private String nazwa;

	private String opis;

	public Producent() {
	}

	public Integer getIdProducena() {
		return this.idProducena;
	}

	public void setIdProducena(Integer idProducena) {
		this.idProducena = idProducena;
	}

	public String getNazwa() {
		return this.nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

}