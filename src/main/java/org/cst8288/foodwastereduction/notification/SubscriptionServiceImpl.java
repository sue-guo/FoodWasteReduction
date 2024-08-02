/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.model.SubscriberDTO;

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
    public void addSubscription(Integer userId, Integer retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences) {
        Subscription subscription = new Subscription(0, userId, retailerId, communicationPreference, foodPreferences, 
                                                     new Timestamp(System.currentTimeMillis()), 
                                                     new Timestamp(System.currentTimeMillis()));
        subscriptionDAO.addSubscription(subscription);
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        subscriptionDAO.updateSubscription(subscription);
    }

//    @Override
//    public List<Subscription> getSubscriptionsByRetailer(Integer retailerId) {
//        return subscriptionDAO.getSubscriptionsByRetailer(retailerId);
//    }

    @Override
    public List<SubscriberDTO> getSubscriptionsByUser(Integer userId) {
        
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByUser(userId);
        List<SubscriberDTO> subscriptionsDTO = new ArrayList<>();
        User user = new User();
        for (Subscription subscription : subscriptions) {
            UserDao userDao = new UserDaoImpl();
            user = userDao.getUserById(subscription.getRetailerId());
            SubscriberDTO subscriberDTO = new SubscriberDTO(user, subscription);
            subscriptionsDTO.add(subscriberDTO);
        }
        return subscriptionsDTO;
    }

    @Override
    public void removeSubscription(Integer userId, Integer retailerId) {
        subscriptionDAO.deleteSubscription(userId, retailerId);
    }

    @Override
    public boolean hasSubscription(Integer userId, Integer retailerId) {
        List<Subscription> userSubscriptions = subscriptionDAO.getSubscriptionsByUser(userId);
        return userSubscriptions.stream().anyMatch(sub -> sub.getRetailerId() == retailerId);
    }
    
    @Override
    public boolean isSubscribed(Integer consumerId, Integer retailerId) {
        return hasSubscription(consumerId, retailerId);
    }

    @Override
    public boolean isInterestedInCategory(Integer consumerId, Integer retailerId, CategoryEnum foodCategory) {
        Subscription subscription = subscriptionDAO.getSubscription(consumerId, retailerId);
        if (subscription == null) {
            return false;
        }
        // Debugging output
        System.out.println("Checking food preferences: " + subscription.getFoodPreferences());
        System.out.println("Looking for category: " + foodCategory.name());
        return subscription.getFoodPreferences().contains(foodCategory.name().toUpperCase());
    }

    /**
     * Used to get Food Preferences of a consumer
     * @param consumerId
     * @return 
     */
    private Set<String> getFoodPreferences(Integer consumerId) {
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
    public List<SubscriberDTO> getSubscribersByRetailerId(Integer retailerId) {
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByRetailer(retailerId);
        List<SubscriberDTO> subscribers = new ArrayList<>();
//        System.out.println("Found " + subscriptions.size() + " subscriptions for retailer " + retailerId);
        
        for (Subscription subscription : subscriptions) {
            User user = userDao.getUserById(subscription.getUserId());
            if (user != null) {
                SubscriberDTO dto = new SubscriberDTO(user, subscription);
                subscribers.add(new SubscriberDTO(user, subscription));
//                System.out.println("Added subscriber: " + dto.getUserName() + ", Type: " + dto.getUserType());
            }
        } 
        
//        System.out.println("Returning " + subscribers.size() + " SubscriberDTOs");
        return subscribers;
    }
    
        @Override
    public List<User> getUserByRetailerId(Integer retailerId) {
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
    
    @Override
    public void updateUserSubscriptions(int userId, int retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences) {
        // Get current user's all subscriptions
        Subscription existingSubscription = subscriptionDAO.getSubscription(userId, retailerId);
        
        if (existingSubscription != null) {
            // Update existed subscription
            existingSubscription.setCommunicationPreference(communicationPreference);
            existingSubscription.setFoodPreferences(foodPreferences);
            subscriptionDAO.updateSubscription(existingSubscription);
        } else if (!foodPreferences.isEmpty()) {
            // New subscription
            Subscription newSubscription = new Subscription(userId, retailerId, communicationPreference, foodPreferences);
            subscriptionDAO.addSubscription(newSubscription);
        } else {
            // If no selection, there would be no operation
            System.out.println("No categories selected for new subscription. Skipping.");
        }
    }
}