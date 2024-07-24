/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import java.sql.Timestamp;

/**
 * dto for table Notifications
 * @author ryany
 */
public class Notification {
    private int notificationId;
    private int userId;
    private int inventoryId;
    private String notificationType;
    private Timestamp createdAt;

    // Constructor
    public Notification(int notificationId, int userId, int inventoryId, String notificationType, Timestamp createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.inventoryId = inventoryId;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", inventoryId=" + inventoryId +
                ", notificationType='" + notificationType + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }    
     
}
