package mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EnvioJavaMail {
	private String username = "";
	private String password = "";
	private String de = "";
	private String[] para;
	private String assunto = "";
	private String mensagem = "";
	private File anexoFile;

	public EnvioJavaMail(String username, String password, String de, String para, String assunto, String mensagem) {
	    this.username = username;
	    this.password = password;
	    this.de = de;
	    this.para = para.split(",");
	    this.assunto = assunto;
	    this.mensagem = mensagem;
	}

	public EnvioJavaMail(String username, String password, String de, String para, String assunto, String mensagem,File anexoFile) {
	    this(username, password, de, para, assunto, mensagem); // chama o construtor sem anexo
	    if (anexoFile != null) {
	        this.anexoFile = anexoFile;
	    }
	}
	public boolean enviarEmailAnexo(boolean envioHtml) throws IOException, Exception {
	    boolean enviadoComSucesso = false;
	    if (anexoFile == null || !anexoFile.exists()) {
	        enviadoComSucesso = enviarEmail(envioHtml);
	    } else {
			// Configuração das propriedades
			String host = "smtp-mail.outlook.com";
			int port = 587;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");

			// Autenticação do usuário
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(username, password);
				}
			});

			try {
				// Cria a mensagem de email
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username, de));
				for (String address : para) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(address.trim()));
				}
				message.setSubject(assunto);

				/* texto email */
				MimeBodyPart corpoEmailBodyPart = new MimeBodyPart();
				if (envioHtml) {
					corpoEmailBodyPart.setContent(mensagem, "text/html; charset=utf8");
				} else {
					corpoEmailBodyPart.setText(mensagem);
				}

				/* Anexo do email */
				MimeBodyPart anexoEmailBodyPart = new MimeBodyPart();
				anexoEmailBodyPart.setDataHandler(new DataHandler(
						new ByteArrayDataSource(new FileInputStream(anexoFile), "application/octet-stream")));
				anexoEmailBodyPart.setFileName(anexoFile.getName());

				/* unifica o corpo do email com o anexo */
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(corpoEmailBodyPart);
				multipart.addBodyPart(anexoEmailBodyPart);

				message.setContent(multipart);

				// Envia a mensagem
				Transport.send(message);
				enviadoComSucesso = true;
			} catch (MessagingException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return enviadoComSucesso;
	}	
	public boolean enviarEmail(boolean envioHtml) {
		boolean enviadoComSucessoSemAnexo = false;
		// Configuração das propriedades
		String host = "smtp-mail.outlook.com";
		int port = 587;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");

		// Autenticação do usuário
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(username, password);
			}
		});

		try {
			// Cria a mensagem de email
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username, de));
			for (String address : para) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(address.trim()));
			}
			message.setSubject(assunto);
			if (envioHtml) {
				message.setContent(mensagem, "text/html; charset=utf8");
			} else {
				message.setText(mensagem);
			}
			// Envia a mensagem
			Transport.send(message);
			enviadoComSucessoSemAnexo = true;
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return enviadoComSucessoSemAnexo;
	}

	
}