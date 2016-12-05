package dbmodel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the uzytkownik database table.
 * 
 */
@Entity
@NamedQuery(name="Uzytkownik.findAll", query="SELECT u FROM Uzytkownik u")
public class Uzytkownik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user")
	private Integer idUser;

	@Column(name="building_number")
	private String buildingNumber;

	private String city;

	@Column(name="company_name")
	private String companyName;

	@Column(name="customer_type")
	private String customerType;

	private String login;

	private String mail;

	private String name;

	private String nip;

	private String password;

	private String phone;

	@Column(name="pos_code")
	private String posCode;

	private String role;

	@Column(name="room_number")
	private String roomNumber;

	private String state;

	private String street;

	private String surname;

	//bi-directional many-to-one association to FakturaSprzedazy
	@OneToMany(mappedBy="uzytkownik1", fetch=FetchType.EAGER)
	private List<FakturaSprzedazy> fakturaSprzedazies1;

	//bi-directional many-to-one association to FakturaSprzedazy
	@OneToMany(mappedBy="uzytkownik2", fetch=FetchType.EAGER)
	private List<FakturaSprzedazy> fakturaSprzedazies2;

	//bi-directional many-to-one association to Paragon
	@OneToMany(mappedBy="uzytkownik", fetch=FetchType.EAGER)
	private List<Paragon> paragons;

	//bi-directional many-to-one association to WiadomoscZamSprzedaz
	@OneToMany(mappedBy="uzytkownik", fetch=FetchType.EAGER)
	private List<WiadomoscZamSprzedaz> wiadomoscZamSprzedazs;

	//bi-directional many-to-one association to ZamowienieSprzedaz
	@OneToMany(mappedBy="uzytkownik1", fetch=FetchType.EAGER)
	private List<ZamowienieSprzedaz> zamowienieSprzedazs1;

	//bi-directional many-to-one association to ZamowienieSprzedaz
	@OneToMany(mappedBy="uzytkownik2", fetch=FetchType.EAGER)
	private List<ZamowienieSprzedaz> zamowienieSprzedazs2;

	//bi-directional many-to-one association to Zgloszenie
	@OneToMany(mappedBy="uzytkownik1", fetch=FetchType.EAGER)
	private List<Zgloszenie> zgloszenies1;

	//bi-directional many-to-one association to Zgloszenie
	@OneToMany(mappedBy="uzytkownik2", fetch=FetchType.EAGER)
	private List<Zgloszenie> zgloszenies2;

	//bi-directional many-to-one association to ZgloszenieKomentarz
	@OneToMany(mappedBy="uzytkownik", fetch=FetchType.EAGER)
	private List<ZgloszenieKomentarz> zgloszenieKomentarzs;

	public Uzytkownik() {
	}

	public Integer getIdUser() {
		return this.idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getPosCode() {
		return this.posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoomNumber() {
		return this.roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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

	public List<FakturaSprzedazy> getFakturaSprzedazies1() {
		return this.fakturaSprzedazies1;
	}

	public void setFakturaSprzedazies1(List<FakturaSprzedazy> fakturaSprzedazies1) {
		this.fakturaSprzedazies1 = fakturaSprzedazies1;
	}

	public FakturaSprzedazy addFakturaSprzedazies1(FakturaSprzedazy fakturaSprzedazies1) {
		getFakturaSprzedazies1().add(fakturaSprzedazies1);
		fakturaSprzedazies1.setUzytkownik1(this);

		return fakturaSprzedazies1;
	}

	public FakturaSprzedazy removeFakturaSprzedazies1(FakturaSprzedazy fakturaSprzedazies1) {
		getFakturaSprzedazies1().remove(fakturaSprzedazies1);
		fakturaSprzedazies1.setUzytkownik1(null);

		return fakturaSprzedazies1;
	}

	public List<FakturaSprzedazy> getFakturaSprzedazies2() {
		return this.fakturaSprzedazies2;
	}

	public void setFakturaSprzedazies2(List<FakturaSprzedazy> fakturaSprzedazies2) {
		this.fakturaSprzedazies2 = fakturaSprzedazies2;
	}

	public FakturaSprzedazy addFakturaSprzedazies2(FakturaSprzedazy fakturaSprzedazies2) {
		getFakturaSprzedazies2().add(fakturaSprzedazies2);
		fakturaSprzedazies2.setUzytkownik2(this);

		return fakturaSprzedazies2;
	}

	public FakturaSprzedazy removeFakturaSprzedazies2(FakturaSprzedazy fakturaSprzedazies2) {
		getFakturaSprzedazies2().remove(fakturaSprzedazies2);
		fakturaSprzedazies2.setUzytkownik2(null);

		return fakturaSprzedazies2;
	}

	public List<Paragon> getParagons() {
		return this.paragons;
	}

	public void setParagons(List<Paragon> paragons) {
		this.paragons = paragons;
	}

	public Paragon addParagon(Paragon paragon) {
		getParagons().add(paragon);
		paragon.setUzytkownik(this);

		return paragon;
	}

	public Paragon removeParagon(Paragon paragon) {
		getParagons().remove(paragon);
		paragon.setUzytkownik(null);

		return paragon;
	}

	public List<WiadomoscZamSprzedaz> getWiadomoscZamSprzedazs() {
		return this.wiadomoscZamSprzedazs;
	}

	public void setWiadomoscZamSprzedazs(List<WiadomoscZamSprzedaz> wiadomoscZamSprzedazs) {
		this.wiadomoscZamSprzedazs = wiadomoscZamSprzedazs;
	}

	public WiadomoscZamSprzedaz addWiadomoscZamSprzedaz(WiadomoscZamSprzedaz wiadomoscZamSprzedaz) {
		getWiadomoscZamSprzedazs().add(wiadomoscZamSprzedaz);
		wiadomoscZamSprzedaz.setUzytkownik(this);

		return wiadomoscZamSprzedaz;
	}

	public WiadomoscZamSprzedaz removeWiadomoscZamSprzedaz(WiadomoscZamSprzedaz wiadomoscZamSprzedaz) {
		getWiadomoscZamSprzedazs().remove(wiadomoscZamSprzedaz);
		wiadomoscZamSprzedaz.setUzytkownik(null);

		return wiadomoscZamSprzedaz;
	}

	public List<ZamowienieSprzedaz> getZamowienieSprzedazs1() {
		return this.zamowienieSprzedazs1;
	}

	public void setZamowienieSprzedazs1(List<ZamowienieSprzedaz> zamowienieSprzedazs1) {
		this.zamowienieSprzedazs1 = zamowienieSprzedazs1;
	}

	public ZamowienieSprzedaz addZamowienieSprzedazs1(ZamowienieSprzedaz zamowienieSprzedazs1) {
		getZamowienieSprzedazs1().add(zamowienieSprzedazs1);
		zamowienieSprzedazs1.setUzytkownik1(this);

		return zamowienieSprzedazs1;
	}

	public ZamowienieSprzedaz removeZamowienieSprzedazs1(ZamowienieSprzedaz zamowienieSprzedazs1) {
		getZamowienieSprzedazs1().remove(zamowienieSprzedazs1);
		zamowienieSprzedazs1.setUzytkownik1(null);

		return zamowienieSprzedazs1;
	}

	public List<ZamowienieSprzedaz> getZamowienieSprzedazs2() {
		return this.zamowienieSprzedazs2;
	}

	public void setZamowienieSprzedazs2(List<ZamowienieSprzedaz> zamowienieSprzedazs2) {
		this.zamowienieSprzedazs2 = zamowienieSprzedazs2;
	}

	public ZamowienieSprzedaz addZamowienieSprzedazs2(ZamowienieSprzedaz zamowienieSprzedazs2) {
		getZamowienieSprzedazs2().add(zamowienieSprzedazs2);
		zamowienieSprzedazs2.setUzytkownik2(this);

		return zamowienieSprzedazs2;
	}

	public ZamowienieSprzedaz removeZamowienieSprzedazs2(ZamowienieSprzedaz zamowienieSprzedazs2) {
		getZamowienieSprzedazs2().remove(zamowienieSprzedazs2);
		zamowienieSprzedazs2.setUzytkownik2(null);

		return zamowienieSprzedazs2;
	}

	public List<Zgloszenie> getZgloszenies1() {
		return this.zgloszenies1;
	}

	public void setZgloszenies1(List<Zgloszenie> zgloszenies1) {
		this.zgloszenies1 = zgloszenies1;
	}

	public Zgloszenie addZgloszenies1(Zgloszenie zgloszenies1) {
		getZgloszenies1().add(zgloszenies1);
		zgloszenies1.setUzytkownik1(this);

		return zgloszenies1;
	}

	public Zgloszenie removeZgloszenies1(Zgloszenie zgloszenies1) {
		getZgloszenies1().remove(zgloszenies1);
		zgloszenies1.setUzytkownik1(null);

		return zgloszenies1;
	}

	public List<Zgloszenie> getZgloszenies2() {
		return this.zgloszenies2;
	}

	public void setZgloszenies2(List<Zgloszenie> zgloszenies2) {
		this.zgloszenies2 = zgloszenies2;
	}

	public Zgloszenie addZgloszenies2(Zgloszenie zgloszenies2) {
		getZgloszenies2().add(zgloszenies2);
		zgloszenies2.setUzytkownik2(this);

		return zgloszenies2;
	}

	public Zgloszenie removeZgloszenies2(Zgloszenie zgloszenies2) {
		getZgloszenies2().remove(zgloszenies2);
		zgloszenies2.setUzytkownik2(null);

		return zgloszenies2;
	}

	public List<ZgloszenieKomentarz> getZgloszenieKomentarzs() {
		return this.zgloszenieKomentarzs;
	}

	public void setZgloszenieKomentarzs(List<ZgloszenieKomentarz> zgloszenieKomentarzs) {
		this.zgloszenieKomentarzs = zgloszenieKomentarzs;
	}

	public ZgloszenieKomentarz addZgloszenieKomentarz(ZgloszenieKomentarz zgloszenieKomentarz) {
		getZgloszenieKomentarzs().add(zgloszenieKomentarz);
		zgloszenieKomentarz.setUzytkownik(this);

		return zgloszenieKomentarz;
	}

	public ZgloszenieKomentarz removeZgloszenieKomentarz(ZgloszenieKomentarz zgloszenieKomentarz) {
		getZgloszenieKomentarzs().remove(zgloszenieKomentarz);
		zgloszenieKomentarz.setUzytkownik(null);

		return zgloszenieKomentarz;
	}

}