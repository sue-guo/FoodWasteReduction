/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 *
 * @author ryany
 */
public interface NotificationMessageService {
    String createDonationMessage(InventoryDTO inventory);
    String createDiscountMessage(InventoryDTO inventory);
}