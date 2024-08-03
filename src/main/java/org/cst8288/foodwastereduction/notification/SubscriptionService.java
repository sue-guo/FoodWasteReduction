package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;

/**
 * File: SubscriptionService.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-27
 * Modified: 2024-08-03 
 * Description: Interface for Subscription service
 */
public interface SubscriptionService {
    /**
     * Abstract method to add subscription
     * @param userId
     * @param retailerId
     * @param communicationPreference
     * @param foodPreferences 
     */
    void addSubscription(Integer userId, Integer retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences);
    
    /**
     * Abstract method to update subscription
     * @param subscription 
     */
    void updateSubscription(Subscription subscription);
    
    /**
     * Abstract method to remove subscription
    */
    void removeSubscription(Integer userId, Integer retailerId);
    
//    List<Subscription> getSubscriptionsByRetailer(Integer retailerId);
    
    /**
     * Abstract method to get subscribers by userId, return type is List of SubscriberDTO
     * @param userId
     * @return List of SubscriberDTO
     */
    List<SubscriberDTO> getSubscriptionsByUser(Integer userId);
    
    /**
     * Abstract method to get subscribers by retailerId, return type is List of SubscriberDTO
     * @param retailerId
     * @return 
     */
    List<SubscriberDTO> getSubscribersByRetailerId(Integer retailerId);
    
    /**
     * Abstract method to get user by retailerId
     * @param retailerId
     * @return 
     */
    List<User> getUserByRetailerId(Integer retailerId);

    /**
     * Determine if the consumer/charitable_organization is subscriber
     * @param userId
     * @param retailerId
     * @return 
     */
    boolean hasSubscription(Integer userId, Integer retailerId);
    
    /**
     * Determine if the retailer is subscribed by consumer/charatable_organization
     * @param consumerId
     * @param retailerId
     * @return 
     */
    boolean isSubscribed(Integer consumerId, Integer retailerId);
    
    /**
     * Determine if consumer/charatable_organization is Interested in some food Category
     * @param consumerId
     * @param retailerId
     * @param foodCategory
     * @return 
     */
    boolean isInterestedInCategory(Integer consumerId, Integer retailerId, CategoryEnum foodCategory);
    
    /**
     * Update user subscription
     * @param userId
     * @param retailerId
     * @param communicationPreference
     * @param foodPreferences 
     */
    void updateUserSubscriptions(int userId, int retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences);
}
