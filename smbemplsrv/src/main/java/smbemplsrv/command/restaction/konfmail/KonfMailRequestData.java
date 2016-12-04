package smbemplsrv.command.restaction.konfmail;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KonfMailRequestData{
    @XmlElement(name="mail")
    public String mail;
    @XmlElement(name="password")
    public String password;
    @XmlElement(name="host")
    public String host;
    @XmlElement(name="port")
    public String port;
}

