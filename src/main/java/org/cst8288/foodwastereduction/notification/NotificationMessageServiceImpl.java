/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDAO;
import org.cst8288.foodwastereduction.model.FoodItem;
import org.cst8288.foodwastereduction.model.Inventory;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
 */
public class NotificationMessageServiceImpl implements NotificationMessageService {
    private final FoodItemDAO foodItemDAO;
    private final UserDAO userDAO;
    
    public NotificationMessageServiceImpl(FoodItemDAO foodItemDAO, UserDAO userDAO) {
        this.foodItemDAO = foodItemDAO;
        this.userDAO = userDAO;
    }

    @Override
    public String createDonationMessage(Inventory item) {
        return createMessage(item, UserType.CHARITABLEORGANIZATION);
    }

    @Override
    public String createDiscountMessage(Inventory item) {
        return createMessage(item, UserType.CONSUMER);
    }

    private String createMessage(Inventory item, UserType userType){
        FoodItem foodItem;
        User retailer;
        try {
            foodItem = foodItemDAO.getById(item.getFoodItemId());
            retailer = userDAO.getById(item.getRetailerId());
            if (userType.equals(UserType.CONSUMER)){
                return "Discount available for " + foodItem.getName() + " at retailer " + retailer.getName() + 
                   ": Regular price: $" + item.getRegularPrice()  + ", Discount: " + item.getDiscountRate();        
            } else if (userType.equals(UserType.CHARITABLEORGANIZATION)){
                return "Donation available for " + foodItem.getName() + " at retailer " + retailer.getName();            
            } else {
                return "Unknown user type";
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificationMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "Error creating message";
        }
    }
}