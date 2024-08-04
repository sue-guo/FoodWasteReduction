package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.FoodItemDTO;

/**
 * Implementation of the FoodItemDAO interface.
 * This class provides methods to perform CRUD operations on FoodItem objects in the database.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WNAG JIAYUN & YAO YI & Ryan Xu
 * 
 */
public class FoodItemDAOImpl implements FoodItemDAO {

    /**
     * Adds a new food item to the database.
     * 
     * @param foodItem the FoodItemDTO object to be added
     */
    @Override
    public void addFoodItem(FoodItemDTO foodItem) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // Get a connection to the database
            con = DataSource.getConnection();
            // SQL query to insert a new food item
            String sql = "INSERT INTO FoodItems (RetailerID, Name, Description, Category, Brand, Unit) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            // Set the parameters for the prepared statement
            pstmt.setInt(1, foodItem.getRetailerId());
            pstmt.setString(2, foodItem.getName());
            pstmt.setString(3, foodItem.getDescription());
            pstmt.setString(4, foodItem.getCategory().toString());
            pstmt.setString(5, foodItem.getBrand());
            pstmt.setString(6, foodItem.getUnit());
            // Execute the update
            pstmt.executeUpdate();
            
            // Log the successful insertion
            LMSLogger.getInstance().saveLogInformation("Insert a food item into database successfully!", FoodItemDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur
            LMSLogger.getInstance().saveLogInformation("SQLException occur at addFoodItem method: " + ex.getMessage(), FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
        } 
    }

    /**
     * Retrieves a list of food items for a specific retailer.
     * 
     * @param retailerId the ID of the retailer
     * @return a list of FoodItemDTO objects
     */
    @Override
    public List<FoodItemDTO> getFoodItemsByRetailerId(int retailerId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FoodItemDTO> foodItems = new ArrayList<>();
        try {
            // Get a connection to the database
            con = DataSource.getConnection();
            // SQL query to select food items by retailer ID
            String sql = "SELECT * FROM FoodItems WHERE RetailerID = ?";
            pstmt = con.prepareStatement(sql);
            // Set the retailer ID parameter
            pstmt.setInt(1, retailerId);
            // Execute the query
            rs = pstmt.executeQuery();
           
            // Iterate through the result set and map each row to a FoodItemDTO object
            while (rs.next()) {
                foodItems.add(mapResultSetToFoodItem(rs));
            }
 
            // Log the successful retrieval
            LMSLogger.getInstance().saveLogInformation("Get food items successfully by retailerId = " + retailerId, FoodItemDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getFoodItemsByRetailerId method: " + ex.getMessage(), FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
        } 

        return foodItems;
    }

    /**
     * Retrieves a list of all food items in the database.
     * 
     * @return a list of FoodItemDTO objects
     */
    @Override
    public List<FoodItemDTO> getAllFoodItems() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FoodItemDTO> foodItems = new ArrayList<>();
        try {
            // Get a connection to the database
            con = DataSource.getConnection();
            // SQL query to select all food items
            String sql = "SELECT * FROM FoodItems";
            pstmt = con.prepareStatement(sql);
            // Execute the query
            rs = pstmt.executeQuery();

            // Iterate through the result set and map each row to a FoodItemDTO object
            while (rs.next()) {
                foodItems.add(mapResultSetToFoodItem(rs));
            }
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getAllFoodItems method: " + ex.getMessage(), FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
        }
        return foodItems;
    }

    /**
     * Retrieves a food item by its ID.
     * 
     * @param foodItemId the ID of the food item
     * @return the FoodItemDTO object
     */
    @Override
    public FoodItemDTO getFoodItemById(int foodItemId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FoodItemDTO foodItem = null;
        try {
            // Get a connection to the database
            con = DataSource.getConnection();
            // SQL query to select a food item by its ID
            String sql = "SELECT * FROM FoodItems WHERE FoodItemID = ?";
            pstmt = con.prepareStatement(sql);
            // Set the food item ID parameter
            pstmt.setInt(1, foodItemId);
            // Execute the query
            rs = pstmt.executeQuery();

            // If a result is found, map it to a FoodItemDTO object
            if (rs.next()) {
                foodItem = mapResultSetToFoodItem(rs);
            }
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur
            LMSLogger.getInstance().saveLogInformation("SQLException occur at getFoodItemById method: " + ex.getMessage(), FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
        }
        return foodItem;
    }

    /**
     * Updates an existing food item in the database.
     * 
     * @param foodItem the FoodItemDTO object with updated information
     */
    @Override
    public void updateFoodItem(FoodItemDTO foodItem) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            // Get a connection to the database
            con = DataSource.getConnection();
            // SQL query to update a food item
            String sql = "UPDATE FoodItems SET RetailerID = ?, Name = ?, Description = ?, Category = ?, Brand = ?, Unit = ? WHERE FoodItemID = ?";
            pstmt = con.prepareStatement(sql);

            // Set the parameters for the prepared statement
            pstmt.setInt(1, foodItem.getRetailerId());
            pstmt.setString(2, foodItem.getName());
            pstmt.setString(3, foodItem.getDescription());
            pstmt.setString(4, foodItem.getCategory().name());
            pstmt.setString(5, foodItem.getBrand());
            pstmt.setString(6, foodItem.getUnit());
            pstmt.setInt(7, foodItem.getFoodItemId());

            // Execute the update
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            // Log any SQL exceptions that occur
            LMSLogger.getInstance().saveLogInformation("SQLException occur at updateFoodItem method: " + ex.getMessage(), FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
        } finally {
            // Close the resources
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                // Log any SQL exceptions that occur during resource closing
                LMSLogger.getInstance().saveLogInformation("SQLException occur at updateFoodItem method during closing resources: " + ex.getMessage(), FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
            }
        }
    }

    /**
     * Maps a ResultSet row to a FoodItemDTO object.
     * 
     * @param resultSet the ResultSet object
     * @return the FoodItemDTO object
     * @throws SQLException if a database access error occurs
     */
    private FoodItemDTO mapResultSetToFoodItem(ResultSet resultSet) throws SQLException {
        // Create a new FoodItemDTO object
        FoodItemDTO foodItem = new FoodItemDTO();
        try{
            // Set the properties of the FoodItemDTO object from the ResultSet
            foodItem.setFoodItemId(resultSet.getInt("FoodItemID"));
            foodItem.setRetailerId(resultSet.getInt("RetailerID"));
            foodItem.setName(resultSet.getString("Name"));
            foodItem.setDescription(resultSet.getString("Description"));
            foodItem.setCategory(CategoryEnum.valueOf(resultSet.getString("Category")));
            foodItem.setBrand(resultSet.getString("Brand"));
            foodItem.setUnit(resultSet.getString("Unit"));
            
            LMSLogger.getInstance().saveLogInformation("Successfully mapped ResultSet to FoodItemDTO: ", FoodItemDAOImpl.class.getName(), LogLevel.INFO);
        } catch (SQLException e) {
            LMSLogger.getInstance().saveLogInformation("Error mapping ResultSet to FoodItemDTO: ", FoodItemDAOImpl.class.getName(), LogLevel.ERROR);
        throw e;
    }
        // Return the FoodItemDTO object
        return foodItem;
    }
}