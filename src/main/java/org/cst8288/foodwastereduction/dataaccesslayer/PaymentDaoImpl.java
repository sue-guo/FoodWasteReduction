/* File: PaymentDaoImpl.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This class is an implementation of the PaymentDao interface,
 *              responsible for handling database operations related to payments.
 *
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.Payment;

/**
 * This class is an implementation of the PaymentDao interface,
 * responsible for handling database operations related to payments.
 * 
 * @author Hongxiu Guo
 */
public class PaymentDaoImpl implements PaymentDao {
    
    /**
     * Adds a payment record to the database.
     * 
     * @param payment the payment object containing transaction details
     */
    @Override
    public void addPayment(Payment payment) {
        
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            con = DataSource.getConnection();
            //Prepare SQL statement
            String sql = " INSERT INTO Payments (TransactionID, PaymentType, Amount, Credential, CreatedAt) VALUES (?,?,?,?, now())";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, payment.getTransactionId());
            pstmt.setString(2, payment.getPaymentType().toString());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setString(4, payment.getCredential());

            //Execute SQL statement
            pstmt.executeUpdate();
            LMSLogger.getInstance().saveLogInformation("Insert an payment into database successfully, TransactionID ="+payment.getTransactionId(), PaymentDaoImpl.class.getName() , LogLevel.INFO);
            
        } catch (SQLException ex) {
           ex.printStackTrace();
           LMSLogger.getInstance().saveLogInformation("SQLException occur at addPayment: "+ex.getMessage(), PaymentDaoImpl.class.getName() , LogLevel.ERROR);
        }
    }
    
}
