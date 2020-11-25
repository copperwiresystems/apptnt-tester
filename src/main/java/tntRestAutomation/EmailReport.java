package tntRestAutomation;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailReport {

	public static void sendReport() throws IOException {

		final String username = ReusableMethods.getProperty("from");// "copperwiresystemstesting@gmail.com";
		final String password = ReusableMethods.getProperty("password");// "copperwire";

		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			String to = ReusableMethods.getProperty("to");
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("TNT REST-API AUTOMATION REPORT AT-" + ReusableMethods.getDateWithHrMinuteSecond());
			message.setText("PFA");

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String file = "./test-output/emailable-report.html";
			String fileName = "emailable-report.html";
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] a) throws IOException {
		EmailReport.sendReport();
	}
}