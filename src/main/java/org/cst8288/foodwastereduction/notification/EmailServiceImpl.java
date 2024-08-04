package org.cst8288.foodwastereduction.notification;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

/**
 * File: EmailServiceImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: This class is Implementation of email service
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
        String logMessage;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", config.getHost());
        props.put("mail.smtp.port", config.getPort());

        this.from = config.getUsername();
        this.isTestMode = isTestMode;

        try {
            this.session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getUsername(), config.getPassword());
                }
            });
            logMessage = "Email session created successfully";
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
        } catch (Exception e) {
            logMessage = "Error creating email session: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Failed to initialize email service", e);
        }
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
        String logMessage;
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(isTestMode ? from : to));
            message.setSubject(subject);
            message.setText(content);
            String actualRecipient = isTestMode ? from : to;
            Transport.send(message);
			logMessage = "Email sent successfully" + 
			 " | From: " + from + 
			 " | To: " + actualRecipient + 
			 " | Subject: " + subject;
			LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
          
            return from;
        } catch (MessagingException e) {
            logMessage = "Error sending email" + 
                         " | From: " + from + 
                         " | To: " + (isTestMode ? from : to) + 
                         " | Subject: " + subject + 
                         " | Error: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);

            return "Error";
        }
    }
}