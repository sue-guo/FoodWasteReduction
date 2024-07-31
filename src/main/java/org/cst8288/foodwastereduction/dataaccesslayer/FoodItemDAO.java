/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;
import java.sql.SQLException;
import java.util.List;
import org.cst8288.foodwastereduction.model.FoodItemDTO;


/**
 *
 * @author ryany
 */
public interface FoodItemDAO {
    void addFoodItem(FoodItemDTO foodItem);
    List<FoodItemDTO> getFoodItemsByRetailerId(Integer retailerId);
    
    FoodItemDTO getFoodItemById(Integer foodItemId);
    List<FoodItemDTO> getAllFoodItems();
    void updateFoodItem(FoodItemDTO foodItem);
    void deleteFoodItem(Integer foodItemId);
}

