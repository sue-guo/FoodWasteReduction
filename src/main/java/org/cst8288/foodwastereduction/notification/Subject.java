/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

/**
 * Subject for Observer pattern
 * @author Ryan Xu
 * Created on 2024-07-28
 */
public interface Subject {
    /**
     * Abstract method for registering Observer
     * @param observer 
     */
    void registerObserver(Observer observer);
    
    /**
     * Abstract method for removing observer
     * @param observer 
     */
    void removeObserver(Observer observer);
    
    /**
     * Abstract method for notify Observers
     */
    void notifyObservers();    
}
