package util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;

public class SendMail {
	private static Properties props = new Properties();

	static {
		loadProperties();
	}

	static void loadProperties() {
		try {
			InputStream is = SendMail.class
					.getResourceAsStream("/META-INF/mail.properties");
			props.load(is);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void send(String from, String to, String subject, String body) {

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(props
								.getProperty("mail.smtp.user"), props
								.getProperty("mail.smtp.pass"));

					}
				});

		/** Ativa Debug para sessão */
		session.setDebug("true".equals(props.getProperty("mail.debug")));

		try {

			Message message = new MimeMessage(session);
			//Remetente
			message.setFrom(new InternetAddress(from));

      //Destinatário(s)
			Address[] toUser = InternetAddress.parse(to);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(subject);//Assunto
			message.setText(body);

			/**Método para enviar a mensagem criada*/
			Transport.send(message);

			System.out.println("Email Sent!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String ... args) {
	  String from = props.getProperty("mail.from");
	  String to = props.getProperty("mail.to");
	  String subject = "Assunto";
	  String body = "Body";
	  send(from, to, subject, body);
	}

}
