package org.cst8288.foodwastereduction.notification;

import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 * File: FoodItemServiceImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description:  This class is implementation of interface FoodItemService
 */
public class FoodItemServiceImpl implements FoodItemService {
    
    /**
     * foodItemDAO
     */
    private final FoodItemDAO foodItemDAO;

    /**
     * default constructor which needed to initialize the attribute
     */
    public FoodItemServiceImpl() {
        this.foodItemDAO = new FoodItemDAOImpl();
    }

    /**
     * override the method in interface to get food category from foodItem
     * @param foodItemId
     * @return
     * @throws NoSuchElementException 
     */
    @Override
    public CategoryEnum getFoodCategory(Integer foodItemId) throws NoSuchElementException {
        FoodItemDTO foodItem = null;
        foodItem = foodItemDAO.getFoodItemById(foodItemId);
        if (foodItem == null) {
            throw new NoSuchElementException("Food item not found for id: " + foodItemId);
        }
        return foodItem.getCategory();
    }
}