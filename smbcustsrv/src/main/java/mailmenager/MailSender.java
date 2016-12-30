package mailmenager;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dbmenager.mailconfiguration.SystemMailCongifurationDAO;
import dbmodel.SysEmailConfiguration;
import dbmodel.Users;
import utils.SmallSmbUtils;

@Stateless
@LocalBean
public class MailSender { 
	private Properties mailServerProperties = null;
	private Session getMailSession = null;
	private MimeMessage generateMailMessage = null;

	@EJB
	SystemMailCongifurationDAO konfMailEjbQueryController;
	
	
	public void sendMail(String subject, String body, String email) throws AddressException, MessagingException {
		SysEmailConfiguration  mailKonf = konfMailEjbQueryController.findAll().iterator().next();
		System.out.println("SendMail START");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", mailKonf.getPort()); 
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		generateMailMessage.setSubject(subject);
		generateMailMessage.setContent(body, "text/html");

		Transport transport = getMailSession.getTransport("smtp");

		transport.connect(mailKonf.getHost(), mailKonf.getEmail(), mailKonf.getPassword());
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
		System.out.println("SendMail STOP");
	}
	
	public void sendActivationMail(Users user, String activationUri) throws AddressException, MessagingException{
		String encodedUserId = SmallSmbUtils.encodeInteger(user.getId());
		String activationLink = activationUri.substring(0, activationUri.indexOf("rejestracja")) + "activationAccount?code=" + encodedUserId;

		String subject = "Link aktywacyjny";
		sendMail(subject, activationLink, user.getEmail());
	}
	
}