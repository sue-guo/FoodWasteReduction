/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.model.UserType;

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
    private List<String> notifiedUsers;

    public ObserverConsumer(NotificationService notificationService, 
                            NotificationMessageService messageService,
                            SubscriptionService subscriptionService,
                            FoodItemService foodItemService,
                            User consumer, List<String> notifiedUsers) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.subscriptionService = subscriptionService;
        this.foodItemService = foodItemService;
        this.consumer = consumer;
        this.notifiedUsers = notifiedUsers;
    }


	@Override
    public void update(InventoryDTO inventory) {
        System.out.println("Updating observer for inventory: " + inventory.getInventoryId());
        if (consumer.getUserType() != UserType.CONSUMER) {
            System.out.println("Not a consumer. Skipping discount notification.");
            return; 
        }
        if (inventory.getSurplusStatus() == SurplusStatusEnum.Discount) {
            processDiscountNotification(inventory);
        }
    }
    
     private void processDiscountNotification(InventoryDTO inventory) {
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
                System.out.println("Discount email sent successfully to consumer");
                notifiedUsers.add(consumer.getName());
            }
        } catch (NoSuchElementException e) {
            System.err.println("Failed to get food category for inventory: " + inventory.getInventoryId() + ". " + e.getMessage());
        }
    } 
}