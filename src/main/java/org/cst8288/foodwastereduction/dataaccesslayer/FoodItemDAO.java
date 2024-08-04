package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 * Data Access Object (DAO) interface for FoodItem.
 * This interface defines the standard operations to be performed on a FoodItem model object.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WNAG JIAYUN & YAO YI & Ryan Xu
 */
public interface FoodItemDAO {

    /**
     * Adds a new food item to the database.
     * 
     * @param foodItem the FoodItemDTO object to be added
     */
    void addFoodItem(FoodItemDTO foodItem);

    /**
     * Retrieves a list of food items for a specific retailer.
     * 
     * @param retailerId the ID of the retailer
     * @return a list of FoodItemDTO objects
     */
    List<FoodItemDTO> getFoodItemsByRetailerId(int retailerId);

    /**
     * Retrieves a list of all food items in the database.
     * 
     * @return a list of FoodItemDTO objects
     */
    List<FoodItemDTO> getAllFoodItems();

    /**
     * Retrieves a food item by its ID.
     * 
     * @param foodItemId the ID of the food item
     * @return the FoodItemDTO object
     */
    FoodItemDTO getFoodItemById(int foodItemId);

    /**
     * Updates an existing food item in the database.
     * 
     * @param foodItem the FoodItemDTO object with updated information
     */
    void updateFoodItem(FoodItemDTO foodItem);

}