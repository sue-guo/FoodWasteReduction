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
public interface UserDAO {

    User getUserByEmail(String email);
    User getById(int userId);
    void addUser(User user);
    
}
