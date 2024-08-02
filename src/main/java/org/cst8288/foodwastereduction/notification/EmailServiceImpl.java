/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * This class is Implementation of email service
 * @author Ryan Xu
 * Created on 2024-07-28
 */
public class EmailServiceImpl implements EmailService {

    /**
     * session
     */
    private final Session session;
    
    /**
     * from
     */
    private final String from;
    
    /**
     * is test mode or not
     */
    private final boolean isTestMode;
    
    /**
     * another Constructor used for test mode
     * @param config
     * @param isTestMode 
     */
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

    /**
     * Constructor chaining after test mode
     * @param config 
     */
    public EmailServiceImpl(EmailConfig config) {
        this(config, false);
    }
    
    /**
     * over ride the method defined in interface
     * @param to
     * @param subject
     * @param content
     * @return 
     */
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