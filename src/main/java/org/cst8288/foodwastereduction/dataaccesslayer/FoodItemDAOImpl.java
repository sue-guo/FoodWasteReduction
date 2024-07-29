/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.constants.FoodCategory;
import org.cst8288.foodwastereduction.model.FoodItem;


/**
 *
 * @author ryany
 */
public class FoodItemDAOImpl implements FoodItemDAO {
    
    @Override
    public void insert(FoodItem foodItem) throws SQLException {
        String sql = "INSERT INTO FoodItems (RetailerID, Name, Description, Category, Brand, Unit) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, foodItem.getRetailerId());
            statement.setString(2, foodItem.getName());
            statement.setString(3, foodItem.getDescription());
            statement.setString(4, foodItem.getCategory().name());
            statement.setString(5, foodItem.getBrand());
            statement.setString(6, foodItem.getUnit());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating food item failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    foodItem.setFoodItemId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating food item failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public FoodItem getById(int foodItemID) throws SQLException {
        String sql = "SELECT * FROM FoodItems WHERE FoodItemID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, foodItemID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToFoodItem(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<FoodItem> getAll() throws SQLException {
        List<FoodItem> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM FoodItems";
        try (Connection connection = DataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                foodItems.add(mapResultSetToFoodItem(resultSet));
            }
        }
        return foodItems;
    }

    @Override
    public List<FoodItem> getByRetailer(int retailerID) throws SQLException {
        List<FoodItem> foodItems = new ArrayList<>();
        String sql = "SELECT * FROM FoodItems WHERE RetailerID = ?";
        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, retailerID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    foodItems.add(mapResultSetToFoodItem(resultSet));
                }
            }
        }
        return foodItems;
    }

    @Override
    public void update(FoodItem foodItem) throws SQLException {
        String sql = "UPDATE FoodItems SET RetailerID = ?, Name = ?, Description = ?, Category = ?, Brand = ?, Unit = ? WHERE FoodItemID = ?";
        try (Connection connection = DataSource.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, foodItem.getRetailerId());
            statement.setString(2, foodItem.getName());
            statement.setString(3, foodItem.getDescription());
            statement.setString(4, foodItem.getCategory().name());
            statement.setString(5, foodItem.getBrand());
            statement.setString(6, foodItem.getUnit());
            statement.setInt(7, foodItem.getFoodItemId());
            
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int foodItemID) throws SQLException {
        String sql = "DELETE FROM FoodItems WHERE FoodItemID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, foodItemID);
            statement.executeUpdate();
        }
    }

    private FoodItem mapResultSetToFoodItem(ResultSet resultSet) throws SQLException {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodItemId(resultSet.getInt("FoodItemID"));
        foodItem.setRetailerId(resultSet.getInt("RetailerID"));
        foodItem.setName(resultSet.getString("Name"));
        foodItem.setDescription(resultSet.getString("Description"));
        foodItem.setCategory(FoodCategory.valueOf(resultSet.getString("Category").toUpperCase()));
        foodItem.setBrand(resultSet.getString("Brand"));
        foodItem.setUnit(resultSet.getString("Unit"));
        return foodItem;
    }
}