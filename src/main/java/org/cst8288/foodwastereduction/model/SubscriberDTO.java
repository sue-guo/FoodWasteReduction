/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

/**
 *
 * @author ryany
 */
public class SubscriberDTO {
    private int userId;
    private UserType userType;
    private String communicationPreference;
    private Set<String> foodPreferences;
    private Timestamp createdAt;
    private Timestamp lastUpdated;

    /**
     * Construction
     * @param user
     * @param subscription 
     */
    public SubscriberDTO(User user, Subscription subscription) {
        this.userId = user.getUserID();
        this.userType = user.getUserType();
        this.communicationPreference = subscription.getCommunicationPreference();
        this.foodPreferences = subscription.getFoodPreferences();
        this.createdAt = subscription.getCreatedAt();
        this.lastUpdated = subscription.getLastUpdated();
    }
}
