package smbemplsrv.command.restaction.katzgl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KatZglRequestData {
	@XmlElement(name = "id")
	public Integer id;
	@XmlElement(name = "name")
	public String name;
	@XmlElement(name = "default")
	public String def;
}
