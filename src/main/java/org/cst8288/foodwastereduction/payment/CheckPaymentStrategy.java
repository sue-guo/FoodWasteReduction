/* File: CheckPaymentStrategy.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This class implements the PaymentStrategy interface for processing
 *              check payments.
 *
 */
package org.cst8288.foodwastereduction.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.servlet.http.HttpServletRequest;

/**
 * This class implements the PaymentStrategy interface for processing
 * check payments.
 * 
 * @author Hongxiu Guo
 */
public class CheckPaymentStrategy implements PaymentStrategy {
    /**
     * Processes the check payment by extracting the necessary details
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
        paymentDetails.put("type", "CHECK");
        paymentDetails.put("checkNumber", request.getParameter("checkNumber"));
        paymentDetails.put("bankName", request.getParameter("bankName"));
        paymentDetails.put("accountNumber", request.getParameter("accountNumber"));
        paymentDetails.put("routingNumber", request.getParameter("routingNumber"));
        paymentDetails.put("payee", request.getParameter("payee"));
        paymentDetails.put("date", request.getParameter("date"));
        paymentDetails.put("memo", request.getParameter("memo"));
        return paymentDetails.toString();
    }
}
