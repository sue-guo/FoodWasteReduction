/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import org.cst8288.foodwastereduction.model.Notification;

/**
 * Interface for Notification service
 * @author Ryan Xu
 * Created on 2024-07-28
 */
public interface NotificationService {
    /**
     * abstract method to sendEmail
     * @param userId
     * @param inventoryId
     * @param email
     * @param subject
     * @param content 
     */
    void sendEmail(Integer userId, Integer inventoryId, String email, String subject, String content);
    
    /**
     * abstract method to sendSMS
     * @param userId
     * @param inventoryId
     * @param phoneNumber
     * @param message 
     */
    void sendSMS(Integer userId, Integer inventoryId, String phoneNumber, String message);
    
    /**
     * abstract method to logNotification in log file, but not database
     * @param userId
     * @param inventoryId
     * @param notificationType 
     */
    void logNotification(Integer userId, Integer inventoryId, String notificationType);
    
    /**
     * abstract method to get user notification
     * @param userId
     * @return 
     */
    List<Notification> getUserNotifications(Integer userId);
    
    /**
     * abstract method to delete notification
     * @param notificationId 
     */
    void deleteNotification(Integer notificationId);
}