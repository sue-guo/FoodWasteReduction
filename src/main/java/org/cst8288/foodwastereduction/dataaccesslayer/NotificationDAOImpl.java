/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.model.Notification;

/**
 *
 * @author ryany
 */
public class NotificationDAOImpl implements NotificationDAO {

    @Override
    public void saveNotification(Notification notification) {
        String sql = "INSERT INTO Notifications (UserID, InventoryID, NotificationType, CreatedAt) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notification.getUserId());
            pstmt.setInt(2, notification.getInventoryId());
            pstmt.setString(3, notification.getNotificationType());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving notification", e);
        }
    }

    @Override
    public List<Notification> getNotificationsByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE UserID = ?";
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving notification", e);            
        }
        return notifications;
    }

    @Override
    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM Notifications WHERE NotificationID = ?";
        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, notificationId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving notification", e);            
        }
    }
}
