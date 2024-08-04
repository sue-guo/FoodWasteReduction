package org.cst8288.foodwastereduction.notification;

import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.dataaccesslayer.FoodItemDAO;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

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
        String logMessage;
        FoodItemDTO foodItem;
        User retailer;

        try {
            foodItem = foodItemDAO.getFoodItemById(item.getFoodItemId());
            if (foodItem == null) {
                logMessage = "Food item not found for FoodItemID: " + item.getFoodItemId();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
                return "Error: Food item not found";
            }

            retailer = userDao.getUserById(item.getRetailerId());
            if (retailer == null) {
                logMessage = "Retailer not found for RetailerID: " + item.getRetailerId();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
                return "Error: Retailer not found";
            }

            String message;
            switch (userType) {
                case CONSUMER:
                    message = "Discount available for " + foodItem.getName() + " at retailer " + retailer.getName() +
                            ": Regular price: $" + item.getRegularPrice() + ", Discount: " + item.getDiscountRate();
                    break;
                case CHARITABLE_ORGANIZATION:
                    message = "Donation available for " + foodItem.getName() + " at retailer " + retailer.getName();
                    break;
                default:
                    logMessage = "Unknown user type: " + userType;
                    LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
                    message = "Unknown user type";
                    break;
            }

            logMessage = "Message created successfully for InventoryID: " + item.getInventoryId();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

            return message;
        } catch (Exception e) {
            logMessage = "Error creating message for InventoryID: " + item.getInventoryId() + 
                         " | Error: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            return "Error creating message";
        }
    }
}