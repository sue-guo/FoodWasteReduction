
package org.cst8288.foodwastereduction.model;

import java.sql.Timestamp;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;

/**
 * dto of Subscriptions
 * @author Ryan Xu
 * Created on 2024-7-29
 */
public class Subscription {
    /**
     * The unique identifier for the subscription.
     */
    private Integer subscriptionId;

    /**
     * The unique identifier for the user associated with the subscription.
     */
    private Integer userId;

    /**
     * The unique identifier for the retailer associated with the subscription.
     */
    private Integer retailerId;

    /**
     * The user's communication preference for notifications.
     * Could be EMAIL, SMS, etc.
     */
    private CommunicationPreference communicationPreference;

    /**
     * The set of food preferences selected by the user.
     * Each preference is represented as a string.
     */
    private Set<String> foodPreferences;

    /**
     * The timestamp indicating when the subscription was created.
     */
    private Timestamp createdAt;

    /**
     * The timestamp indicating the last time the subscription was updated.
     */
    private Timestamp lastUpdated;


    /** 
     * Constructor of Subscription
     * @param subscriptionId
     * @param userId
     * @param retailerId
     * @param communicationPreference
     * @param foodPreferences
     * @param createdAt
     * @param lastUpdated 
     */
    public Subscription(Integer subscriptionId, Integer userId, Integer retailerId, CommunicationPreference communicationPreference, 
                        Set<String> foodPreferences, Timestamp createdAt, Timestamp lastUpdated) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.retailerId = retailerId;
        this.communicationPreference = communicationPreference;
        this.foodPreferences = foodPreferences;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }
    
    /**
     * Another constructor with timestamp
     * @param userId
     * @param retailerId
     * @param communicationPreference
     * @param foodPreferences 
     */
    public Subscription(Integer userId, Integer retailerId, CommunicationPreference communicationPreference, 
                        Set<String> foodPreferences) {
        this.userId = userId;
        this.retailerId = retailerId;
        this.communicationPreference = communicationPreference;
        this.foodPreferences = foodPreferences;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.lastUpdated = new Timestamp(System.currentTimeMillis());
    }
    
    
    /**
     * Gets the unique identifier for the subscription.
     * @return the subscriptionId.
     */
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * Sets the unique identifier for the subscription.
     * @param subscriptionId the subscriptionId to set.
     */
    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    /**
     * Gets the unique identifier for the user associated with the subscription.
     * @return the userId.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user associated with the subscription.
     * @param userId the userId to set.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the unique identifier for the retailer associated with the subscription.
     * @return the retailerId.
     */
    public Integer getRetailerId() {
        return retailerId;
    }

    /**
     * Sets the unique identifier for the retailer associated with the subscription.
     * @param retailerId the retailerId to set.
     */
    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    /**
     * Gets the user's communication preference for notifications.
     * @return the communicationPreference.
     */
    public CommunicationPreference getCommunicationPreference() {
        return communicationPreference;
    }

    /**
     * Sets the user's communication preference for notifications.
     * @param communicationPreference the communicationPreference to set.
     */
    public void setCommunicationPreference(CommunicationPreference communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    /**
     * Gets the set of food preferences selected by the user.
     * @return the foodPreferences.
     */
    public Set<String> getFoodPreferences() {
        return foodPreferences;
    }

    /**
     * Sets the set of food preferences selected by the user.
     * @param foodPreferences the foodPreferences to set.
     */
    public void setFoodPreferences(Set<String> foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

    /**
     * Gets the timestamp indicating when the subscription was created.
     * @return the createdAt timestamp.
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the timestamp indicating when the subscription was created.
     * @param createdAt the createdAt timestamp to set.
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the timestamp indicating the last time the subscription was updated.
     * @return the lastUpdated timestamp.
     */
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the timestamp indicating the last time the subscription was updated.
     * @param lastUpdated the lastUpdated timestamp to set.
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
