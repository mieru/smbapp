package command.restaction.register;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterRequestData{
    @XmlElement(name="username")
    public String username;
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
    @XmlElement(name="building_number")
    public String buildingNumber;
    @XmlElement(name="flat_number")
    public String flatNumber;
    @XmlElement(name="city")
    public String city;
    @XmlElement(name="post_code")
    public String postCode;
    @XmlElement(name="customer_type")
    public String customerType;
    @XmlElement(name="nip")
    public String nip;
    @XmlElement(name="companyName")
    public String companyName;
    @XmlElement(name="code")
    public String idEncoded;
    @XmlElement(name="activationUri")
    public String url;
}

