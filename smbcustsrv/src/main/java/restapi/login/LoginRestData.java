package restapi.login;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginRestData{
    @XmlElement(name="username")
    public String username;
    @XmlElement(name="password")
    public String password;
}
