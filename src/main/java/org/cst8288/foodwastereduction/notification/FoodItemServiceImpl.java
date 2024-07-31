/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;


import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 *
 * @author ryany
 */
public class FoodItemServiceImpl implements FoodItemService {
    private final FoodItemDAO foodItemDAO;

    public FoodItemServiceImpl() {
        this.foodItemDAO = new FoodItemDAOImpl();
    }

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