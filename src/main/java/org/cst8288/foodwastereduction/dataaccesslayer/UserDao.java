/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.util.List;
import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author ryany
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
    
    User getUserById(int userId);
    
}