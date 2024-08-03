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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 *
 * @author WNAG JIAYUN & Ryan Xu
 */
public class FoodItemDAOImpl implements FoodItemDAO {
    
    private static final Logger LOGGER = Logger.getLogger(FoodItemDAOImpl.class.getName());

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
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
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
                foodItems.add(mapResultSetToFoodItem(rs));
            }
 
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
        return foodItems;
    }

    public List<FoodItemDTO> getAllFoodItems() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FoodItemDTO> foodItems = new ArrayList<>();
        try {
            con = DataSource.getConnection();
            String sql = "SELECT * FROM FoodItems";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                foodItems.add(mapResultSetToFoodItem(rs));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return foodItems;
    }

    @Override
    public FoodItemDTO getFoodItemById(int foodItemId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FoodItemDTO foodItem = null;
        try {
            con = DataSource.getConnection();
            String sql = "SELECT * FROM FoodItems WHERE FoodItemID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, foodItemId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                foodItem = mapResultSetToFoodItem(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return foodItem;
    }

  
    @Override
    public void updateFoodItem(FoodItemDTO foodItem) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DataSource.getConnection();
            String sql = "UPDATE FoodItems SET RetailerID = ?, Name = ?, Description = ?, Category = ?, Brand = ?, Unit = ? WHERE FoodItemID = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, foodItem.getRetailerId());
            pstmt.setString(2, foodItem.getName());
            pstmt.setString(3, foodItem.getDescription());
            pstmt.setString(4, foodItem.getCategory().name());
            pstmt.setString(5, foodItem.getBrand());
            pstmt.setString(6, foodItem.getUnit());
            pstmt.setInt(7, foodItem.getFoodItemId());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteFoodItem(Integer foodItemID) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DataSource.getConnection();
            String sql = "DELETE FROM FoodItems WHERE FoodItemID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, foodItemID);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    private FoodItemDTO mapResultSetToFoodItem(ResultSet resultSet) throws SQLException {
        FoodItemDTO foodItem = new FoodItemDTO();
        foodItem.setFoodItemId(resultSet.getInt("FoodItemID"));
        foodItem.setRetailerId(resultSet.getInt("RetailerID"));
        foodItem.setName(resultSet.getString("Name"));
        foodItem.setDescription(resultSet.getString("Description"));
        foodItem.setCategory(CategoryEnum.valueOf(resultSet.getString("Category")));
        foodItem.setBrand(resultSet.getString("Brand"));
        foodItem.setUnit(resultSet.getString("Unit"));
        return foodItem;
    }
}
