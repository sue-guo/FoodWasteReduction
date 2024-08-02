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
 * This dto is a 'temporary' class include attribute from User and Subscription
 * Which is helpful to for subscription query and notification sending
 * @author Ryan Xu
 * Created on 2024-07-31
 */
public class SubscriberDTO {
    /**
     * userID from User
     */
    private Integer userID;
    
    /**
     * userName from User
     */
    private String userName;
    
    /**
     * userType from User
     */
    private UserType userType;
    
    /**
     * CommunicationPrefrence from subscription
     */
    private CommunicationPreference communicationPreference;
    
    /**
     * food preferences from subscription
     */
    private Set<String> foodPreferences;
    
    /**
     * create timestamp from subscription
     */
    private Timestamp createdAt;
    
    /**
     * update timestamp from subscription
     */
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
    
    /**
     * getter of userID
     * @return 
     */
    public Integer getUserID() { return userID; }
    
    /**
     * getter of UsrType
     * @return 
     */
    public String getUserName() { return userName; }
    
    /**
     * getter of UsrType
     * @return 
     */
    public UserType getUserType() { return userType; }
    
    /**
     * getter of CommunicationPreference
     * @return 
     */    
    public CommunicationPreference getCommunicationPreference() { return communicationPreference; }
    
    /**
     * getter of foodPreferences
     * @return 
     */    
    public Set<String> getFoodPreferences() { return foodPreferences; }
    
    /**
     * getter of createdAt
     * @return 
     */    
    public Timestamp getCreatedAt() { return createdAt; }
    
    /**
     * getter of lastUpdated
     * @return 
     */    
    public Timestamp getLastUpdated() { return lastUpdated; }

    /**
     * setter of userID
     * @param userID
     */    
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * setter of userName
     * @param userName
     */    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * setter of userType
     * @param userType
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    /**
     * setter of communicationPreference
     * @param communicationPreference
     */
    public void setCommunicationPreference(CommunicationPreference communicationPreference) {
        this.communicationPreference = communicationPreference;
    }
    
    /**
     * setter of foodPreferences
     * @param foodPreferences
     */
    public void setFoodPreferences(Set<String> foodPreferences) {
        this.foodPreferences = foodPreferences;
    }

   /**
    * Setter of createAt
    * @param createdAt 
    */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * setter of lastUpdated
     * @param lastUpdated
     */
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
