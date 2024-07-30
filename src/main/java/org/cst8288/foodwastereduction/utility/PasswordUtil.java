/* File: PasswordUtil.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This is a utility class for hashing and verifying passwords using BCrypt.
 *
 */
package org.cst8288.foodwastereduction.utility;
import org.mindrot.jbcrypt.BCrypt;
/**
 * Utility class for hashing and verifying passwords using BCrypt.
 * Provides methods to hash a plain password and check a plain password against a hashed password.
 * 
 * @author Hongxiu Guo
 */
public class PasswordUtil {
    /**
     * Hashes a plain text password using BCrypt.
     * 
     * @param plainPassword the plain text password to be hashed
     * @return the hashed password
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Verifies a plain text password against a hashed password using BCrypt.
     * 
     * @param plainPassword the plain text password to be verified
     * @param hashedPassword the hashed password to compare against
     * @return true if the password matches, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
