/* File: PaymentBusiness.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description:  This class provides business logic for handling payments.
 *
 */
package org.cst8288.foodwastereduction.businesslayer;

import org.cst8288.foodwastereduction.dataaccesslayer.PaymentDao;
import org.cst8288.foodwastereduction.dataaccesslayer.PaymentDaoImpl;
import org.cst8288.foodwastereduction.model.Payment;

/**
 * This class provides business logic for handling payments.
 * 
 * It interacts with the data access layer (PaymentDao) to persist payment information.
 * The class is designed to be a bridge between the servlet layer and the data access layer,
 * ensuring that payments are correctly processed and stored.
 * @author Hongxiu Guo
 */
public class PaymentBusiness {

    /**
     * paymentDao 
     */
    private PaymentDao paymentDao = null;
    /**
     * Constructor initializes the PaymentDao implementation.
     * 
     * The constructor creates an instance of PaymentDaoImpl, which handles 
     * the actual data access operations.
     */
    public PaymentBusiness() {
        paymentDao = new PaymentDaoImpl();
    }
    
    /**
     * Adds a payment to the database.
     * 
     * This method delegates the addPayment operation to the PaymentDao implementation.
     * 
     * @param payment the Payment object containing payment details
     */
    public void addPayment(Payment payment) {
        paymentDao.addPayment(payment);
        //TODO : add a transction dao to update the the transction stutus: paid
    }
    
}
