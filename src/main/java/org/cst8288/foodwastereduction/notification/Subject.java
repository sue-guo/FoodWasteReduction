package org.cst8288.foodwastereduction.notification;

/**
 * File: Subject.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Subject for Observer pattern
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
