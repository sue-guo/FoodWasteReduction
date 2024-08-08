
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * File: NotificationMessageService.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Interface of notification Messages service
 */
public interface NotificationMessageService {
    
    /**
     * Create message for donation
     * @param inventory
     * @return String type
     */
    String createDonationMessage(InventoryDTO inventory);
    
    /**
     * Create message for consumer
     * @param inventory
     * @return String type
     */
    String createDiscountMessage(InventoryDTO inventory);
}