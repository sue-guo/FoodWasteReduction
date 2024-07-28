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
public class ObserverCharitableOrganization implements Observer {
    private NotificationService notificationService;
    private NotificationMessageService messageService;
    private User charitableOrganization;

    public ObserverCharitableOrganization(NotificationService notificationService, 
                                          NotificationMessageService messageService, 
                                          User charitableOrganization) {
        this.notificationService = notificationService;
        this.messageService = messageService;
        this.charitableOrganization = charitableOrganization;
    }

    @Override
    public void update(Inventory inventory) {
        if (inventory.getSurplusStatus() == SurplusStatus.DONATION) {
            String message = messageService.createDonationMessage(inventory);
            notificationService.sendEmail(charitableOrganization.getUserID(), 
                                          inventory.getInventoryID(), 
                                          charitableOrganization.getEmail(), 
                                          "Donation Available", 
                                          message);
        }
    }
}