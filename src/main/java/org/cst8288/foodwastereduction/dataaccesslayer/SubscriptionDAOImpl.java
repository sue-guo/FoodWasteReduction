/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.model.Notification;

/**
 *
 * @author ryany
 */
import java.sql.*;
import java.util.*;
import org.cst8288.foodwastereduction.model.Subscription;

public class SubscriptionDAOImpl implements SubscriptionDAO {
    private Connection connection; 

    public SubscriptionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveSubscription(Subscription subscription) {
        String sql = "INSERT INTO Subscriptions (UserID, RetailerID, CommunicationPreference, FoodPreferences, CreatedAt, LastUpdated) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, subscription.getUserId());
            pstmt.setInt(2, subscription.getRetailerId());
            pstmt.setString(3, subscription.getCommunicationPreference());
            pstmt.setString(4, String.join(",", subscription.getFoodPreferences())); // transfer Set to String
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
        }
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        String sql = "UPDATE Subscriptions SET RetailerID = ?, CommunicationPreference = ?, " +
                     "FoodPreferences = ?, LastUpdated = ? WHERE SubscriptionID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getRetailerId());
            pstmt.setString(2, subscription.getCommunicationPreference());
            pstmt.setString(3, String.join(",", subscription.getFoodPreferences()));
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(5, subscription.getSubscriptionId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSubscription(int userId, int retailerId) {
        String sql = "DELETE FROM Subscriptions WHERE UserID = ? AND RetailerID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, retailerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Subscription> getSubscriptionsByRetailer(int retailerId) {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscriptions WHERE RetailerID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
    public List<Subscription> getSubscriptionsByUser(int userId) {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscriptions WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
            rs.getString("CommunicationPreference"),
            new HashSet<>(Arrays.asList(rs.getString("FoodPreferences").split(","))),
            rs.getTimestamp("CreatedAt"),
            rs.getTimestamp("LastUpdated")
        );
    }
}