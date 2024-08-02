/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * Observer interface, Observer pattern
 * @author Ryan Xu
 * Created on 2024-07-28
 */
public interface Observer {
    
    /**
     * Abstract method to update inventory
    */
    void update(InventoryDTO inventory);    
}
