/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDAOImpl;
import org.cst8288.foodwastereduction.email.EmailConfig;
import org.cst8288.foodwastereduction.email.EmailService;
import org.cst8288.foodwastereduction.email.EmailServiceImpl;
import org.cst8288.foodwastereduction.model.Notification;

/**
 *
 * @author ryany
 */
public class NotificationServiceImpl implements NotificationService {
    private NotificationDAO notificationDAO;
    private Notification currentNotification;
    private EmailService emailService;
    private boolean isTestMode;
    
    /**
     * 
     * @param notificationDAO
     * @param isTestMode
     * @param eamilconfig, the configuration used in real operation environment.
     */
    public NotificationServiceImpl(NotificationDAO notificationDAO, boolean isTestMode, EmailConfig eamilconfig) {
        this.notificationDAO = notificationDAO;
        this.isTestMode = isTestMode;
        
        // EmailService
        EmailConfig emailConfig = isTestMode ? EmailConfig.getTestConfig() : eamilconfig;
        this.emailService = new EmailServiceImpl(emailConfig, isTestMode);
    }

    private void initializeNotification(int userId, int inventoryId, String notificationType) {
        this.currentNotification = new Notification(0, userId, inventoryId, notificationType, new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public void sendEmail(int userId, int inventoryId, String email, String subject, String content) {
        initializeNotification(userId, inventoryId, "SurplusAlert");
        try {
            String from = emailService.sendEmail(email, subject, content);
            logNotification(from, email, subject, content);
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
    public void sendSMS(int userId, int inventoryId, String phoneNumber, String message) {
        initializeNotification(userId, inventoryId, "SurplusAlert");
        // Implement SMS sending logic
        saveNotification();
    }

    @Override
    public void logNotification(int userId, int inventoryId, String notificationType) {
        if (!notificationType.equals("SurplusAlert") && !notificationType.equals("Other")) {
            throw new IllegalArgumentException("Invalid notification type");
        }
        initializeNotification(userId, inventoryId, notificationType);
        saveNotification();
    }

    @Override
    public List<Notification> getUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    @Override
    public void deleteNotification(int notificationId) {
        notificationDAO.deleteNotification(notificationId);
    }

    private void saveNotification() {
        if (currentNotification != null) {
            notificationDAO.saveNotification(currentNotification);
            currentNotification = null; // Reset after saving
        }
    }
    
    private void logNotification(String from, String to, String subject, String content) {
        try (FileWriter fw = new FileWriter("./src/main/java/org/cst8288/foodwastereduction/logger/notificationLog.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            pw.printf("Date: %s\nFrom: %s\nTo: %s\nSubject: %s\nContent: %s\n\n", 
                      timestamp, from, to, subject, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}