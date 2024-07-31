/* File: PaymentStrategy.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description:  This interface defines the strategy for processing different types of payments.
 *
 */
package org.cst8288.foodwastereduction.payment;

import javax.servlet.http.HttpServletRequest;

/**
 * This interface defines the strategy for processing different types of payments.
 * Implementing classes should provide their own specific payment processing logic.
 * 
 * @author Hongxiu Guo
 */
public interface PaymentStrategy {
    /**
     * Processes the payment based on the provided HTTP request.
     * 
     * @param request the HTTP request containing the payment details
     * @return a JSON string containing the processed payment details
     * @throws Exception if an error occurs during payment processing
     */
    String processPayment(HttpServletRequest request) throws Exception;
}
