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
public interface NotificationService {
    void sendEmail(int userId, int inventoryId, String email, String subject, String content);
    void sendSMS(int userId, int inventoryId, String phoneNumber, String message);
    void logNotification(int userId, int inventoryId, String notificationType);
    List<Notification> getUserNotifications(int userId);
    void deleteNotification(int notificationId);
}