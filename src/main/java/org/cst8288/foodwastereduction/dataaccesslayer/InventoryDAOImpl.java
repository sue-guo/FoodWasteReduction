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
 * Implementation of the InventoryDAO interface for accessing inventory data from the database.
 * This class provides methods to perform CRUD operations on the Inventory table.
 * It uses JDBC for database interactions and logs operations using LMSLogger.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WNAG JIAYUN & YAO YI & Ryan Xu
 */
public class InventoryDAOImpl implements InventoryDAO {

    /**
     * Retrieves a list of inventories for a specific retailer by their ID.
     * 
     * @param retailerId the ID of the retailer
     * @return a list of InventoryDTO objects
     */
    @Override
    public List<InventoryDTO> getInventoriesByRetailerId(int retailerId) {
        List<InventoryDTO> inventories = new ArrayList<>();
        String sql = "SELECT * FROM Inventory WHERE RetailerID = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establishing connection to the database
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, retailerId);
            rs = pstmt.executeQuery();

            // Iterating through the result set and mapping to InventoryDTO
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

            // Logging successful retrieval
            LMSLogger.getInstance().saveLogInformation("Get inventory successfully by retailerId = " + retailerId, InventoryDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException ex) {
            // Logging SQL exception
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getInventoriesByRetailerId method: " + ex.getMessage(), InventoryDAOImpl.class.getName(), LogLevel.ERROR);
        } 

        return inventories;
    }

    
    /**
     * Retrieves an inventory by its ID.
     * 
     * @param inventoryID the ID of the inventory
     * @return an InventoryDTO object
     */
    @Override
    public InventoryDTO getInventoryById(int inventoryID) {
        InventoryDTO inventory = null;
        String sql = "SELECT * FROM Inventory WHERE InventoryID = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establishing connection to the database
            con = DataSource.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, inventoryID);
            rs = pstmt.executeQuery();

            // Mapping result set to InventoryDTO
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

            // Logging successful retrieval
            LMSLogger.getInstance().saveLogInformation("Get inventory successfully by inventoryId = " + inventoryID, InventoryDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException ex) {
            // Logging SQL exception
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getInventoryById method: " + ex.getMessage(), InventoryDAOImpl.class.getName(), LogLevel.ERROR);
        } 
        return inventory;
    }

    /**
     * Adds a new inventory to the database.
     * 
     * @param inventory the InventoryDTO object to be added
     */
    @Override
    public void addInventory(InventoryDTO inventory) {
        String sql = "INSERT INTO Inventory (RetailerID, FoodItemID, BatchNumber, Quantity, RegularPrice, DiscountRate, ExpirationDate, ReceiveDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // Establishing connection to the database
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

            // Logging successful insertion
            LMSLogger.getInstance().saveLogInformation("Insert an inventory into database successfully!", InventoryDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException ex) {
            // Logging SQL exception
            LMSLogger.getInstance().saveLogInformation("SQLException occur at addInventory method: " + ex.getMessage(), InventoryDAOImpl.class.getName(), LogLevel.ERROR);
        } 
    }

    /**
     * Updates an existing inventory in the database.
     * 
     * @param inventory the InventoryDTO object to be updated
     */
    @Override
    public void updateInventory(InventoryDTO inventory) {
        String sql = "UPDATE Inventory SET RetailerID = ?, FoodItemID = ?, BatchNumber = ?, Quantity = ?, RegularPrice = ?, DiscountRate = ?, ExpirationDate = ?, ReceiveDate = ?, IsSurplus = ?, SurplusStatus = ?, IsActive = ? WHERE InventoryID = ?";
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            // Establishing connection to the database
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

            // Logging successful update
            LMSLogger.getInstance().saveLogInformation("Update an inventory in database successfully, inventory ID = " + inventory.getInventoryId(), InventoryDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException ex) {
            // Logging SQL exception
            LMSLogger.getInstance().saveLogInformation("SQLException occur at updateInventory method: " + ex.getMessage(), InventoryDAOImpl.class.getName(), LogLevel.ERROR);
        } 
    }

    /**
     * Maps a ResultSet to an InventoryDTO object.
     * 
     * @param resultSet the ResultSet to be mapped
     * @return an InventoryDTO object
     * @throws SQLException if a database access error occurs
     */
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

    /**
     * Retrieves all inventories from the database.
     * 
     * @return a list of InventoryDTO objects
     */
    @Override
    public List<InventoryDTO> getAllInventories() {
        List<InventoryDTO> inventories = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establishing connection to the database
            con = DataSource.getConnection();
            String sql = "SELECT * FROM Inventory";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Iterating through the result set and mapping to InventoryDTO
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
            // Logging SQL exception
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getAllInventories method: " + ex.getMessage(), InventoryDAOImpl.class.getName(), LogLevel.ERROR);
        }
        return inventories;
    }
}