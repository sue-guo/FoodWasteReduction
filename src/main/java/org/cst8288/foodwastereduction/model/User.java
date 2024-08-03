/* File: User.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This class represents a user entity in the Food Waste Reduction platform.
 *
 */
package org.cst8288.foodwastereduction.model;

/**
 * Represents a user entity in the Food Waste Reduction platform.
 * This class contains information about the user such as their ID, name, email,
 * password, user type, phone number, address, city, and the date the account was created.
 * 
 * Each field has its getter and setter methods to access and modify the user data.
 * 
 * @author Hongxiu Guo
 */
public class User {
    /**
     * The unique identifier for the user.
     */
    private Integer userID;
    /**
     * The name of the user.
     */
    private String name;
    /**
     * The email address of the user.
     */
    private String email;
    /**
     * The password of the user.
     */
    private String password;
    /**
     * The type of user (e.g., RETAILER, CONSUMER, CHARITABLE_ORGANIZATION).
     */
    private UserType userType;
    /**
     * The phone number of the user.
     */
    private String phoneNumber;
    /**
     * The address of the user.
     */
    private String address;
    /**
     * The city where the user is located.
     */
    private String city;
    /**
     * The date and time when the user account was created.
     */
    private String createAt;
    
    public User(){};
    
    /**
     * Constructor
     * @param userId
     * @param name
     * @param email
     * @param password
     * @param userType
     * @param phoneNumber
     * @param address
     * @param city 
     */
    public User(Integer userId, String name, String email, String password, UserType userType, String phoneNumber, String address, String city) {
        this.userID = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
    }
    /**
     * Gets the user ID.
     * @return the user ID.
     */
    public Integer getUserID() {
        return userID;
    }
    /**
     * Sets the user ID.
     * 
     * @param userID the new user ID.
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    /**
     * Gets the user's name.
     * 
     * @return the user's name.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the user's name.
     * 
     * @param name the new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the user's email address.
     * 
     * @return the user's email address.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the user's email address.
     * 
     * @param email the new email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gets the user's password.
     * 
     * @return the user's password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the user's password.
     * 
     * @param password the new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Gets the user's type.
     * 
     * @return the user's type.
     */
    public UserType getUserType() {
        return userType;
    }
    /**
     * Sets the user's type.
     * 
     * @param userType the new type of the user.
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    /**
     * Gets the user's phone number.
     * 
     * @return the user's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * Sets the user's phone number.
     * 
     * @param phoneNumber the new phone number of the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * Gets the user's address.
     * 
     * @return the user's address.
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the user's address.
     * 
     * @param address the new address of the user.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Gets the city where the user is located.
     * 
     * @return the city where the user is located.
     */
    public String getCity() {
        return city;
    }
    /**
     * Sets the city where the user is located.
     * 
     * @param city the new city where the user is located.
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Gets the date and time when the user account was created.
     * 
     * @return the date and time when the user account was created.
     */
    public String getCreateAt() {
        return createAt;
    }
    /**
     * Sets the date and time when the user account was created.
     * 
     * @param createAt the new date and time when the user account was created.
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
    
}
