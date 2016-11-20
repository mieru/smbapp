package mailer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dbmodel.Uzytkownik;
import utils.SmbUtil;

public class MailSender { // DOROBIC KONFIGURACJE Z GUI
	private Properties mailServerProperties = null;
	private Session getMailSession = null;
	private MimeMessage generateMailMessage = null;

	public void sendMail(String subject, String body, String email) throws AddressException, MessagingException {
		System.out.println("SendMail START");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587"); // standardowy port smtp 25
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		generateMailMessage.setSubject(subject);
		generateMailMessage.setContent(body, "text/html");

		Transport transport = getMailSession.getTransport("smtp");

		transport.connect("smtp.gmail.com", "smbpracinz@gmail.com", "SMBpracinz2016");
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