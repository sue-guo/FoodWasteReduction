/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.Subscription;

/**
 * DAO of Subscription
 * @author ryany
 */
public interface SubscriptionDAO {
    void saveSubscription(Subscription subscription);
    void updateSubscription(Subscription subscription);
    List<Subscription> getSubscriptionsByRetailer(int retailerId);
    List<Subscription> getSubscriptionsByUser(int userId);
    void deleteSubscription(int userId, int retailerId);
}
