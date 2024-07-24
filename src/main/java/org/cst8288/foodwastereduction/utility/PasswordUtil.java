
package org.cst8288.foodwastereduction.utility;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author Hongxiu Guo
 */
public class PasswordUtil {
    // Hash the password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify the password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
