/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
 */
public interface SubscriptionService {
    void addSubscription(Integer userId, Integer retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences);
    void updateSubscription(Subscription subscription);
    void removeSubscription(Integer userId, Integer retailerId);
    
//    List<Subscription> getSubscriptionsByRetailer(Integer retailerId);
    List<Subscription> getSubscriptionsByUser(Integer userId);
    List<SubscriberDTO> getSubscribersByRetailerId(Integer retailerId);
    List<User> getUserByRetailerId(Integer retailerId);

    boolean hasSubscription(Integer userId, Integer retailerId);
    boolean isSubscribed(Integer consumerId, Integer retailerId);
    boolean isInterestedInCategory(Integer consumerId, Integer retailerId, CategoryEnum foodCategory);
    
    void updateUserSubscriptions(int userId, int retailerId, CommunicationPreference communicationPreference, Set<String> foodPreferences);
}
