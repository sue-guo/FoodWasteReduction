package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.model.UserType;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;

/**
 * File: NotificationMessageServiceImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-28
 * Modified: 2024-08-03 
 * Description: Implementation of Notification message service
 */
public class NotificationMessageServiceImpl implements NotificationMessageService {
    /**
     * attribute foodItemDAO
     */
    private final FoodItemDAO foodItemDAO;
    
    /**
     * attribute userDao
     */
    private final UserDao userDao;
    
    /**
     * constructor
     * @param foodItemDAO
     * @param userDao 
     */
    public NotificationMessageServiceImpl(FoodItemDAO foodItemDAO, UserDao userDao) {
        this.foodItemDAO = foodItemDAO;
        this.userDao = userDao;
    }

    /**
     * method to create message for donation item
     * @param item
     * @return 
     */
    @Override
    public String createDonationMessage(InventoryDTO item) {
        return createMessage(item, UserType.CHARITABLE_ORGANIZATION);
    }

    /**
     * method to create message for discount sale
     * @param item
     * @return 
     */
    @Override
    public String createDiscountMessage(InventoryDTO item) {
        return createMessage(item, UserType.CONSUMER);
    }

    /**
     * private message to create detail message by message type
     * @param item
     * @param userType
     * @return 
     */
    private String createMessage(InventoryDTO item, UserType userType){
        FoodItemDTO foodItem;
        User retailer;
        foodItem = foodItemDAO.getFoodItemById(item.getFoodItemId());
        retailer = userDao.getUserById(item.getRetailerId());
        if (userType.equals(UserType.CONSUMER)){
            return "Discount available for " + foodItem.getName() + " at retailer " + retailer.getName() +
                    ": Regular price: $" + item.getRegularPrice()  + ", Discount: " + item.getDiscountRate();
        } else if (userType.equals(UserType.CHARITABLE_ORGANIZATION)){
            return "Donation available for " + foodItem.getName() + " at retailer " + retailer.getName();
        } else {
            return "Unknown user type";
        }
    }
}