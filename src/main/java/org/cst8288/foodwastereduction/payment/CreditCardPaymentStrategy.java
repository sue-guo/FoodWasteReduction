/* File: CreditCardPaymentStrategy.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description:  This class implements the PaymentStrategy interface for processing
 *               credit card payments.
 *
 */
package org.cst8288.foodwastereduction.payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.servlet.http.HttpServletRequest;

/**
 * This class implements the PaymentStrategy interface for processing
 * credit card payments.
 * 
 * @author Hongxiu Guo
 */
public class CreditCardPaymentStrategy implements PaymentStrategy{
    
    /**
     * Processes the credit card payment by extracting the necessary details
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
        paymentDetails.put("type", "CREDIT_CARD");
        paymentDetails.put("cardNumber", request.getParameter("cardNumber"));
        paymentDetails.put("cardHolder", request.getParameter("cardHolder"));
        paymentDetails.put("expiryDate", request.getParameter("expiryDate"));
        paymentDetails.put("cvv", request.getParameter("cvv"));
        return paymentDetails.toString();
    }
    
}
