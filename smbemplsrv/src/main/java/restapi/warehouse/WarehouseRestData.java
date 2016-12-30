package restapi.warehouse;

import javax.xml.bind.annotation.XmlElement;

public class WarehouseRestData {
	 	@XmlElement(name="id_magazynu")
	    public Integer idMagazynu;
	 	@XmlElement(name = "id")
		public Integer id;
		@XmlElement(name = "name")
		public String name;
		@XmlElement(name = "default")
		public String def;
}
