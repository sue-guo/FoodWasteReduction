/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.Inventory;

/**
 *
 * @author ryany
 */
public interface NotificationMessageService {
    String createDonationMessage(Inventory inventory);
    String createDiscountMessage(Inventory inventory);
}