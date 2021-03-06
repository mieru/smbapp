package restapi.companyinfo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CompanyInfoRestData{
    @XmlElement(name="name")
    public String name;
    @XmlElement(name="nip")
    public String nip;
    @XmlElement(name="regon")
    public String regon;
    @XmlElement(name="krs")
    public String krs;
    @XmlElement(name="mail")
    public String mail;
    @XmlElement(name="telefon")
    public String telefon;
    @XmlElement(name="ulica")
    public String ulica;  
    @XmlElement(name="nr_bud")
    public String nrBud;
    @XmlElement(name="nr_lok")
    public String nrLok;
    @XmlElement(name="kod_pocz")
    public String kodPocztowy;
    @XmlElement(name="miasto")
    public String miasto;
    @XmlElement(name="id")
    public String id;
    
    
  
}

