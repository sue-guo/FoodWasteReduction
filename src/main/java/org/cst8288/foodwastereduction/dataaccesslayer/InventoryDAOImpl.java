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
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;

/**
 *
 * @author WANG JIAYUN & Ryan Xu
 */
public class InventoryDAOImpl implements InventoryDAO {

     
   
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
            
               LMSLogger.getInstance().saveLogInformation("Get inventory successfully by retailerId = "+retailerId, InventoryDAOImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
              LMSLogger.getInstance().saveLogInformation("SQLException occur at getInventoriesByRetailerId method: "+ex.getMessage(), InventoryDAOImpl.class.getName() , LogLevel.ERROR);
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
            
                LMSLogger.getInstance().saveLogInformation("Get inventory successfully by inventoryId = "+inventoryID, InventoryDAOImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
              LMSLogger.getInstance().saveLogInformation("SQLException occur at getInventoryById method: "+ex.getMessage(), InventoryDAOImpl.class.getName() , LogLevel.ERROR);
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
            
            LMSLogger.getInstance().saveLogInformation("Insert an inventory into database successfully!", InventoryDAOImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
              LMSLogger.getInstance().saveLogInformation("SQLException occur at addInventory method: "+ex.getMessage(), InventoryDAOImpl.class.getName() , LogLevel.ERROR);
        } 
    }

    @Override
    public void updateInventory(InventoryDTO inventory) {
        String sql = "UPDATE Inventory SET RetailerID = ?, FoodItemID = ?, BatchNumber = ?, Quantity = ?, RegularPrice = ?, DiscountRate = ?, ExpirationDate = ?, ReceiveDate = ?, IsSurplus = ?, SurplusStatus = ?, IsActive = ? WHERE InventoryID = ?";
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
            pstmt.setBoolean(9, inventory.getIsSurplus());
            pstmt.setString(10, inventory.getSurplusStatus().name());
            pstmt.setBoolean(11, inventory.getIsActive());
            pstmt.setInt(12, inventory.getInventoryId());
            pstmt.executeUpdate();
            
            LMSLogger.getInstance().saveLogInformation("Update an inventory in database successfully, inventory ID = "+ inventory.getInventoryId(), InventoryDAOImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
              LMSLogger.getInstance().saveLogInformation("SQLException occur at updateInventory method: "+ex.getMessage(), InventoryDAOImpl.class.getName() , LogLevel.ERROR);
        } 
    }


    private InventoryDTO mapResultSetToInventory(ResultSet resultSet) throws SQLException {
        InventoryDTO inventory = new InventoryDTO();
        inventory.setInventoryId(resultSet.getInt("InventoryId"));
        inventory.setRetailerId(resultSet.getInt("RetailerId"));
        inventory.setFoodItemId(resultSet.getInt("FoodItemId"));
        inventory.setBatchNumber(resultSet.getString("BatchNumber"));
        inventory.setQuantity(resultSet.getInt("Quantity"));
        inventory.setRegularPrice(resultSet.getDouble("RegularPrice"));
        inventory.setDiscountRate(resultSet.getDouble("DiscountRate"));
        inventory.setExpirationDate(resultSet.getDate("ExpirationDate"));
        inventory.setReceiveDate(resultSet.getDate("ReceiveDate"));
        inventory.setIsSurplus(resultSet.getBoolean("IsSurplus"));
        inventory.setSurplusStatus(SurplusStatusEnum.valueOf(resultSet.getString("SurplusStatus").toUpperCase()));
        inventory.setLastUpdated(resultSet.getTimestamp("LastUpdated"));
        inventory.setIsActive(resultSet.getBoolean("IsActive"));
        return inventory;
    }

    @Override
    public List<InventoryDTO> getAllInventories() {

        List<InventoryDTO> inventories = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DataSource.getConnection();
            String sql = "SELECT * FROM Inventory";
            pstmt = con.prepareStatement(sql);
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

}
