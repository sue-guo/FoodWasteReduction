/* File: UserDao.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This interface defines the methods for interacting with user data in the database.  
 *
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.User;

/**
 * This interface defines the methods for interacting with user data in the database. 
 * 
 * It provides methods for retrieving a user by their email address and adding a new user.
 * Implementations of this interface should provide the actual logic for these operations.
 * 
 * @author Hongxiu Guo
 */
public interface UserDao {
    /**
     * Retrieves a user from the database based on their email address.
     * 
     * @param email the email address of the user to retrieve
     * @return a User object representing the user with the specified email, or null if no such user exists
     */
    User getUserByEmail(String email);
    
    /**
     * Adds a new user to the database.
     * 
     * @param user the User object containing the information to be added to the database
     */
    void addUser(User user);
    
}
