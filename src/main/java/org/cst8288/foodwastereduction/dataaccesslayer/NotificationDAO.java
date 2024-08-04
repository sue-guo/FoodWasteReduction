package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.Notification;

/**
 * File: NotificationDAO.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-04 
 * Description: NotificationDAO
 */
public interface NotificationDAO {
    void saveNotification(Notification notification);
    List<Notification> getNotificationsByUserId(int userId);
    void deleteNotification(int notificationId);    
}
