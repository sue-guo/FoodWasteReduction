/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.sql.Timestamp;
import java.util.List;
import org.cst8288.foodwastereduction.dataaccesslayer.NotificationDAO;
import org.cst8288.foodwastereduction.model.Notification;

/**
 *
 * @author ryany
 */
public class NotificationService {
    private NotificationDAO notificationDAO;
    private Notification currentNotification;
    
    public NotificationService(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    private void initializeNotification(int userId, int inventoryId, String notificationType) {
        this.currentNotification = new Notification(0, userId, inventoryId, notificationType, new Timestamp(System.currentTimeMillis()));
    }
	
    public void sendEmail(int userId, int inventoryId, String email, String subject, String content) {
		initializeNotification(userId, inventoryId, "SurplusAlert");
        // Implement email sending logic
		saveNotification();
    }

    public void sendSMS(int userId, int inventoryId, String phoneNumber, String message) {
		initializeNotification(userId, inventoryId, "SurplusAlert");
        // Implement SMS sending logic
		saveNotification();
    }

    public void logNotification(int userId, int inventoryId, String notificationType) {
        if (!notificationType.equals("SurplusAlert") && !notificationType.equals("Other")) {
            throw new IllegalArgumentException("Invalid notification type");
        }
        initializeNotification(userId, inventoryId, notificationType);
        saveNotification();
    }

    public List<Notification> getUserNotifications(int userId) {
        return notificationDAO.getNotificationsByUserId(userId);
    }

    private void saveNotification() {
        if (currentNotification != null) {
            notificationDAO.saveNotification(currentNotification);
            currentNotification = null; // Reset after saving
        }
    }
	
    public void deleteNotification(int notificationId) {
        notificationDAO.deleteNotification(notificationId);
    }
}
