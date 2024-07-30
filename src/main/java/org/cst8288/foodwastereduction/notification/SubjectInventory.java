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
 *
 * @author ryany
 */
public class SubjectInventory implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private InventoryDTO inventory;

    public SubjectInventory(InventoryDTO inventory) {
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
            System.out.print(observer); // for testing
            observer.update(inventory);
        }
    }

    public void setSurplusStatus(SurplusStatusEnum status) {
        if (inventory.getIsSurplus()) {
            inventory.setSurplusStatus(status);
            if (status != SurplusStatusEnum.None) {
                notifyObservers();
            }
        }
    }
}
