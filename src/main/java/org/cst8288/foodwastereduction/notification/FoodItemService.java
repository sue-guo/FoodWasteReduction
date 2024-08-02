/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;

/**
 * This interface is used for FoodItem service get FoodCategory from foodItem
 * @author Ryan Xu
 * Created on 2024-07-28
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
