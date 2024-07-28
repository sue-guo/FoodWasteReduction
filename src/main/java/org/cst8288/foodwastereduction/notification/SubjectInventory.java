/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.constants.SurplusStatus;
import org.cst8288.foodwastereduction.model.Inventory;

/**
 *
 * @author ryany
 */
public class SubjectInventory implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private Inventory inventory;

    public SubjectInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(inventory);
        }
    }

    public void setSurplusStatus(SurplusStatus status) {
        if (inventory.isSurplus()) {
            inventory.setSurplusStatus(status);
            if (status != SurplusStatus.NONE) {
                notifyObservers();
            }
        }
    }
}
