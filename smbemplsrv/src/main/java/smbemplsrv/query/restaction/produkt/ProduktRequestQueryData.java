package smbemplsrv.query.restaction.produkt;

import javax.xml.bind.annotation.XmlElement;

public class ProduktRequestQueryData {
	 	@XmlElement(name="id_magazynu")
	    public Integer idMagazynu;
		@XmlElement(name="id")
	    public Integer idProduktu;
}
