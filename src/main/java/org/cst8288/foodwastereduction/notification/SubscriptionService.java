/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
 */
public class SubscriptionService {
    private InventoryManager inventoryManager;
    private SubscriptionDAO subscriptionDAO;
	private NotificationService notificationService;
    private FoodItemDAO foodItemDAO;

    public SubscriptionService(InventoryManager inventoryManager, SubscriptionDAO subscriptionDAO, 
                               NotificationService notificationService, FoodItemDAO foodItemDAO) {
        this.inventoryManager = inventoryManager;
        this.subscriptionDAO = subscriptionDAO;
        this.notificationService = notificationService;
        this.foodItemDAO = foodItemDAO;
    }

    public void subscribe(User user, int retailerId) {
        Subscription subscription = new Subscription(user.getUserId(), retailerId, user.getCommunicationPreference(), user.getFoodPreferences());
        subscriptionDAO.saveSubscription(subscription);
        UserNotificationObserver observer = new UserNotificationObserver(user, notificationService, foodItemDAO);
        inventoryManager.registerObserver(observer);
    }

    public void unsubscribe(User user, int retailerId) {
        subscriptionDAO.deleteSubscription(user.getUserId(), retailerId);
    }
}
