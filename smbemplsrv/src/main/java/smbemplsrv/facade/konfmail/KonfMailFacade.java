package smbemplsrv.facade.konfmail;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.json.JSONObject;

import smbemplsrv.command.ejbcontrol.konfmail.KonfMailEjbCommandController;
import smbemplsrv.command.restaction.konfmail.KonfMailRequestData;
import smbemplsrv.dbmodel.MailKonf;
import smbemplsrv.query.ejbcontrol.konfmail.KonfMailEjbQueryController;

@Stateless
@LocalBean
public class KonfMailFacade {

	@EJB
	KonfMailEjbCommandController konfMailEjbCommandController;
	
	@EJB
	KonfMailEjbQueryController konfMailEjbQueryController;
	
	
	public String getKonfMail(){
		JSONObject jsonObject = new JSONObject();
		MailKonf konfMail = null;
		try {
			konfMail = konfMailEjbQueryController.findAll().iterator().next();
			jsonObject.put("mail", konfMail.getMail());
			jsonObject.put("password", konfMail.getPassword());
			jsonObject.put("host", konfMail.getHost());
			jsonObject.put("port", konfMail.getPort());
		} catch (Exception e) {}
		return jsonObject.toString();
		
		
	}


	public String saveMailKonfiguration(KonfMailRequestData konfMailRequestData) {
		MailKonf konfMail = new MailKonf();
		try {
			konfMail = konfMailEjbQueryController.findAll().iterator().next();
			konfMail.setMail(konfMailRequestData.mail);
			konfMail.setPassword(konfMailRequestData.password);
			konfMail.setHost(konfMailRequestData.host);
			konfMail.setPort(Integer.parseInt(konfMailRequestData.port));
			konfMailEjbCommandController.update(konfMail);
	}catch (Exception e) {
		konfMail.setMail(konfMailRequestData.mail);
		konfMail.setPassword(konfMailRequestData.password);
		konfMail.setHost(konfMailRequestData.host);
		konfMail.setPort(Integer.parseInt(konfMailRequestData.port));
		konfMailEjbCommandController.insert(konfMail);
	}	
	return "";
}
}
