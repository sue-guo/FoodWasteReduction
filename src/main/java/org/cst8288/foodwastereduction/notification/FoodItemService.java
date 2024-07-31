/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;

/**
 *
 * @author ryany
 */
public interface FoodItemService {
    /**
     * 
     * @param foodItemId
     * @return
     * @throws NoSuchElementException 
     */
    CategoryEnum getFoodCategory(Integer foodItemId) throws NoSuchElementException;
}
