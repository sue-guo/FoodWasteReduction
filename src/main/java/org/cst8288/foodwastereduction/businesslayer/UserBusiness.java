/* File: UserBusiness.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This class is Business logic class for managing user-related operations.
 *
 */
package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.utility.PasswordUtil;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;

/**
 * Business logic class for managing user-related operations.
 * 
 * This class acts as an intermediary between the data access layer (UserDao) 
 * and other parts of the application. It provides methods for retrieving user 
 * information, adding new users, and authenticating user credentials.
 * 
 * @author Hongxiu Guo
 */
public class UserBusiness {
    
    private UserDao userDao = null;
    /**
     * Constructs a UserBusiness instance and initializes the UserDao.
     * 
     * The constructor creates a new instance of UserDaoImpl, which is used
     * to perform data access operations.
     */
    public UserBusiness() {
        userDao = new UserDaoImpl();
    }
    
    /**
     * Retrieves a user by their email address.
     * 
     * This method calls the corresponding method in UserDao to get user details
     * based on the provided email address.
     * 
     * @param email the email address of the user to retrieve
     * @return a User object representing the user with the specified email, 
     *         or null if no user is found
     */
    public User getUserByEmail(String email) {
        
       User user =  userDao.getUserByEmail(email);
       return user;
        
    }
    /**
     * Adds a new user to the system.
     * 
     * This method delegates the task of adding a user to the UserDao implementation.
     * 
     * @param user the User object containing information to be added
     */
    public void addUser(User user) {
        userDao.addUser(user);
    }
    
    /**
     * Authenticates a user based on their email address and password.
     * 
     * This method retrieves the user by email and checks if the provided password
     * matches the stored password using the PasswordUtil class.
     * 
     * @param email the email address of the user to authenticate
     * @param password the password to verify
     * @return true if the email exists and the password is correct, false otherwise
     */
    public boolean authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null) {
            return PasswordUtil.checkPassword(password, user.getPassword());
        }
        return false;
    }
    
}
