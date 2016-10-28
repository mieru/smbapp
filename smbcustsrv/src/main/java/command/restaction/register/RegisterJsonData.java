package command.restaction.register;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterJsonData{
    @XmlElement(name="username")
    public String username;
    @XmlElement(name="password")
    public String password;
    @XmlElement(name="email")
    public String email;
    @XmlElement(name="code")
    public String idEncoded;
    @XmlElement(name="activationUri")
    public String activationUri;
    
}
