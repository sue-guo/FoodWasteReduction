package org.cst8288.foodwastereduction.notification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.SubscriberDTO;

/**
 * File: SubscriptionServiceImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-27
 * Modified: 2024-08-03 
 * Description: Implementation of interface for Subscription service
 */
public class SubscriptionServiceImpl implements SubscriptionService {
    /**
     * subscriptionDAO
     */
    private final SubscriptionDAO subscriptionDAO;
    
    /**
     * userDao
     */
    private final UserDao userDao;

    /**
     * Constructor
     * @param subscriptionDAO
     * @param userDao 
     */
    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO, UserDao userDao) {
        this.subscriptionDAO = subscriptionDAO;
        this.userDao = userDao;
    }

    /**
     * Concrete method to add subscription
     * @param userId
     * @param retailerId
     * @param communicationPreference
     * @param foodPreferences 
     */
    @Override
    public void addSubscription(Integer userId, Integer retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences) {
        Subscription subscription = new Subscription(0, userId, retailerId, communicationPreference, foodPreferences, 
                                                     new Timestamp(System.currentTimeMillis()), 
                                                     new Timestamp(System.currentTimeMillis()));
        subscriptionDAO.addSubscription(subscription);
    }

    /**
     * Concrete method to update subscription
     * @param subscription 
     */
    @Override
    public void updateSubscription(Subscription subscription) {
        subscriptionDAO.updateSubscription(subscription);
    }

    /**
     * Concrete method to get subscription by userId
     * @param userId
     * @return 
     */
    @Override
    public List<SubscriberDTO> getSubscriptionsByUser(Integer userId) {
        String logMessage;
        
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByUser(userId);
        List<SubscriberDTO> subscriptionsDTO = new ArrayList<>();
        
        logMessage = "Found " + subscriptions.size() + " subscriptions for UserID: " + userId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        UserDao userDao = new UserDaoImpl();
        for (Subscription subscription : subscriptions) {
            User retailer = userDao.getUserById(subscription.getRetailerId());
            if (retailer != null) {
                SubscriberDTO subscriberDTO = new SubscriberDTO(retailer, subscription);
                subscriptionsDTO.add(subscriberDTO);

                logMessage = "Added subscription for RetailerID: " + retailer.getUserID() + ", RetailerName: " + retailer.getName();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
            } else {
                logMessage = "Retailer not found for RetailerID: " + subscription.getRetailerId() + ", subscription skipped";
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            }
        }
        return subscriptionsDTO;
    }

    /**
     * Concrete method to remove subscription
     * @param userId
     * @param retailerId 
     */
    @Override
    public void removeSubscription(Integer userId, Integer retailerId) {
        subscriptionDAO.deleteSubscription(userId, retailerId);
    }

    /**
     * Concrete method to determine is retailer has subscriptions
     * @param userId
     * @param retailerId
     * @return 
     */
    @Override
    public boolean hasSubscription(Integer userId, Integer retailerId) {
        List<Subscription> userSubscriptions = subscriptionDAO.getSubscriptionsByUser(userId);
        return userSubscriptions.stream().anyMatch(sub -> sub.getRetailerId() == retailerId);
    }
    
    /**
     * Concrete method to determine if a consumer is a subscriber
     * @param consumerId
     * @param retailerId
     * @return 
     */
    @Override
    public boolean isSubscribed(Integer consumerId, Integer retailerId) {
        return hasSubscription(consumerId, retailerId);
    }

    /**
     * Concrete method to determine if a consumer has interested in a food category
     * @param consumerId
     * @param retailerId
     * @param foodCategory
     * @return 
     */
    @Override
    public boolean isInterestedInCategory(Integer consumerId, Integer retailerId, CategoryEnum foodCategory) {
        String logMessage;
        Subscription subscription = subscriptionDAO.getSubscription(consumerId, retailerId);
        if (subscription == null) {
            logMessage = "No subscription found for ConsumerID: " + consumerId + ", RetailerID: " + retailerId;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
            return false;
        }
        
        Set<String> foodPreferences = subscription.getFoodPreferences();
        
        logMessage = "Food preferences for ConsumerID " + consumerId + ": " + foodPreferences;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        boolean isInterested = foodPreferences.contains(foodCategory.name().toUpperCase());

        logMessage = "Interest check result for category " + foodCategory + ": " + isInterested;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        return isInterested;
    }
    
    /**
     * This function is used to get Subscribers by retailerId
     * @param retailerId
     * @return 
     */
    @Override
    public List<SubscriberDTO> getSubscribersByRetailerId(Integer retailerId) {
        String logMessage;
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByRetailer(retailerId);
        List<SubscriberDTO> subscribers = new ArrayList<>();
        
        logMessage = "Found " + subscriptions.size() + " subscriptions for RetailerID: " + retailerId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
 
        for (Subscription subscription : subscriptions) {
            User user = userDao.getUserById(subscription.getUserId());
            if (user != null) {
                SubscriberDTO dto = new SubscriberDTO(user, subscription);
                subscribers.add(new SubscriberDTO(user, subscription));
                
                logMessage = "Added subscriber: UserID " + user.getUserID() + ", Name: " + dto.getUserName() + ", Type: " + dto.getUserType();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
            } else {
                logMessage = "User not found for UserID: " + subscription.getUserId() + ", subscription skipped";
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            }
        } 
        
        logMessage = "Returning " + subscribers.size() + " SubscriberDTOs for RetailerID: " + retailerId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);

        return subscribers;
    }
    
    /**
     * Concrete method to get subscription user by retailerId
     * @param retailerId
     * @return 
     */
    @Override
    public List<User> getUserByRetailerId(Integer retailerId) {
        String logMessage;
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByRetailer(retailerId);
        List<User> subscribers = new ArrayList<>();
        
        logMessage = "Found " + subscriptions.size() + " subscriptions for RetailerID: " + retailerId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);

        for (Subscription subscription : subscriptions) {
            User user = userDao.getUserById(subscription.getUserId());
            if (user != null) {
                subscribers.add(user);
                logMessage = "Added user: UserID " + user.getUserID() + ", Type: " + user.getUserType();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.DEBUG);
            } else {
                logMessage = "User not found for UserID: " + subscription.getUserId() + ", subscription skipped";
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            }
        } 
        
        logMessage = "Returning " + subscribers.size() + " Users for RetailerID: " + retailerId;
        LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);

        return subscribers;
    } 
    
    /**
     * Concrete method to update subscription
     * @param userId
     * @param retailerId
     * @param communicationPreference
     * @param foodPreferences 
     */
    @Override
    public void updateUserSubscriptions(int userId, int retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences) {
        String logMessage;
        // Get current user's all subscriptions
        Subscription existingSubscription = subscriptionDAO.getSubscription(userId, retailerId);
        
        if (existingSubscription != null) {
            // Update existed subscription
            existingSubscription.setCommunicationPreference(communicationPreference);
            existingSubscription.setFoodPreferences(foodPreferences);
            subscriptionDAO.updateSubscription(existingSubscription);
            logMessage = "Subscription updated successfully for UserID: " + userId + ", RetailerID: " + retailerId;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
        } else if (!foodPreferences.isEmpty()) {
            // New subscription
            Subscription newSubscription = new Subscription(userId, retailerId, communicationPreference, foodPreferences);
            subscriptionDAO.addSubscription(newSubscription);
            logMessage = "New subscription created successfully for UserID: " + userId + ", RetailerID: " + retailerId;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
        } else {
            logMessage = "No categories selected for new subscription. Skipping operation for UserID: " + userId + ", RetailerID: " + retailerId;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
        }
    }
}