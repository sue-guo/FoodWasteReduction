/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.InventoryDTO;
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
    public void update(InventoryDTO inventory) {
        System.out.println("Updating observer for inventory: " + inventory.getInventoryId());
        if (inventory.getSurplusStatus() == SurplusStatusEnum.Discount) {
            System.out.println("Processing discount notification for consumer: " + consumer.getUserID());
        
            boolean isSubscribed = subscriptionService.isSubscribed(consumer.getUserID(), inventory.getRetailerId());
            System.out.println("Is consumer subscribed: " + isSubscribed);
            try {
                Integer foodItemId = inventory.getFoodItemId();
                CategoryEnum foodCategory = foodItemService.getFoodCategory(foodItemId);
                System.out.println("Food category: " + foodCategory);
                
                boolean isInterested = subscriptionService.isInterestedInCategory(
                    consumer.getUserID(), 
                    inventory.getRetailerId(), 
                    foodCategory
                );
                System.out.println("Is consumer interested in category: " + isInterested);
                
                if (isSubscribed && isInterested) {
                    String message = messageService.createDiscountMessage(inventory);
                    notificationService.sendEmail(consumer.getUserID(), 
                                                  inventory.getInventoryId(), 
                                                  consumer.getEmail(), 
                                                  "Discount Available", 
                                                  message);
                    System.out.println("Email sent successfully");
                }
            } catch (NoSuchElementException e) {
                // logger ??
                System.err.println("Failed to get food category for inventory: " + inventory.getInventoryId() + ". " + e.getMessage());
            }
        }
    }
}