package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;

/**
 * File: ObserverCharitableOrganization.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Concrete observer for charitable organization
 */
public class ObserverCharitableOrganization implements Observer {
    private NotificationService notificationService;
    private NotificationMessageService messageService;
    private SubscriptionService subscriptionService;
    private FoodItemService foodItemService;    
    private User charitableOrganization;
    private List<String> notifiedUsers;

    /**
     * Constructor
     * @param notificationService
     * @param messageService
     * @param subscriptionService
     * @param foodItemService
     * @param charitableOrganization
     * @param notifiedUsers 
     */
    public ObserverCharitableOrganization(NotificationService notificationService, 
                                          NotificationMessageService messageService, 
                                          SubscriptionService subscriptionService,
                                          FoodItemService foodItemService,
                                          User charitableOrganization, List<String> notifiedUsers) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.subscriptionService = subscriptionService;
        this.foodItemService = foodItemService;
        this.charitableOrganization = charitableOrganization;
        this.notifiedUsers = notifiedUsers;
    }

    /**
     * method to update inventory
     * @param inventory 
     */
    @Override
    public void update(InventoryDTO inventory) {
        String logMessage;
        if (inventory.getSurplusStatus() == SurplusStatusEnum.Donation) {
            boolean isSubscribed = subscriptionService.isSubscribed(charitableOrganization.getUserID(), inventory.getRetailerId());
            
            try {
                int foodItemId = inventory.getFoodItemId();
                CategoryEnum foodCategory = foodItemService.getFoodCategory(foodItemId);
                boolean isInterested = subscriptionService.isInterestedInCategory(
                    charitableOrganization.getUserID(), 
                    inventory.getRetailerId(), 
                    foodCategory
                );

                if (isSubscribed && isInterested) {
                    String message = messageService.createDonationMessage(inventory);
                    notificationService.sendEmail(charitableOrganization.getUserID(), 
                                                  inventory.getInventoryId(), 
                                                  charitableOrganization.getEmail(), 
                                                  "Donation Available", 
                                                  message);
                    notifiedUsers.add(charitableOrganization.getName());
                }
            } catch (NoSuchElementException e) {
                logMessage = "Failed to get food category for inventory: " + inventory.getInventoryId() + 
                             ". Error: " + e.getMessage();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            }
        }
        logMessage = "Update completed for inventory: " + inventory.getInventoryId();
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
    }
}