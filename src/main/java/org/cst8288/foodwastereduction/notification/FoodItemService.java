/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;

/**
 * File: FoodItemService.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03
 * Description: This interface is used for FoodItem service get FoodCategory from foodItem
 */
public interface FoodItemService {
    /**
     * Abstract method to get food category
     * @param foodItemId
     * @return
     * @throws NoSuchElementException 
     */
    CategoryEnum getFoodCategory(Integer foodItemId) throws NoSuchElementException;
}
