/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import org.cst8288.foodwastereduction.model.Notification;

/**
 *
 * @author ryany
 */
public interface NotificationService {
    void sendEmail(Integer userId, Integer inventoryId, String email, String subject, String content);
    void sendSMS(Integer userId, Integer inventoryId, String phoneNumber, String message);
    void logNotification(Integer userId, Integer inventoryId, String notificationType);
    List<Notification> getUserNotifications(Integer userId);
    void deleteNotification(Integer notificationId);
}