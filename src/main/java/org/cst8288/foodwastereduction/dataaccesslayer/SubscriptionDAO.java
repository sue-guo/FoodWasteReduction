package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.Subscription;

/**
 * File: SubscriptionDAO.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-31
 * Modified: 2024-08-03 
 * Description: DAO of Subscription.
 */
public interface SubscriptionDAO {
    void addSubscription(Subscription subscription);
    void updateSubscription(Subscription subscription);
    List<Subscription> getSubscriptionsByRetailer(Integer retailerId);
    List<Subscription> getSubscriptionsByUser(Integer userId);
    void deleteSubscription(Integer userId, Integer retailerId);
    Subscription getSubscription(Integer consumerId, Integer retailerId);
}
