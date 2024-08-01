/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;
import java.util.List;
import org.cst8288.foodwastereduction.model.FoodItemDTO;


/**
 *
 * @author ryany
 */
public interface FoodItemDAO {
    void addFoodItem(FoodItemDTO foodItem);

    List<FoodItemDTO> getFoodItemsByRetailerId(Integer retailerId);
    List<FoodItemDTO> getAllFoodItems();
    
    FoodItemDTO getFoodItemById(Integer foodItemId);
    void updateFoodItem(FoodItemDTO foodItem);
    void deleteFoodItem(Integer foodItemId);
}

