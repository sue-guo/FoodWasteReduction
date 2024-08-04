package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.Notification;

/**
 * File: NotificationDAOImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-04 
 * Description: Implementation of NotificationDAO
 */
public class NotificationDAOImpl implements NotificationDAO {

    @Override
    public void saveNotification(Notification notification) {
        String logMessage;
        String sql = "INSERT INTO Notifications (UserID, InventoryID, NotificationType, CreatedAt) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notification.getUserId());
            pstmt.setInt(2, notification.getInventoryId());
            pstmt.setString(3, notification.getNotificationType());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logMessage = "Error saving notification: " + e.getMessage() + 
             " | UserID: " + notification.getUserId() + 
             " | InventoryID: " + notification.getInventoryId() + 
             " | NotificationType: " + notification.getNotificationType();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);

            throw new RuntimeException("Error saving notification", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
        String logMessage;
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE UserID = ?";
        
        // DEBUG level log
        logMessage = "Attempting to retrieve notifications for UserID: " + userId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Notification notification = new Notification(
                    rs.getInt("NotificationID"),
                    rs.getInt("UserID"),
                    rs.getInt("InventoryID"),
                    rs.getString("NotificationType"),
                    rs.getTimestamp("CreatedAt")
                );
                notifications.add(notification);
            }
            // DEBUG level log
            logMessage = "Retrieved " + notifications.size() + " notifications for UserID: " + userId;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        } catch (SQLException e) {
            logMessage = "Error retrieving notifications for UserID: " + userId + " | Error: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error retrieving notification", e);            
        }
        return notifications;
    }

    @Override
    public void deleteNotification(int notificationId) {
        String logMessage;
        String sql = "DELETE FROM Notifications WHERE NotificationID = ?";
        
        logMessage = "Attempting to delete notification with ID: " + notificationId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);

        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
            
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                logMessage = "Successfully deleted notification with ID: " + notificationId;
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
            } else {
                logMessage = "No notification found with ID: " + notificationId;
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            }           
        } catch (SQLException e) {
            logMessage = "Error deleting notification with ID: " + notificationId + " | Error: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error saving notification", e);            
        }
    }
}
