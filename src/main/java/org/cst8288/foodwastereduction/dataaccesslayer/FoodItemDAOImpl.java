/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 *
 * @author WANG JIAYUN
 */
public class FoodItemDAOImpl implements FoodItemDAO {
    

    @Override
    public void addFoodItem(FoodItemDTO foodItem) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            String sql = "INSERT INTO FoodItems (RetailerID, Name, Description, Category, Brand, Unit) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, foodItem.getRetailerId());
            pstmt.setString(2, foodItem.getName());
            pstmt.setString(3, foodItem.getDescription());
            pstmt.setString(4, foodItem.getCategory().toString());
            pstmt.setString(5, foodItem.getBrand());
            pstmt.setString(6, foodItem.getUnit());
            pstmt.executeUpdate();
            
            LMSLogger.getInstance().saveLogInformation("Insert a food item into database successfully!", FoodItemDAOImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
            LMSLogger.getInstance().saveLogInformation("SQLException occur at addFoodItem method: "+ex.getMessage(), FoodItemDAOImpl.class.getName() , LogLevel.ERROR);
        } 
    }

   


    @Override
    public List<FoodItemDTO> getFoodItemsByRetailerId(int retailerId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FoodItemDTO> foodItems = new ArrayList<>();
        try {
            con = DataSource.getConnection();
            String sql = "SELECT * FROM FoodItems WHERE RetailerID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, retailerId);
            rs = pstmt.executeQuery();
           
            while (rs.next()) {
            FoodItemDTO foodItem = new FoodItemDTO();
            
            int foodItemId = rs.getInt("FoodItemID");
            foodItem.setFoodItemId(rs.wasNull() ? null : foodItemId);
            
            int retailerIdValue = rs.getInt("RetailerID");
            foodItem.setRetailerId(rs.wasNull() ? null : retailerIdValue);
            
            foodItem.setName(rs.getString("Name"));
            foodItem.setDescription(rs.getString("Description"));
            foodItem.setCategory(CategoryEnum.valueOf(rs.getString("Category")));
            foodItem.setBrand(rs.getString("Brand"));
            foodItem.setUnit(rs.getString("Unit"));
            foodItems.add(foodItem);
        }
 
            LMSLogger.getInstance().saveLogInformation("Get food items successfully by retailerId = "+retailerId, FoodItemDAOImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getFoodItemsByRetailerId method: "+ex.getMessage(), FoodItemDAOImpl.class.getName() , LogLevel.ERROR);
        } 

        return foodItems;
    }

  

    
}
