package org.cst8288.foodwastereduction.notification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletContext;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAO;
import org.cst8288.foodwastereduction.model.Notification;

/**
 * File: NotificationServiceImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Implementation of interface NotificationService
 */
public class NotificationServiceImpl implements NotificationService {
    private NotificationDAO notificationDAO;
    private Notification currentNotification;
    private EmailService emailService;
    private boolean isTestMode;
    private ServletContext servletContext;
    
    /**
     * Constructor
     * @param notificationDAO
     * @param isTestMode
     * @param eamilconfig, the configuration used in real operation environment.
     * @param servletContext
     */
    public NotificationServiceImpl(NotificationDAO notificationDAO, boolean isTestMode, EmailConfig eamilconfig, ServletContext servletContext) {
        this.notificationDAO = notificationDAO;
        this.isTestMode = isTestMode;
        
        // EmailService
        EmailConfig emailConfig = isTestMode ? EmailConfig.getTestConfig() : eamilconfig;
        this.emailService = new EmailServiceImpl(emailConfig, isTestMode);
        this.servletContext = servletContext;
    }
    
    /**
     * another constructor
     * @param notificationDAO
     * @param isTestMode
     * @param eamilconfig 
     */
    public NotificationServiceImpl(NotificationDAO notificationDAO, boolean isTestMode, EmailConfig eamilconfig) {
        this.notificationDAO = notificationDAO;
        this.isTestMode = isTestMode;
        
        // EmailService
        EmailConfig emailConfig = isTestMode ? EmailConfig.getTestConfig() : eamilconfig;
        this.emailService = new EmailServiceImpl(emailConfig, isTestMode);
    }
    
    
    /**
     * Initialize a Notification dto
     * @param userId
     * @param inventoryId
     * @param notificationType 
     */
    private void initializeNotification(Integer userId, Integer inventoryId, String notificationType) {
        this.currentNotification = new Notification(0, userId, inventoryId, notificationType, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Send email
     * @param userId
     * @param inventoryId
     * @param email
     * @param subject
     * @param content 
     */
    @Override
    public void sendEmail(Integer userId, Integer inventoryId, String email, String subject, String content) {
        initializeNotification(userId, inventoryId, "SurplusAlert");
        try {
            String from = emailService.sendEmail(email, subject, content);
//            logTxtNotification(from, email, subject, content);
            saveNotification();
            if (isTestMode) {
                System.out.println("Test mode: Email sent to test account. Original recipient: " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // logger?
        }
    }

    /**
     * Send SMS
     * @param userId
     * @param inventoryId
     * @param phoneNumber
     * @param message 
     */
    @Override
    public void sendSMS(Integer userId, Integer inventoryId, String phoneNumber, String message) {
        initializeNotification(userId, inventoryId, "SurplusAlert");
        // Implement SMS sending logic
        saveNotification();
    }

    /**
     * log notification
     * @param userId
     * @param inventoryId
     * @param notificationType 
     */
    @Override
    public void logNotification(Integer userId, Integer inventoryId, String notificationType) {
        if (!notificationType.equals("SurplusAlert") && !notificationType.equals("Other")) {
            throw new IllegalArgumentException("Invalid notification type");
        }
        initializeNotification(userId, inventoryId, notificationType);
        saveNotification();
    }

    /**
     * get user notification
     * @param userId
     * @return 
     */
    @Override
    public List<Notification> getUserNotifications(Integer userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    /**
     * delete notification
     * @param notificationId 
     */
    @Override
    public void deleteNotification(Integer notificationId) {
        notificationDAO.deleteNotification(notificationId);
    }

    /**
     * save notification to database
     */
    private void saveNotification() {
        if (currentNotification != null) {
            notificationDAO.saveNotification(currentNotification);
            currentNotification = null; // Reset after saving
        }
    }
    
    /**
     * log notification to text file
     * @param from
     * @param to
     * @param subject
     * @param content 
     */
    private void logTxtNotification(String from, String to, String subject, String content) {
        String logFilePath = servletContext.getRealPath("/WEB-INF/logs/notificationLog.txt");
        File logFile = new File(logFilePath);
        logFile.getParentFile().mkdirs(); 
        
        try (FileWriter fw = new FileWriter(logFilePath, true);
            PrintWriter pw = new PrintWriter(fw)) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            pw.printf("Date: %s\nFrom: %s\nTo: %s\nSubject: %s\nContent: %s\n\n", 
                      timestamp, from, to, subject, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}