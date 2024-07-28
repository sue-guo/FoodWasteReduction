/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.businesslayer;

import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAOImpl;
import org.cst8288.foodwastereduction.model.FoodItem;

/**
 *
 * @author WANG JIAYUN
 */
public class FoodItemBusiness {
    
    private FoodItemDAO foodItemDAO = null;
    private List<FoodItem> foodItems = new ArrayList<>();
    private FoodItem foodItem = new FoodItem();
    
    public FoodItemBusiness() {
        foodItemDAO = new FoodItemDAOImpl();
    }

    public List<FoodItem> getFoodItemsByRetailerID(int RetailerID) {
      
        return foodItems;
        
    }

//    public FoodItem getFoodItemByID(int FoodItemID) {
//        
//       return foodItem;
//        
//    }

    
    public void addFoodItem (FoodItem foodItem) {
        // userDao.addUser(user);
    }
    
    
}
