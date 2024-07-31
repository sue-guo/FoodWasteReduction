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
    private List<String> notifiedUsers;

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

    @Override
    public void update(InventoryDTO inventory) {
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
                // logger??
                System.err.println("Failed to get food category for inventory: " + inventory.getInventoryId() + ". " + e.getMessage());
            }
        }
    }
}