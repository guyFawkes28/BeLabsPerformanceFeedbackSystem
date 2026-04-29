package org.belabs.feedback.service;

import org.belabs.feedback.util.EmailUtil;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        Properties properties = EmailUtil.loadProperties();
        if (properties.getProperty("mail.username").isBlank() || properties.getProperty("mail.password").isBlank()) {
            throw new IllegalStateException("Configure src/main/resources/email.properties before sending emails.");
        }

        Session session = Session.getInstance(properties, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        properties.getProperty("mail.username"),
                        properties.getProperty("mail.password")
                );
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getProperty("mail.from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException exception) {
            throw new RuntimeException("No fue posible enviar el correo.", exception);
        }
    }
}
