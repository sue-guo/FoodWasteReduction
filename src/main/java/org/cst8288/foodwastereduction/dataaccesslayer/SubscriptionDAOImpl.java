package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.Subscription;

/**
 * File: SubscriptionDAOImpl.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-31
 * Modified: 2024-08-03 
 * Description: Implementation of SubscriptionDAO
 */
public class SubscriptionDAOImpl implements SubscriptionDAO {

    /**
     * add subscription
     * @param subscription 
     */
    @Override
    public void addSubscription(Subscription subscription) {
        String logMessage;
        String sql = "INSERT INTO Subscriptions (UserID, RetailerID, CommunicationPreference, FoodPreferences, CreatedAt, LastUpdated) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, subscription.getUserId());
            pstmt.setInt(2, subscription.getRetailerId());
            pstmt.setString(3, subscription.getCommunicationPreference().toString());
            
            String foodPreferencesString = convertSetToString(subscription.getFoodPreferences());
//            System.out.println("FoodPreferences: " + foodPreferencesString); // Debug the FoodPreferences value
            pstmt.setString(4, foodPreferencesString);
            
            pstmt.setString(4, convertSetToString(subscription.getFoodPreferences())); // transfer Set to String
            pstmt.setTimestamp(5, subscription.getCreatedAt());
            pstmt.setTimestamp(6, subscription.getLastUpdated());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                logMessage = "Creating subscription failed, no rows affected. UserID: " + subscription.getUserId() + ", RetailerID: " + subscription.getRetailerId();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);

