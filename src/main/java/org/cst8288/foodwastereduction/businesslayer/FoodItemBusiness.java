/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.businesslayer;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 *
 * @author WANG JIAYUN
 */

public class FoodItemBusiness {

    private FoodItemDAO foodItemDAO;
    private List<FoodItemDTO> foodItems;


    public FoodItemBusiness() {
        this.foodItemDAO = new FoodItemDAOImpl();
        this.foodItems = new ArrayList<>();
    }

    public List<FoodItemDTO> getFoodItemsByRetailerID(int retailerID) {
        return foodItemDAO.getFoodItemsByRetailerId(retailerID);
    }

    public void addFoodItem(FoodItemDTO foodItem) {
        foodItemDAO.addFoodItem(foodItem);
    }
}