package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.Transaction;
import org.cst8288.foodwastereduction.constants.TransactionType;
import org.cst8288.foodwastereduction.utility.DatetimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

/**
 * Implementation of the {@link TransactionDao} interface.
 *
 * <p>This class provides the actual data access logic for managing
 * transactions in the database. It includes methods for adding new
 * transactions and retrieving existing transactions by user ID or
 * transaction ID.</p>
 *
 * @author yaoyi
 */
public class TransactionDaoImpl implements TransactionDao{

    /**
     * Adds a new transaction to the database.
     *
     * <p>This method inserts a new {@link Transaction} record into
     * the Transactions table in the database.</p>
     *
     * @param transaction the {@link Transaction} object to be added to the database
     */
    @Override
    public void addTransaction(Transaction transaction) {

        Connection con;
        PreparedStatement pstmt;
        try {
            con = DataSource.getConnection();
            String sql = " INSERT INTO Transactions (InventoryID, UserID, Quantity, TransactionType, TransactionDate) VALUES (?,?,?,?, now())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, transaction.getInventoryID());
            pstmt.setInt(2, transaction.getUserID());
            pstmt.setInt(3, transaction.getQuantity());
            pstmt.setString(4, transaction.getTransactionType().toString());
            pstmt.executeUpdate();
            LMSLogger.getInstance().saveLogInformation("Insert an transaction  successfully", TransactionDaoImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
             LMSLogger.getInstance().saveLogInformation("SQLException occur at addTransaction: "+ex.getMessage(), TransactionDaoImpl.class.getName() , LogLevel.ERROR);
        }
    }

    /**
     * Retrieves a list of transactions for a specific user.
     *
     * <p>This method queries the database to retrieve all transactions
     * associated with the given user ID.</p>
     *
     * @param userID the ID of the user whose transactions are to be retrieved
     * @return a {@link List} of {@link Transaction} objects associated with the specified user
     */
    @Override
    public List<Transaction> getTransactionByUser(int userID) {

        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Transaction> transactionList = new ArrayList<>();
        try {
            con = DataSource.getConnection();
            String sql = " select t1.*, t3.Name as FoodItem, t4.Name as retailer, t2.regularPrice, t2.expirationDate,  " +
                            "(CASE" +
                            "        WHEN t1.TransactionType = 'Donation' THEN NULL  " +
                            "        ELSE t1.Quantity * t2.RegularPrice * DiscountRate " +
                            "  END) AS TotalAmount  " +
                            "from transactions t1 " +
                            "left join Inventory t2 on t1.InventoryID  = t2.InventoryID\n" +
                            "left join FoodItems t3 on t2.FoodItemID = t3.FoodItemID \n" +
                            "left join Users t4 on t3.RetailerID = t4.UserID WHERE t1.UserID = ? order by t1.TransactionID desc";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                    transaction.setTransactionID(rs.getInt("TransactionID"));
                    transaction.setInventoryID(rs.getInt("InventoryID"));
                    transaction.setUserID(rs.getInt("UserID"));
                    transaction.setQuantity(rs.getInt("Quantity"));
                    transaction.setTransactionType(TransactionType.valueOf(rs.getString("TransactionType")));
                    transaction.setTransactionDate(DatetimeUtil.formatTimestampAsString(rs.getTimestamp("TransactionDate"), "yyyy-MM-dd HH:mm") );
                    transaction.setPayStatus(rs.getString("PayStatus"));
                    transaction.setFoodItem(rs.getString("FoodItem"));                  
                    transaction.setRetailer(rs.getString("retailer"));
                    transaction.setRegularPrice(rs.getDouble("regularPrice"));
                    transaction.setExpirationDate(DatetimeUtil.formatTimestampAsString(rs.getTimestamp("expirationDate"), "yyyy-MM-dd") );
                    transaction.setTotalAmount(rs.getDouble("TotalAmount"));
                transactionList.add(transaction);
            }
        } catch (SQLException ex) {
             LMSLogger.getInstance().saveLogInformation("SQLException occur at getTransactionByUser: "+ex.getMessage(), TransactionDaoImpl.class.getName() , LogLevel.ERROR);
        }
        return transactionList;
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * <p>This method queries the database to retrieve the {@link Transaction}
     * record with the specified transaction ID.</p>
     *
     * @param transactionID the ID of the transaction to be retrieved
     * @return the {@link Transaction} object with the specified ID, or {@code null}
     *         if no transaction with that ID exists
     */
    public Transaction getTransactionById(int transactionID) {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs = null;
        Transaction transaction = null;

        try {
            con = DataSource.getConnection();
            String sql = "SELECT * FROM Transactions WHERE TransactionID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, transactionID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setTransactionID(rs.getInt("TransactionID"));
                transaction.setInventoryID(rs.getInt("InventoryID"));
                transaction.setUserID(rs.getInt("UserID"));
                transaction.setQuantity(rs.getInt("Quantity"));
                transaction.setTransactionType(TransactionType.valueOf(rs.getString("TransactionType")));
                transaction.setTransactionDate(DatetimeUtil.formatTimestampAsString(rs.getTimestamp("TransactionDate"), "yyyy-MM-dd HH:mm"));
            }
            
        } catch (SQLException ex) {
           LMSLogger.getInstance().saveLogInformation("SQLException occur at getTransactionById: "+ex.getMessage(), TransactionDaoImpl.class.getName() , LogLevel.ERROR);
        }
        return transaction;
    }
    /**
     * Updates the payment status of a transaction to 'PAID' in the database.
     *
     * @param transactionID The ID of the transaction whose payment status is to be updated.
     */
    @Override
    public void updateTransactionPayment(int transactionID) {
        Connection con;
        PreparedStatement pstmt;
        try {
            con = DataSource.getConnection();
            String sql = " UPDATE Transactions SET payStatus = 'PAID' WHERE TransactionID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, transactionID);
            pstmt.executeUpdate();
            LMSLogger.getInstance().saveLogInformation("Update an payment status  successfully, TransactionID ="+transactionID, TransactionDaoImpl.class.getName() , LogLevel.INFO);
        } catch (SQLException ex) {
            LMSLogger.getInstance().saveLogInformation("SQLException occur at updateTransactionPayment: "+ex.getMessage(), TransactionDaoImpl.class.getName() , LogLevel.ERROR);
        }
    }


}