                throw new SQLException("Creating subscription failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subscription.setSubscriptionId(generatedKeys.getInt(1));
                } else {
                    logMessage = "Creating subscription failed, no ID obtained. UserID: " + subscription.getUserId() + ", RetailerID: " + subscription.getRetailerId();
                    LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
 
                    throw new SQLException("Creating subscription failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logMessage = "SQL Error when adding subscription. UserID: " + subscription.getUserId() + 
              ", RetailerID: " + subscription.getRetailerId() + 
              ", Error Code: " + e.getErrorCode() + 
              ", SQL State: " + e.getSQLState() + 
              ", Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error adding subscription", e);          
        }
    }
    
    /**
     * convert set to string
     * @param set
     * @return 
     */
    private String convertSetToString(Set<String> set) {
        return String.join(",", set);
    }
    
    /**
     * update subscription
     * @param subscription 
     */
    @Override
    public void updateSubscription(Subscription subscription) {
        String logMessage;
        String sql = "UPDATE Subscriptions SET RetailerID = ?, CommunicationPreference = ?, " +
                     "FoodPreferences = ?, LastUpdated = ? WHERE SubscriptionID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getRetailerId());
            pstmt.setString(2, subscription.getCommunicationPreference().toString());
            pstmt.setString(3, String.join(",", subscription.getFoodPreferences()));
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(5, subscription.getSubscriptionId());
            
            pstmt.executeUpdate();
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                logMessage = "Updating subscription failed, no rows affected. SubscriptionID: " + subscription.getSubscriptionId();
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            }
        } catch (SQLException e) {
            logMessage = "SQL Error when updating subscription. SubscriptionID: " + subscription.getSubscriptionId() + 
                         ", Error Code: " + e.getErrorCode() + 
                         ", SQL State: " + e.getSQLState() + 
                         ", Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error updating subscription", e);
        }
    }

    /**
     * delete subscription
     * @param userId
     * @param retailerId 
     */
    @Override
    public void deleteSubscription(Integer userId, Integer retailerId) {
        String logMessage;
        String sql = "DELETE FROM Subscriptions WHERE UserID = ? AND RetailerID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, retailerId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logMessage = "Successfully deleted subscription for UserID: " + userId + ", RetailerID: " + retailerId;
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
            } else {
                logMessage = "No subscription found to delete for UserID: " + userId + ", RetailerID: " + retailerId;
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            }
        } catch (SQLException e) {
            logMessage = "Error deleting subscription for UserID: " + userId + ", RetailerID: " + retailerId +
             " | Error Code: " + e.getErrorCode() +
             " | SQL State: " + e.getSQLState() +
             " | Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error deleting subscription", e);
        }
    }

    /**
     * get List subscription
     * @param retailerId
     * @return 
     */
    @Override
    public List<Subscription> getSubscriptionsByRetailer(Integer retailerId) {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscriptions WHERE RetailerID = ?";
        try (Connection connection = DataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, retailerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    subscriptions.add(extractSubscriptionFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            String logMessage = "Error retrieving subscriptions for RetailerID: " + retailerId +
                                " | Error Code: " + e.getErrorCode() +
                                " | SQL State: " + e.getSQLState() +
                                " | Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error retrieving subscriptions", e);
        }
        return subscriptions;
    }

    /**
     * List subscription 
     * @param userId
     * @return 
     */
    @Override
    public List<Subscription> getSubscriptionsByUser(Integer userId) {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscriptions WHERE UserID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    subscriptions.add(extractSubscriptionFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            String logMessage = "Error retrieving subscriptions for UserID: " + userId +
                                " | Error Code: " + e.getErrorCode() +
                                " | SQL State: " + e.getSQLState() +
                                " | Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error retrieving subscriptions for user", e);
        }
        return subscriptions;
    }

    /**
     * extract subscription fro ResultSEt
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Subscription extractSubscriptionFromResultSet(ResultSet rs) throws SQLException {
        return new Subscription(
            rs.getInt("SubscriptionID"),
            rs.getInt("UserID"),
            rs.getInt("RetailerID"),
            CommunicationPreference.valueOf(rs.getString("CommunicationPreference").toUpperCase()),
            new HashSet<>(Arrays.asList(rs.getString("FoodPreferences").toUpperCase().split(","))),
            rs.getTimestamp("CreatedAt"),
            rs.getTimestamp("LastUpdated")
        );
    }

    /**
     * get subscription
     * @param consumerId
     * @param retailerId
     * @return 
     */
    @Override
    public Subscription getSubscription(Integer consumerId, Integer retailerId) {
        String sql = "SELECT * FROM subscriptions WHERE userid = ? AND retailerid = ?";
        
        try (Connection connection = DataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, consumerId);
            pstmt.setInt(2, retailerId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSubscription(rs);
                }
            }
        } catch (SQLException e) {
            String logMessage = "Error get subscription by consumerId " + consumerId + " and retailerId " + retailerId + ": " +
                                " | Error Code: " + e.getErrorCode() +
                                " | SQL State: " + e.getSQLState() +
                                " | Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new RuntimeException("Error get subscription by consumerId and retailerId.", e);
        }
        
        return null;  // No subscription
    }

    /**
     * map result set to subscription
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Subscription mapResultSetToSubscription(ResultSet rs) throws SQLException {
        try {
           Integer id = rs.getInt("SubscriptionID");
           Integer userId = rs.getInt("UserID");
           Integer retailerId = rs.getInt("RetailerID");
           CommunicationPreference communicationPreference = CommunicationPreference.valueOf(rs.getString("CommunicationPreference").toUpperCase());

           // Save the fodd preferences in string split by ","
           String foodPreferencesStr = rs.getString("FoodPreferences").toUpperCase();
           Set<String> foodPreferences = new HashSet<>(Arrays.asList(foodPreferencesStr.split(",")));

           Timestamp createdAt = rs.getTimestamp("CreatedAt");
           Timestamp updatedAt = rs.getTimestamp("LastUpdated");

           return new Subscription(id, userId, retailerId, communicationPreference, foodPreferences, createdAt, updatedAt);       
        } catch (IllegalArgumentException e) {
            // This could happen if the CommunicationPreference enum value is invalid
            String logMessage = "Error mapping ResultSet to Subscription. Invalid CommunicationPreference for SubscriptionID: " + rs.getInt("SubscriptionID") +
                                " | Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw new SQLException("Invalid data in ResultSet", e);
        } catch (SQLException e) {
            String logMessage = "Error mapping ResultSet to Subscription" +
                                " | Error Code: " + e.getErrorCode() +
                                " | SQL State: " + e.getSQLState() +
                                " | Error Message: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);
            throw e; // if no this throw will be error.
        }
    }
    
}