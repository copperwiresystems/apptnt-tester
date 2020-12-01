package tntRestAutomation;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailReport {

	public static void sendReport() throws IOException {

		final String username = TestPropertyReader.getProperty("from");
		final String password = TestPropertyReader.getProperty("password");

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
			String to = TestPropertyReader.getProperty("to");
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("TNT REST-API AUTOMATION REPORT AT-" + ReusableMethods.getDateWithHrMinuteSecond());
			message.setText("PFA");

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String file = getReportPath();
			String fileName = "test-report.html";
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

	private static String getReportPath() {
		String mavenReportPath = "./target/site/surefire-report.html";
		String testngReportPath = "./test-output/emailable-report.html";
		try {
			if (/*ReusableMethods.isExecutionFromMaven()*/true && !new File(mavenReportPath).exists()) {
				System.out.println("Generating maven report");
				runCommand("cmd /C mvn surefire-report:report-only");
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ReusableMethods.isExecutionFromMaven() ? mavenReportPath : testngReportPath;
	}

	private static void runCommand(String cmd) throws Exception {
		Process application = Runtime.getRuntime().exec(cmd);
		application.waitFor();
	}

	public static void main(String[] a) throws IOException {
		EmailReport.sendReport();
	}
}