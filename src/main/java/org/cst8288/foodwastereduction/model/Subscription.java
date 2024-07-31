/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import java.sql.Timestamp;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;

/**
 * dto of Subscriptions
 * @author ryany
 */
public class Subscription {
    private Integer subscriptionId;
    private Integer userId;
    private Integer retailerId;
    private CommunicationPreference communicationPreference;
    private Set<String> foodPreferences;
    private Timestamp createdAt;
    private Timestamp lastUpdated;

    // Constructor
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

    // Getters and Setters
    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Integer retailerId) {
        this.retailerId = retailerId;
    }

    public CommunicationPreference getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(CommunicationPreference communicationPreference) {
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
