/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.utility.DatetimeUtil;

/**
 *
 * @author ryany
 */
public class UserDaoImpl implements UserDao{
    
    /**
     * Gets user by user email
     * 
     * @param email
     * @return user with specific email or null
     */
   @Override
    public User getUserByEmail(String email) {
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = DataSource.getConnection();
            String sql = " select * from users where email = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("PasswordHash"));
                user.setUserType(UserType.valueOf(rs.getString("UserType").toUpperCase()));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setCity(rs.getString("City"));
                user.setCreateAt(DatetimeUtil.formatTimestampAsString(rs.getTimestamp("CreatedAt"), "YYYY-MM-DD HH:ss") );
            }
             
        } catch (SQLException ex) {
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getUserByEmail: "+ex.getMessage(), UserDaoImpl.class.getName() , LogLevel.ERROR);
        }
       return user;
    }

    /**
     * Add user information into database
     * @param user  the user to be inserted into the database
     */
    @Override
    public void addUser(User user) {
        
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            con = DataSource.getConnection();
            //Prepare SQL statement
            String sql = " INSERT INTO Users (Name, Email, PasswordHash, UserType, PhoneNumber, Address, City, CreatedAt) VALUES (?,?,?,?,?,?,?, now())";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getUserType().toString());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getCity());
            //Execute SQL statement
            pstmt.executeUpdate();
            LMSLogger.getInstance().saveLogInformation("Insert an user into database successfully, userEmail="+user.getEmail(), UserDaoImpl.class.getName() , LogLevel.INFO);
            
        } catch (SQLException ex) {
           LMSLogger.getInstance().saveLogInformation("SQLException occur at addUser: "+ex.getMessage(), UserDaoImpl.class.getName() , LogLevel.ERROR);
        }
    }
    
    
    @Override
    public User getUserById(int userId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = DataSource.getConnection();
            String sql = "SELECT * FROM Users WHERE UserID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setName(rs.getString("Name"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("PasswordHash"));
                user.setUserType(UserType.valueOf(rs.getString("UserType").toUpperCase()));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setCity(rs.getString("City"));
                user.setCreateAt(DatetimeUtil.formatTimestampAsString(rs.getTimestamp("CreatedAt"), "YYYY-MM-DD HH:mm"));
            }
             
        } catch (SQLException ex) {
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getUserById: "+ex.getMessage(), UserDaoImpl.class.getName() , LogLevel.ERROR);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                LMSLogger.getInstance().saveLogInformation("SQLException occur at getUserById: "+ex.getMessage(), UserDaoImpl.class.getName() , LogLevel.ERROR);
            }
        }
        return user;
    }
    
    
}