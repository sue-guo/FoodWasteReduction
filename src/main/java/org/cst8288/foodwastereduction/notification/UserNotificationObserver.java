/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
 */
public class UserNotificationObserver implements SurplusObserver {
    private User user;
    private NotificationService notificationService;
    private FoodItemDAO foodItemDAO;

    public UserNotificationObserver(User user, NotificationService notificationService, FoodItemDAO foodItemDAO) {
        this.user = user;
        this.notificationService = notificationService;
        this.foodItemDAO = foodItemDAO;
    }

    @Override
    public void update(Inventory surplusItem) {
        if (isRelevantSurplus(surplusItem)) {
            sendNotification(surplusItem);
        }
    }

    private boolean isRelevantSurplus(Inventory surplusItem) {
        FoodItem foodItem = foodItemDAO.getFoodItemById(surplusItem.getFoodItemId());
        return user.getCity().equals(surplusItem.getRetailer().getCity()) && 
               user.getFoodPreferences().contains(foodItem.getCategory());
    }

    private void sendNotification(Inventory surplusItem) {
        if ("Email".equals(user.getCommunicationPreference())) {
            notificationService.sendEmail(user.getEmail(), "Surplus Food Alert", createNotificationContent(surplusItem));
        } else if ("Phone".equals(user.getCommunicationPreference())) {
            notificationService.sendSMS(user.getPhoneNumber(), createNotificationContent(surplusItem));
        }
        notificationService.createNotification(user.getUserId(), surplusItem.getInventoryId(), "SurplusAlert");
    }

    private String createNotificationContent(Inventory surplusItem) {
        // Create notification content based on surplusItem
        return "New surplus food item available: " + surplusItem.getFoodItem().getName();
    }
}

