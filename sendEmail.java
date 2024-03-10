import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailException;

	public class sendEmail {
	    public static void send(String toEmail, String subject, String body) throws EmailException, AddressException, MessagingException {
	        // Replace with your email address and password
	        final String fromEmail = "libraryAppForEmirates@gmail.com";
	        final String password = "fxiutxdycjmfhlzt";
	        
	        // Configure the JavaMail session with your SMTP settings
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
	                return new javax.mail.PasswordAuthentication(fromEmail, password);
	            }
	        });
	        
	        // Create the email message
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(fromEmail));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
	        message.setSubject(subject);
	        message.setText(body);
	        
	        // Send the email using the JavaMail Transport object
	        Transport.send(message);
	        
	        System.out.println("Email sent successfully.");
	    }
	}