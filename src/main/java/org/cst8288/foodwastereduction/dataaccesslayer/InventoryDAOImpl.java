/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.constants.SurplusStatus;
import org.cst8288.foodwastereduction.model.Inventory;


/**
 *
 * @author ryany
 */
public class InventoryDAOImpl implements InventoryDAO {

    @Override
    public void insert(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO Inventory (RetailerID, FoodItemID, BatchNumber, Quantity, RegularPrice, DiscountRate, ExpirationDate, ReceiveDate, IsSurplus, SurplusStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, inventory.getRetailerId());
            statement.setInt(2, inventory.getFoodItemId());
            statement.setString(3, inventory.getBatchNumber());
            statement.setInt(4, inventory.getQuantity());
            statement.setDouble(5, inventory.getRegularPrice());
            statement.setDouble(6, inventory.getDiscountRate());
            statement.setDate(7, Date.valueOf(inventory.getExpirationDate()));
            statement.setDate(8, Date.valueOf(inventory.getReceiveDate()));
            statement.setBoolean(9, inventory.isSurplus());
            statement.setString(10, inventory.getSurplusStatus().name());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating inventory failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    inventory.setInventoryId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating inventory failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public Inventory getById(int inventoryID) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE InventoryID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventoryID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToInventory(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Inventory> getAll() throws SQLException {
        List<Inventory> inventories = new ArrayList<>();
        String sql = "SELECT * FROM Inventory WHERE IsActive = TRUE";
        try (Connection connection = DataSource.getConnection();
                Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                inventories.add(mapResultSetToInventory(resultSet));
            }
        }
        return inventories;
    }

    @Override
    public void update(Inventory inventory) throws SQLException {
        String sql = "UPDATE Inventory SET RetailerID = ?, FoodItemID = ?, BatchNumber = ?, Quantity = ?, RegularPrice = ?, DiscountRate = ?, ExpirationDate = ?, ReceiveDate = ?, IsSurplus = ?, SurplusStatus = ? WHERE InventoryID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventory.getRetailerId());
            statement.setInt(2, inventory.getFoodItemId());
            statement.setString(3, inventory.getBatchNumber());
            statement.setInt(4, inventory.getQuantity());
            statement.setDouble(5, inventory.getRegularPrice());
            statement.setDouble(6, inventory.getDiscountRate());
            statement.setDate(7, Date.valueOf(inventory.getExpirationDate()));
            statement.setDate(8, Date.valueOf(inventory.getReceiveDate()));
            statement.setBoolean(9, inventory.isSurplus());
            statement.setString(10, inventory.getSurplusStatus().name());
            statement.setInt(11, inventory.getInventoryId());
            
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int inventoryID) throws SQLException {
        String sql = "UPDATE Inventory SET IsActive = FALSE WHERE InventoryID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, inventoryID);
            statement.executeUpdate();
        }
    }

    private Inventory mapResultSetToInventory(ResultSet resultSet) throws SQLException {
        Inventory inventory = new Inventory();
        inventory.setInventoryId(resultSet.getInt("InventoryId"));
        inventory.setRetailerId(resultSet.getInt("RetailerId"));
        inventory.setFoodItemId(resultSet.getInt("FoodItemId"));
        inventory.setBatchNumber(resultSet.getString("BatchNumber"));
        inventory.setQuantity(resultSet.getInt("Quantity"));
        inventory.setRegularPrice(resultSet.getDouble("RegularPrice"));
        inventory.setDiscountRate(resultSet.getDouble("DiscountRate"));
        inventory.setExpirationDate(resultSet.getDate("ExpirationDate").toLocalDate());
        inventory.setReceiveDate(resultSet.getDate("ReceiveDate").toLocalDate());
        inventory.setSurplus(resultSet.getBoolean("IsSurplus"));
        inventory.setSurplusStatus(SurplusStatus.valueOf(resultSet.getString("SurplusStatus").toUpperCase()));
        inventory.setLastUpdated(resultSet.getTimestamp("LastUpdated"));
        inventory.setActive(resultSet.getBoolean("IsActive"));
        return inventory;
    }
}