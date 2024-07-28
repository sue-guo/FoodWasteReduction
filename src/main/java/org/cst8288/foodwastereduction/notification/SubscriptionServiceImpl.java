/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.notification;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.model.Subscription;

/**
 *
 * @author ryany
 */
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDAO subscriptionDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
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
}