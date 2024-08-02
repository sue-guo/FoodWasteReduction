/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * Interface of notification Messages service
 * @author Ryan Xu
 * Created on 2024-07-28
 */
public interface NotificationMessageService {
    
    /**
     * Create message for donation
     * @param inventory
     * @return 
     */
    String createDonationMessage(InventoryDTO inventory);
    
    /**
     * Create message for consumer
     * @param inventory
     * @return 
     */
    String createDiscountMessage(InventoryDTO inventory);
}