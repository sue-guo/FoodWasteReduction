/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.constants.FoodCategory;

/**
 *
 * @author ryany
 */
public interface FoodItemService {
    FoodCategory getFoodCategory(int foodItemId) throws NoSuchElementException;
}
