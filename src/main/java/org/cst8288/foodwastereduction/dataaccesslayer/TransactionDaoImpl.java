package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.Transaction;
import org.cst8288.foodwastereduction.model.TransactionType;
import org.cst8288.foodwastereduction.utility.DatetimeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yaoyi
 */
public class TransactionDaoImpl implements TransactionDao{

    /**
     * Add new transaction into database
     * @param transaction the transaction to be inserted into the database
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
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Get transactions of a certain by userID
     *
     * @return transactions made by certain user
     */
    @Override
    public List<Transaction> getTransactionByUser(int userID) {

        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        List<Transaction> transactionList = new ArrayList<>();
        try {
            con = DataSource.getConnection();
            String sql = " SELECT * FROM Transactions WHERE UserID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                    transaction.setTransactionID(rs.getInt("TransactionID"));
                    transaction.setInventoryID(rs.getInt("InventoryID"));
                    transaction.setUserID(rs.getInt("UserID"));
                    transaction.setQuantity(rs.getInt("Quantity"));
                    transaction.setTransactionType(TransactionType.valueOf(rs.getString("TransactionType").toUpperCase()));
                    transaction.setTransactionDate(DatetimeUtil.formatTimestampAsString(rs.getTimestamp("TransactionDate"), "YYYY-MM-DD HH:ss") );
                transactionList.add(transaction);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactionList;
    }


}
