
package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.utility.PasswordUtil;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;

/**
 *
 * @author Hongxiu Guo
 */
public class UserBusiness {
    
    private UserDao userDao = null;
    
    public UserBusiness() {
        userDao = new UserDaoImpl();
    }

    public User getUserByEmail(String email) {
        
       User user =  userDao.getUserByEmail(email);
       return user;
        
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }
    
    //This method to verify user credentials
    public boolean authenticateUser(String email, String password) {
        User user = getUserByEmail(email);
        if (user != null) {
            return PasswordUtil.checkPassword(password, user.getPassword());
        }
        return false;
    }
    
}
