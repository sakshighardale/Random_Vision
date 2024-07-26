package com.project.EmailSend;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSending {
	
	public static boolean sendEmail(String message, String subject, String to, String from) {
		// Varible for gmail host

		String host = "smtp.gmail.com";

		// get system properties

		Properties properties = System.getProperties();
		System.out.println("Properties :" + properties);

		// setting important information to properties object

		// host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step 1: Get the session object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("ghardalesakshi@gmail.com", "wgle cjvs pypr qzkm");
			}

		});

		session.setDebug(true);
		// Step 2: Compose the message
		MimeMessage mimeMsg = new MimeMessage(session);

		try {
			// from
			mimeMsg.setFrom(from);
			// add recipient
			mimeMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// adding subject to message
			mimeMsg.setSubject(subject);

			// adding Text to message
			mimeMsg.setText(message);

			// Step 3: Send the message using Transport class
			Transport.send(mimeMsg);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
