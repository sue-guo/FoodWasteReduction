package org.cst8288.foodwastereduction.notification;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
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
        String logMessage = "Notifying " + observers.size() + " observers for inventory " + inventory.getInventoryId();
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        for (Observer observer : observers) {
            logMessage = "Notifying observer: " + observer.getClass().getSimpleName();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
            observer.update(inventory);
        }
    }

    /**
     * set Surplus status
     * @param status 
     */
    public void setSurplusStatus(SurplusStatusEnum status) {
        String logMessage = "Attempting to set surplus status to " + status + " for inventory " + inventory.getInventoryId();
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        if (inventory.getIsSurplus()) {
            inventory.setSurplusStatus(status);
            logMessage = "Surplus status set to " + status + " for inventory " + inventory.getInventoryId();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);

            if (status != SurplusStatusEnum.None) {
                logMessage = "Initiating observer notification for inventory " + inventory.getInventoryId();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
                notifyObservers();
            }
        }else {
            logMessage = "Failed to set surplus status: inventory " + inventory.getInventoryId() + " is not marked as surplus";
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
        }
    }
}
