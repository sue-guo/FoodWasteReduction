/* File: PaymentDao.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This PaymentDao is an interface for payment data access operations.
 *
 */
package org.cst8288.foodwastereduction.dataaccesslayer;

import org.cst8288.foodwastereduction.model.Payment;

/**
 * PaymentDao is an interface for payment data access operations.
 * 
 * This interface defines the contract for adding a payment to the data store.
 * Implementations of this interface should provide the actual logic for persisting payment information.
 * 
 * @autor Hongxiu Guo
 */
public interface PaymentDao {
    /**
     * Adds a payment to the database.
     * 
     * This method is responsible for persisting the given Payment object.
     * 
     * @param payment the Payment object containing payment details
     */
    public void addPayment(Payment payment);
    
}
