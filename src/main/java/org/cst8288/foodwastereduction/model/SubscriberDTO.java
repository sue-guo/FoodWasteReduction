/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;

/**
 *
 * @author ryany
 */
public class SubscriberDTO {
    private Integer userID;
    private String userName;
    private UserType userType;
    private CommunicationPreference communicationPreference;
    private Set<String> foodPreferences;
    private Timestamp createdAt;
    private Timestamp lastUpdated;

    /**
     * Construction
     * @param user
     * @param subscription 
     */
    public SubscriberDTO(User user, Subscription subscription) {
        this.userID = user.getUserID();
        this.userName = user.getName();
        this.userType = user.getUserType();
        this.communicationPreference = subscription.getCommunicationPreference();
        this.foodPreferences = subscription.getFoodPreferences();
        this.createdAt = subscription.getCreatedAt();
        this.lastUpdated = subscription.getLastUpdated();
    }
    
    public Integer getUserID() { return userID; }
    public String getUserName() { return userName; }
    public UserType getUserType() { return userType; }
    public CommunicationPreference getCommunicationPreference() { return communicationPreference; }
    public Set<String> getFoodPreferences() { return foodPreferences; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getLastUpdated() { return lastUpdated; }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setCommunicationPreference(CommunicationPreference communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public void setFoodPreferences(Set<String> foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    
}
