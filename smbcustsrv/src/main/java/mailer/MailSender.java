package mailer;

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

import dbmodel.MailKonf;
import dbmodel.Uzytkownik;
import query.ejbcontrol.konfmail.KonfMailEjbQueryController;
import utils.SmbUtil;

@Stateless
@LocalBean
public class MailSender { 
	private Properties mailServerProperties = null;
	private Session getMailSession = null;
	private MimeMessage generateMailMessage = null;

	@EJB
	KonfMailEjbQueryController konfMailEjbQueryController;
	
	
	public void sendMail(String subject, String body, String email) throws AddressException, MessagingException {
		MailKonf mailKonf = konfMailEjbQueryController.findAll().iterator().next();
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

		transport.connect(mailKonf.getHost(), mailKonf.getMail(), mailKonf.getPassword());
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
		System.out.println("SendMail STOP");
	}
	
	public void sendActivationMail(Uzytkownik uzytkownik, String activationUri) throws AddressException, MessagingException{
		String encodedUserId = SmbUtil.encodeInteger(uzytkownik.getIdUser());
		String activationLink = activationUri.substring(0, activationUri.indexOf("rejestracja")) + "activationAccount?code=" + encodedUserId;

		String subject = "Link aktywacyjny";
		sendMail(subject, activationLink, uzytkownik.getMail());
	}
	
}