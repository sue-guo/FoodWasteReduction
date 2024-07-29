/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cst8288.foodwastereduction.constants.FoodCategory;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.model.FoodItem;

/**
 *
 * @author ryany
 */
public class FoodItemServiceImpl implements FoodItemService {
    private final FoodItemDAO foodItemDAO;

    public FoodItemServiceImpl(FoodItemDAO foodItemDAO) {
        this.foodItemDAO = foodItemDAO;
    }

    @Override
    public FoodCategory getFoodCategory(int foodItemId) throws NoSuchElementException {
        FoodItem foodItem = null;
        try {
            foodItem = foodItemDAO.getById(foodItemId);
        } catch (SQLException ex) {
            Logger.getLogger(FoodItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (foodItem == null) {
            throw new NoSuchElementException("Food item not found for id: " + foodItemId);
        }
        return foodItem.getCategory();
    }
}