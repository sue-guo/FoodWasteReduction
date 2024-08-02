/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * Concrete subject in observer pattern
 * @author Ryan Xu
 * Created on 2024-07-28
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
