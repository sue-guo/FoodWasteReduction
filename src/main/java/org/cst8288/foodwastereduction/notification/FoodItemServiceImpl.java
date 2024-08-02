/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;


import java.util.NoSuchElementException;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 * This class is implementation of interface FoodItemService
 * @author Ryan Xu
 * Created on 2024-07-27
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