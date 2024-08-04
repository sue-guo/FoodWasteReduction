/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import java.sql.Timestamp;

/**
 * dto for table Notifications
 * @author Ryan Xu
 * Created on 2024-7-29
 */
public class Notification {

    /** 
     * Unique identifier for the notification.
     */
    private int notificationId;

    /** 
     * Unique identifier for the user associated with the notification.
     */
    private int userId;

    /** 
     * Unique identifier for the inventory item associated with the notification.
     */
    private int inventoryId;

    /** 
     * The type of notification.
     */
    private String notificationType;

    /** 
     * The timestamp when the notification was created.
     */
    private Timestamp createdAt;

    // Constructor
    public Notification(int notificationId, int userId, int inventoryId, String notificationType, Timestamp createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
    }

    /**
     * Gets the unique identifier for the notification.
     * @return the notification ID
     */
    public int getNotificationId() {
        return notificationId;
    }
    
    /**
     * Sets the unique identifier for the notification.
     * @param notificationId the notification ID to set
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    
    /**
     * Gets the unique identifier for the user associated with the notification.
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Sets the unique identifier for the user associated with the notification.
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * Gets the unique identifier for the inventory item associated with the notification.
     * @return the inventory ID
     */
    public int getInventoryId() {
        return inventoryId;
    }
    
    /**
     * Sets the unique identifier for the inventory item associated with the notification.
     * @param inventoryId the inventory ID to set
     */
    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
    
    /**
     * Gets the type of the notification.
     * @return the notification type
     */
    public String getNotificationType() {
        return notificationType;
    }
    
    /**
     * Sets the type of the notification.
     * @param notificationType the notification type to set
     */
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    
    /**
     * Gets the timestamp when the notification was created.
     * @return the creation timestamp
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Sets the timestamp when the notification was created.
     * @param createdAt the creation timestamp to set
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }  
     
}
