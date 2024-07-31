/* File: PaypalPaymentStrategy.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description:  This class implements the PaymentStrategy interface for processing
 *               PayPal payments.
 *
 */
package org.cst8288.foodwastereduction.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.servlet.http.HttpServletRequest;

/**
 * This class implements the PaymentStrategy interface for processing
 * PayPal payments.
 * 
 * @author Hongxiu Guo
 */
public class PaypalPaymentStrategy implements PaymentStrategy {
    
    /**
     * Processes the PayPal payment by extracting the necessary details
     * from the HTTP request and returning them as a JSON string.
     * 
     * @param request the HTTP request containing the payment details
     * @return a JSON string containing the payment details
     * @throws Exception if an error occurs during processing
     */  
    @Override
    public String processPayment(HttpServletRequest request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode paymentDetails = mapper.createObjectNode();
        paymentDetails.put("type", "PAYPAL");
        paymentDetails.put("email", request.getParameter("email"));
        paymentDetails.put("password", request.getParameter("password"));
        return paymentDetails.toString();
    }
}
