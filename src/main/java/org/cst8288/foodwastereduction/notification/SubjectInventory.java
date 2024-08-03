package org.cst8288.foodwastereduction.notification;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * File: SubjectInventory.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Concrete subject in observer pattern
 */
public class SubjectInventory implements Subject {
    /**
     * Observers List
     */
    private List<Observer> observers = new ArrayList<>();
    
    /**
     * inventoryDTO
     */
    private InventoryDTO inventory;
    
    /**
     * Constructor
     * @param inventory 
     */
    public SubjectInventory(InventoryDTO inventory) {
        this.inventory = inventory;
    }

    /**
     * Concrete method of registering observer
     * @param observer 
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Concrete method of removing observer
     * @param observer 
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * concrete method for notifying observers
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            System.out.print(observer); // for testing
            observer.update(inventory);
        }
    }

    /**
     * set Surplus status
     * @param status 
     */
    public void setSurplusStatus(SurplusStatusEnum status) {
        if (inventory.getIsSurplus()) {
            inventory.setSurplusStatus(status);
            if (status != SurplusStatusEnum.None) {
                notifyObservers();
            }
        }
    }
}
