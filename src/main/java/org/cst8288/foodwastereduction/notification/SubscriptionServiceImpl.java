/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.cst8288.foodwastereduction.constants.FoodCategory;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;

/**
 *
 * @author ryany
 */
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDAO subscriptionDAO;
    private final UserDao userDao;

    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO, UserDao userDao) {
        this.subscriptionDAO = subscriptionDAO;
        this.userDao = userDao;
    }

    @Override
    public void addSubscription(int userId, int retailerId, String communicationPreference, Set<String> foodPreferences) {
        Subscription subscription = new Subscription(0, userId, retailerId, communicationPreference, foodPreferences, 
                                                     new Timestamp(System.currentTimeMillis()), 
                                                     new Timestamp(System.currentTimeMillis()));
        subscriptionDAO.saveSubscription(subscription);
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        subscriptionDAO.updateSubscription(subscription);
    }

    @Override
    public List<Subscription> getSubscriptionsByRetailer(int retailerId) {
        return subscriptionDAO.getSubscriptionsByRetailer(retailerId);
    }

    @Override
    public List<Subscription> getSubscriptionsByUser(int userId) {
        return subscriptionDAO.getSubscriptionsByUser(userId);
    }

    @Override
    public void removeSubscription(int userId, int retailerId) {
        subscriptionDAO.deleteSubscription(userId, retailerId);
    }

    @Override
    public boolean hasSubscription(int userId, int retailerId) {
        List<Subscription> userSubscriptions = subscriptionDAO.getSubscriptionsByUser(userId);
        return userSubscriptions.stream().anyMatch(sub -> sub.getRetailerId() == retailerId);
    }
    
    @Override
    public boolean isSubscribed(int consumerId, int retailerId) {
        return hasSubscription(consumerId, retailerId);
    }

    @Override
    public boolean isInterestedInCategory(int consumerId, int retailerId, FoodCategory foodCategory) {
        Subscription subscription = subscriptionDAO.getSubscription(consumerId, retailerId);
        if (subscription == null) {
            return false;
        }
        return subscription.getFoodPreferences().contains(foodCategory);
    }

    /**
     * Used to get Food Preferences of a consumer
     * @param consumerId
     * @return 
     */
    private Set<String> getFoodPreferences(int consumerId) {
        List<Subscription> userSubscriptions = subscriptionDAO.getSubscriptionsByUser(consumerId);
        return userSubscriptions.stream()
            .flatMap(sub -> sub.getFoodPreferences().stream())
            .collect(Collectors.toSet());
    }
    
    /**
     * This function is used to get Subscribers by retailerId
     * @param retailerId
     * @return 
     */
    @Override
    public List<User> getSubscribersByRetailerId(int retailerId) {
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByRetailer(retailerId);
        List<User> subscribers = new ArrayList<>();
        
        for (Subscription subscription : subscriptions) {
            User user = userDao.getUserById(subscription.getUserId());
            if (user != null) {
                subscribers.add(user);
            }
        } 
        return subscribers;
    }    
    
}