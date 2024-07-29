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
public class ObserverCharitableOrganization implements Observer {
    private NotificationService notificationService;
    private NotificationMessageService messageService;
    private SubscriptionService subscriptionService;
    private FoodItemService foodItemService;    
    private User charitableOrganization;

    public ObserverCharitableOrganization(NotificationService notificationService, 
                                          NotificationMessageService messageService, 
                                          SubscriptionService subscriptionService,
                                          FoodItemService foodItemService,
                                          User charitableOrganization) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.subscriptionService = subscriptionService;
        this.foodItemService = foodItemService;
        this.charitableOrganization = charitableOrganization;
    }

    @Override
    public void update(Inventory inventory) {
        if (inventory.getSurplusStatus() == SurplusStatus.DONATION) {
            boolean isSubscribed = subscriptionService.isSubscribed(charitableOrganization.getUserId(), inventory.getRetailerId());
            
            try {
                int foodItemId = inventory.getFoodItemId();
                FoodCategory foodCategory = foodItemService.getFoodCategory(foodItemId);
                boolean isInterested = subscriptionService.isInterestedInCategory(
                    charitableOrganization.getUserId(), 
                    inventory.getRetailerId(), 
                    foodCategory
                );

                if (isSubscribed && isInterested) {
                    String message = messageService.createDonationMessage(inventory);
                    notificationService.sendEmail(charitableOrganization.getUserId(), 
                                                  inventory.getInventoryId(), 
                                                  charitableOrganization.getEmail(), 
                                                  "Donation Available", 
                                                  message);
                }
            } catch (NoSuchElementException e) {
                // logger??
                System.err.println("Failed to get food category for inventory: " + inventory.getInventoryId() + ". " + e.getMessage());
            }
        }
    }
}