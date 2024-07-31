/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import org.cst8288.foodwastereduction.model.Subscription;

/**
 *
 * @author ryany
 */


public class SubscriptionDAOImpl implements SubscriptionDAO {

    @Override
    public void addSubscription(Subscription subscription) {
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
                throw new SQLException("Creating subscription failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subscription.setSubscriptionId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating subscription failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log more details if needed
            System.err.println("SQL Error Code: " + e.getErrorCode());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Message: " + e.getMessage());            
        }
    }
    
    private String convertSetToString(Set<String> set) {
        return String.join(",", set);
    }
    
    @Override
    public void updateSubscription(Subscription subscription) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSubscription(Integer userId, Integer retailerId) {
        String sql = "DELETE FROM Subscriptions WHERE UserID = ? AND RetailerID = ?";
        try (Connection connection = DataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, retailerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            e.printStackTrace();
        }
        return subscriptions;
    }

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
            e.printStackTrace();
        }
        return subscriptions;
    }

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
            e.printStackTrace();
        }
        
        return null;  // No subscription
    }

    private Subscription mapResultSetToSubscription(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("SubscriptionID");
        Integer userId = rs.getInt("UserID");
        Integer retailerId = rs.getInt("RetailerID");
        CommunicationPreference communicationPreference = CommunicationPreference.valueOf(rs.getString("CommunicationPreference").toUpperCase());
        
        // Save the fodd preferences in string split by ","
        String foodPreferencesStr = rs.getString("FoodPreferences").toUpperCase();
        Set<String> foodPreferences = new HashSet<>(Arrays.asList(foodPreferencesStr.split(",")));
        
        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        Timestamp updatedAt = rs.getTimestamp("UpdatedAt");

        return new Subscription(id, userId, retailerId, communicationPreference, foodPreferences, createdAt, updatedAt);
    }    
    
}