/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.constants.FoodCategory;
import org.cst8288.foodwastereduction.constants.SurplusStatus;
import org.cst8288.foodwastereduction.model.Inventory;
import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
 */
public class ObserverConsumer implements Observer {
    private NotificationService notificationService;
    private NotificationMessageService messageService;
    private SubscriptionService subscriptionService;
    private FoodItemService foodItemService;
    private User consumer;

    public ObserverConsumer(NotificationService notificationService, 
                            NotificationMessageService messageService,
                            SubscriptionService subscriptionService,
                            FoodItemService foodItemService,
                            User consumer) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.subscriptionService = subscriptionService;
        this.foodItemService = foodItemService;
        this.consumer = consumer;
    }


	@Override
    public void update(Inventory inventory) {
        if (inventory.getSurplusStatus() == SurplusStatus.DISCOUNT) {
            boolean isSubscribed = subscriptionService.isSubscribed(consumer.getUserId(), inventory.getRetailerId());
            
            try {
                int foodItemId = inventory.getFoodItemId();
                FoodCategory foodCategory = foodItemService.getFoodCategory(foodItemId);
                boolean isInterested = subscriptionService.isInterestedInCategory(
                    consumer.getUserId(), 
                    inventory.getRetailerId(), 
                    foodCategory
                );

                if (isSubscribed && isInterested) {
                    String message = messageService.createDiscountMessage(inventory);
                    notificationService.sendEmail(consumer.getUserId(), 
                                                  inventory.getInventoryId(), 
                                                  consumer.getEmail(), 
                                                  "Discount Available", 
                                                  message);
                }
            } catch (NoSuchElementException e) {
                // logger ??
                System.err.println("Failed to get food category for inventory: " + inventory.getInventoryId() + ". " + e.getMessage());
            }
        }
    }
}