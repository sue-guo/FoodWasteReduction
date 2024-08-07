package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

/**
 * File: ObserverConsumer.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Concrete observer for consumer
 */
public class ObserverConsumer implements Observer {
    private final NotificationService notificationService;
    private final NotificationMessageService messageService;
    private final SubscriptionService subscriptionService;
    private final FoodItemService foodItemService;
    private final User consumer;
    private final List<String> notifiedUsers;

    /**
     * constructor
     * @param notificationService
     * @param messageService
     * @param subscriptionService
     * @param foodItemService
     * @param consumer
     * @param notifiedUsers 
     */
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

    /**
     * update the inventory
     * @param inventory 
     */
    @Override
    public void update(InventoryDTO inventory) {
        String logMessage;
        
        logMessage = "Updating observer for inventory: " + inventory.getInventoryId();
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        if (consumer.getUserType() != UserType.CONSUMER) {
            logMessage = "Not a consumer. Skipping discount notification. UserType: " + consumer.getUserType();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
            return; 
        }
        if (inventory.getSurplusStatus() == SurplusStatusEnum.Discount) {
            logMessage = "Processing discount notification for inventory: " + inventory.getInventoryId();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
            processDiscountNotification(inventory);
        }
        logMessage = "Update completed for inventory: " + inventory.getInventoryId();
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
    }
    
    /**
     * private method to deal with discount notification
     * @param inventory 
     */
    private void processDiscountNotification(InventoryDTO inventory) {
        String logMessage;
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
            logMessage = "Failed to get food category for inventory: " + inventory.getInventoryId() + 
                             ". Error: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
        }
    }
}