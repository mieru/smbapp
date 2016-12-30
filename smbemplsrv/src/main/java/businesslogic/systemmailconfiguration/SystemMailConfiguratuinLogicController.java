package businesslogic.systemmailconfiguration;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONObject;

import dbmenager.mailconfiguration.SystemMailCongifurationDAO;
import dbmodel.SysEmailConfiguration;
import restapi.sysconfmail.SysMailConfRestData;

@Stateless
@LocalBean
public class SystemMailConfiguratuinLogicController {

	@EJB
	SystemMailCongifurationDAO systemMailCongifurationDAO;
	
	
	public String getSysMailConfiguration(){
		JSONObject jsonObject = new JSONObject();
		SysEmailConfiguration sysEmailConfiguration = null;
		try {
			sysEmailConfiguration = systemMailCongifurationDAO.findAll().iterator().next();
			jsonObject.put("mail", sysEmailConfiguration.getEmail());
			jsonObject.put("password", sysEmailConfiguration.getPassword());
			jsonObject.put("host", sysEmailConfiguration.getHost());
			jsonObject.put("port", sysEmailConfiguration.getPort());
		} catch (Exception e) {}
		return jsonObject.toString();
		
		
	}


	public String saveMailConfiguration(SysMailConfRestData sysMailConfRestData) {
		SysEmailConfiguration sysEmailConfiguration = new SysEmailConfiguration();
		try {
			sysEmailConfiguration = systemMailCongifurationDAO.findAll().iterator().next();
			sysEmailConfiguration.setEmail(sysMailConfRestData.mail);
			sysEmailConfiguration.setPassword(sysMailConfRestData.password);
			sysEmailConfiguration.setHost(sysMailConfRestData.host);
			sysEmailConfiguration.setPort(Integer.parseInt(sysMailConfRestData.port));
			systemMailCongifurationDAO.update(sysEmailConfiguration);
	}catch (Exception e) {
		sysEmailConfiguration.setEmail(sysMailConfRestData.mail);
		sysEmailConfiguration.setPassword(sysMailConfRestData.password);
		sysEmailConfiguration.setHost(sysMailConfRestData.host);
		sysEmailConfiguration.setPort(Integer.parseInt(sysMailConfRestData.port));
		systemMailCongifurationDAO.insert(sysEmailConfiguration);
	}	
	return "";
}
}
