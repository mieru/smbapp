package restapi.users;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsersRestData {
	@XmlElement(name = "id")
	public Integer idUzytkownika;
	@XmlElement(name = "type")
	public String type;
	@XmlElement(name="id_uzytkownika")
    public Integer idUser;
    @XmlElement(name="password")
    public String password;
    @XmlElement(name="email")
    public String email;
    @XmlElement(name="phone")
    public String phone;
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="surname")
    public String surname;
    @XmlElement(name="street")
    public String street;
    @XmlElement(name="number_house")
    public String buildingNumber;
    @XmlElement(name="number_flat")
    public String flatNumber;
    @XmlElement(name="city")
    public String city;
    @XmlElement(name="post_code")
    public String postCode;
    @XmlElement(name="customer_type")
    public String customerType;
    @XmlElement(name="nip")
    public String nip;
    @XmlElement(name="company_name")
    public String companyName;
    @XmlElement(name="code")
    public String idEncoded;
    @XmlElement(name="activationUri")
    public String activationUri;
    @XmlElement(name="upr")
    public String upr;
    @XmlElement(name="uprKod")
    public String uprKod;
    @XmlElement(name="rola")
    public String rola;
    @XmlElement(name="login")
    public String login;
    @XmlElement(name="status")
    public String status;
}
