package org.cst8288.foodwastereduction.businesslayer;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 * Business layer class for managing food items.
 * This class interacts with the data access layer to perform CRUD operations on food items.
 * 
 * @see org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO
 * @see org.cst8288.foodwastereduction.model.FoodItemDTO
 * @see org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN
 */
public class FoodItemBusiness {

    private FoodItemDAO foodItemDAO;
    private List<FoodItemDTO> foodItems;

    /**
     * Default constructor that initializes the FoodItemDAO implementation and the foodItems list.
     */
    public FoodItemBusiness() {
        this.foodItemDAO = new FoodItemDAOImpl();
        this.foodItems = new ArrayList<>();
    }

    /**
     * Retrieves a list of food items associated with a specific retailer ID.
     * 
     * @param retailerID the ID of the retailer whose food items are to be retrieved
     * @return a list of FoodItemDTO objects associated with the specified retailer ID
     */
    public List<FoodItemDTO> getFoodItemsByRetailerID(int retailerID) {
        return foodItemDAO.getFoodItemsByRetailerId(retailerID);
    }

    /**
     * Adds a new food item to the database.
     * 
     * @param foodItem the FoodItemDTO object representing the food item to be added
     */
    public void addFoodItem(FoodItemDTO foodItem) {
        foodItemDAO.addFoodItem(foodItem);
    }

    /**
     * Retrieves a list of all food items from the database.
     * 
     * @return a list of all FoodItemDTO objects
     */
    public List<FoodItemDTO> getAllFoodItems() {
        return foodItemDAO.getAllFoodItems();
    }

    /**
     * Retrieves a food item by its ID.
     * 
     * @param foodItemID the ID of the food item to be retrieved
     * @return the FoodItemDTO object representing the food item with the specified ID
     */
    public FoodItemDTO getFoodItemById(int foodItemID) {
        return foodItemDAO.getFoodItemById(foodItemID);
    }
}