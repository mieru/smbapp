package smbemplsrv.command.restaction.magazyn;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MagazynRequestData {
	@XmlElement(name = "id")
	public Integer id;
	@XmlElement(name = "name")
	public String name;
	@XmlElement(name = "default")
	public String def;
}
