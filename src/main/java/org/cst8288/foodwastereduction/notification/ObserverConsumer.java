/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

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
    private User consumer;

    public ObserverConsumer(NotificationService notificationService, 
                            NotificationMessageService messageService, 
                            User consumer) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.consumer = consumer;
    }

    @Override
    public void update(Inventory inventory) {
        if (inventory.getSurplusStatus() == SurplusStatus.DISCOUNT) {
            String message = messageService.createDiscountMessage(inventory);
            notificationService.sendEmail(consumer.getUserID(), 
                                          inventory.getInventoryID(), 
                                          consumer.getEmail(), 
                                          "Discount Available", 
                                          message);
        }
    }
}