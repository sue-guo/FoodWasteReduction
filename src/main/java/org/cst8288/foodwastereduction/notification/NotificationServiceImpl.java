/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.model.Notification;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;

/**
 *
 * @author ryany
 */
public class NotificationServiceImpl implements NotificationService {
    private NotificationDAO notificationDAO;
    private Notification currentNotification;
    private EmailService emailService;
    private boolean isTestMode;
    private ServletContext servletContext;
    
    /**
     * 
     * @param notificationDAO
     * @param isTestMode
     * @param eamilconfig, the configuration used in real operation environment.
     */
    public NotificationServiceImpl(NotificationDAO notificationDAO, boolean isTestMode, EmailConfig eamilconfig, ServletContext servletContext) {
        this.notificationDAO = notificationDAO;
        this.isTestMode = isTestMode;
        
        // EmailService
        EmailConfig emailConfig = isTestMode ? EmailConfig.getTestConfig() : eamilconfig;
        this.emailService = new EmailServiceImpl(emailConfig, isTestMode);
        this.servletContext = servletContext;
    }
    
    public NotificationServiceImpl(NotificationDAO notificationDAO, boolean isTestMode, EmailConfig eamilconfig) {
        this.notificationDAO = notificationDAO;
        this.isTestMode = isTestMode;
        
        // EmailService
        EmailConfig emailConfig = isTestMode ? EmailConfig.getTestConfig() : eamilconfig;
        this.emailService = new EmailServiceImpl(emailConfig, isTestMode);
    }
    
    
    
    private void initializeNotification(Integer userId, Integer inventoryId, String notificationType) {
        this.currentNotification = new Notification(0, userId, inventoryId, notificationType, new Timestamp(System.currentTimeMillis()));
    }

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

    @Override
    public void sendSMS(Integer userId, Integer inventoryId, String phoneNumber, String message) {
        initializeNotification(userId, inventoryId, "SurplusAlert");
        // Implement SMS sending logic
        saveNotification();
    }

    @Override
    public void logNotification(Integer userId, Integer inventoryId, String notificationType) {
        if (!notificationType.equals("SurplusAlert") && !notificationType.equals("Other")) {
            throw new IllegalArgumentException("Invalid notification type");
        }
        initializeNotification(userId, inventoryId, notificationType);
        saveNotification();
    }

    @Override
    public List<Notification> getUserNotifications(Integer userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    @Override
    public void deleteNotification(Integer notificationId) {
        notificationDAO.deleteNotification(notificationId);
    }

    private void saveNotification() {
        if (currentNotification != null) {
            notificationDAO.saveNotification(currentNotification);
            currentNotification = null; // Reset after saving
        }
    }
    
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