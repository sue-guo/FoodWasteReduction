/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
 */
public interface SubscriptionService {
    void addSubscription(int userId, int retailerId, String communicationPreference, Set<String> foodPreferences);
    void updateSubscription(Subscription subscription);
    void removeSubscription(int userId, int retailerId);
    
//    List<Subscription> getSubscriptionsByRetailer(int retailerId);
    List<Subscription> getSubscriptionsByUser(int userId);
    List<SubscriberDTO> getSubscribersByRetailerId(int retailerId);
    List<User> getUserByRetailerId(int retailerId);

    boolean hasSubscription(int userId, int retailerId);
    boolean isSubscribed(int consumerId, int retailerId);
    boolean isInterestedInCategory(int consumerId, int retailerId, CategoryEnum foodCategory);
}
