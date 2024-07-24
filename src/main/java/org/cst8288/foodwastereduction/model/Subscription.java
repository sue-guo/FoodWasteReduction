/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import java.sql.Timestamp;
import java.util.Set;

/**
 * dto of Subscriptions
 * @author ryany
 */
public class Subscription {
    private int subscriptionId;
    private int userId;
    private int retailerId;
    private String communicationPreference;
    private Set<String> foodPreferences;
    private Timestamp createdAt;
    private Timestamp lastUpdated;

    // Constructor
    public Subscription(int subscriptionId, int userId, int retailerId, String communicationPreference, 
                        Set<String> foodPreferences, Timestamp createdAt, Timestamp lastUpdated) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.retailerId = retailerId;
        this.communicationPreference = communicationPreference;
        this.foodPreferences = foodPreferences;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters
    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public Set<String> getFoodPreferences() {
        return foodPreferences;
    }

    public void setFoodPreferences(Set<String> foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionId=" + subscriptionId +
                ", userId=" + userId +
                ", retailerId=" + retailerId +
                ", communicationPreference='" + communicationPreference + '\'' +
                ", foodPreferences=" + foodPreferences +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
