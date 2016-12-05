package smbemplsrv.command.restaction.produkt;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProduktRequestData {
	@XmlElement(name = "id")
	public Integer id;
	@XmlElement(name = "name")
	public String name;
	@XmlElement(name = "default")
	public String def;
	@XmlElement(name = "id_magazynu")
	public Integer idMagazynu;
	@XmlElement(name = "image")
	public String image;
	@XmlElement(name = "opis")
	public String opis;
	@XmlElement(name = "cnetto") 
	public Double cnetto;
	@XmlElement(name = "stawka_vat")
	public Integer stawkaVat;
	@XmlElement(name = "jednostka")
	public String jednostka;
	@XmlElement(name = "cbrutto")
	public String cbrutto;
	@XmlElement(name = "ilosc")
	public Integer ilosc;
	@XmlElement(name = "stan_min")
	public Integer stan_min;
	@XmlElement(name = "stan_min_uzyt")
	public Integer stan_min_uzyt;
	@XmlElement(name = "stan_min_sys")
	public Integer stan_min_sys;
	@XmlElement(name = "kat")
	public Integer kat;
	@XmlElement(name = "fontColor")
	public String fontColor;
	@XmlElement(name = "czyStanUzyt")
	public Boolean czyStanUzyt;
	@XmlElement(name = "czyStanSys")
	public Boolean czyStanSys;
	
	
	
}
