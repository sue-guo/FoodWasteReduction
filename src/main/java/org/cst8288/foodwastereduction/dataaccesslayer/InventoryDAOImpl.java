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
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;

/**
 *
 * @author WANG JIAYUN
 */
public class InventoryDAOImpl implements InventoryDAO {
    
     private static final Logger LOGGER = Logger.getLogger(InventoryDAOImpl.class.getName());
     
   
    @Override
    public List<InventoryDTO> getInventoriesByRetailerId(int retailerId) {
        
        List<InventoryDTO> inventories = new ArrayList<>();
        String sql = "SELECT * FROM Inventory WHERE RetailerID = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, retailerId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                InventoryDTO inventory = new InventoryDTO();
                inventory.setInventoryId(rs.getInt("InventoryID"));
                inventory.setRetailerId(rs.getInt("RetailerID"));
                inventory.setFoodItemId(rs.getInt("FoodItemID"));
                inventory.setBatchNumber(rs.getString("BatchNumber"));
                inventory.setQuantity(rs.getInt("Quantity"));
                inventory.setRegularPrice(rs.getDouble("RegularPrice"));
                inventory.setDiscountRate(rs.getDouble("DiscountRate"));
                inventory.setExpirationDate(rs.getDate("ExpirationDate"));
                inventory.setReceiveDate(rs.getDate("ReceiveDate"));
                inventory.setIsSurplus(rs.getBoolean("IsSurplus"));
                inventory.setSurplusStatus(SurplusStatusEnum.valueOf(rs.getString("SurplusStatus")));
                inventory.setLastUpdated(rs.getTimestamp("LastUpdated"));
                inventory.setIsActive(rs.getBoolean("IsActive"));
                inventories.add(inventory);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 

        return inventories;
    }

    
    
    
    
    
     @Override
     public InventoryDTO getInventoryById(int inventoryID) {
        InventoryDTO inventory = null;
        String sql = "SELECT * FROM Inventory WHERE InventoryID = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, inventoryID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                inventory = new InventoryDTO();
                inventory.setInventoryId(rs.getInt("InventoryID"));
                inventory.setRetailerId(rs.getInt("RetailerID"));
                inventory.setFoodItemId(rs.getInt("FoodItemID"));
                inventory.setBatchNumber(rs.getString("BatchNumber"));
                inventory.setQuantity(rs.getInt("Quantity"));
                inventory.setRegularPrice(rs.getDouble("RegularPrice"));
                inventory.setDiscountRate(rs.getDouble("DiscountRate"));
                inventory.setExpirationDate(rs.getDate("ExpirationDate"));
                inventory.setReceiveDate(rs.getDate("ReceiveDate"));
                inventory.setIsSurplus(rs.getBoolean("IsSurplus"));
                inventory.setSurplusStatus(SurplusStatusEnum.valueOf(rs.getString("SurplusStatus")));
                inventory.setLastUpdated(rs.getTimestamp("LastUpdated"));
                inventory.setIsActive(rs.getBoolean("IsActive"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
        return inventory;
    }
    
     
     
     
     
     
      @Override
      public void addInventory(InventoryDTO inventory) {
        String sql = "INSERT INTO Inventory (RetailerID, FoodItemID, BatchNumber, Quantity, RegularPrice, DiscountRate, ExpirationDate, ReceiveDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, inventory.getRetailerId());
            pstmt.setInt(2, inventory.getFoodItemId());
            pstmt.setString(3, inventory.getBatchNumber());
            pstmt.setInt(4, inventory.getQuantity());
            pstmt.setDouble(5, inventory.getRegularPrice());
            pstmt.setDouble(6, inventory.getDiscountRate());
            pstmt.setDate(7, inventory.getExpirationDate());
            pstmt.setDate(8, inventory.getReceiveDate());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
    }
      
      
       @Override
       public void updateInventory(InventoryDTO inventory) {
        String sql = "UPDATE Inventory SET RetailerID = ?, FoodItemID = ?, BatchNumber = ?, Quantity = ?, RegularPrice = ?, DiscountRate = ?, ExpirationDate = ?, ReceiveDate = ? WHERE InventoryID = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, inventory.getRetailerId());
            pstmt.setInt(2, inventory.getFoodItemId());
            pstmt.setString(3, inventory.getBatchNumber());
            pstmt.setInt(4, inventory.getQuantity());
            pstmt.setDouble(5, inventory.getRegularPrice());
            pstmt.setDouble(6, inventory.getDiscountRate());
            pstmt.setDate(7, inventory.getExpirationDate());
            pstmt.setDate(8, inventory.getReceiveDate());
            pstmt.setInt(9, inventory.getInventoryId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
    }


}
