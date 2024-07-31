/* File: PaymentContext.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description:  This class serves as the context for executing a payment strategy.
 *
 */
package org.cst8288.foodwastereduction.payment;

import javax.servlet.http.HttpServletRequest;

/**
 * This class serves as the context for executing a payment strategy.
 * It utilizes a specific payment strategy to process a payment request.
 * 
 * @author Hongxiu Guo
 */
public class PaymentContext {
    /**
     * The payment strategy to be used for processing the payment.
     */
    private PaymentStrategy paymentStrategy;
    /**
     * Constructor to initialize the PaymentContext with a specific payment strategy.
     * 
     * @param paymentStrategy the payment strategy to be used
     */
    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    /**
     * Executes the payment process using the specified payment strategy.
     * 
     * @param request the HTTP request containing the payment details
     * @return a JSON string containing the processed payment details
     * @throws Exception if an error occurs during payment processing
     */
    public String executePayment(HttpServletRequest request) throws Exception {
        return paymentStrategy.processPayment(request);
    }
    
}
