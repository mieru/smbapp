package restapi.notyficationcategory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NotyficationCategoryRestData {
	@XmlElement(name = "id")
	public Integer id;
	@XmlElement(name = "name")
	public String name;
	@XmlElement(name = "default")
	public String def;
	@XmlElement(name = "czyKlient")
	public Boolean czyKlient;
	@XmlElement(name = "czyMagazyn")
	public Boolean czyMagazyn;
}
