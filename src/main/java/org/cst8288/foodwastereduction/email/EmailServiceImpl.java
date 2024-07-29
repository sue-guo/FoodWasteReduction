/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.email;
import org.cst8288.foodwastereduction.email.EmailService;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 *
 * @author ryany
 */
public class EmailServiceImpl implements EmailService {

    private final Session session;
    private final String from;
    private final boolean isTestMode;
    
    
    public EmailServiceImpl(EmailConfig config) {
        this(config, false);
    }

    public EmailServiceImpl(EmailConfig config, boolean isTestMode) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", config.getHost());
        props.put("mail.smtp.port", config.getPort());

        this.from = config.getUsername();
        this.isTestMode = isTestMode;

        this.session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        });
    }

    @Override
    public String sendEmail(String to, String subject, String content) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(isTestMode ? from : to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            return from;
        } catch (MessagingException e) {
            e.printStackTrace();
            // Logger??
            return "Error";
        }
    }
}