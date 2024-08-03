package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * File: Observer.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Observer interface, Observer pattern
 */
public interface Observer {
    
    /**
     * Abstract method to update inventory
    */
    void update(InventoryDTO inventory);    
}
