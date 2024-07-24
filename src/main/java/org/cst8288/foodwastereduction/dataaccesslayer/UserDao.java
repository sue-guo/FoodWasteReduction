/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.User;

/**
 *
 * @author Hongxiu Guo
 */
public interface UserDao {

    User getUserByEmail(String email);
    
    void addUser(User user);
    
}
