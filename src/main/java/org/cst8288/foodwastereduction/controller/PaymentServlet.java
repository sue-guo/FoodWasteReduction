/* File: PaymentServlet.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This Servlet for handling payment processing for web application requests.
 *
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.PaymentBusiness;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.payment.ApplePayPaymentStrategy;
import org.cst8288.foodwastereduction.payment.CheckPaymentStrategy;
import org.cst8288.foodwastereduction.payment.CreditCardPaymentStrategy;
import org.cst8288.foodwastereduction.model.Payment;
import org.cst8288.foodwastereduction.payment.PaymentContext;
import org.cst8288.foodwastereduction.payment.PaymentStrategy;
import org.cst8288.foodwastereduction.model.PaymentType;
import org.cst8288.foodwastereduction.payment.PaypalPaymentStrategy;


/**
 * This Servlet for handling payment processing for web application requests.
 * 
 * This servlet supports both GET and POST methods to process payments. 
 * It ensures consistency by delegating GET requests to the POST handler.
 * The POST handler manages the creation of Payment objects, selects the 
 * appropriate PaymentStrategy based on the type of payment, and processes 
 * the payment through the PaymentContext.
 * 
 * @author Hongxiu Guo
 */
public class PaymentServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests for payment
     * 
     * This method delegates the request to the doPost method to ensure that the logout
     * process is handled consistently regardless of the request method.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles HTTP POST requests for payment
     * 
     * Invalidates the current session if it exists, effectively logging out the user,
     * and then redirects to the login page.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try {
            addPayment(request, response);
        } catch (Exception ex) {
            LMSLogger.getInstance().saveLogInformation("Exception occur at PaymentServlet class doPost method: "+ex.getMessage(), PaymentServlet.class.getName() , LogLevel.ERROR);
        }

        // Redirect to login page
        response.sendRedirect("views/login.jsp");
    }
    
    
    /**
     * Adds a payment based on the request parameters
     * 
     * This method creates a Payment object, determines the appropriate payment strategy
     * based on the payment type, and executes the payment. It then sets the payment details
     * and adds the payment to the payment business layer.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     * @throws Exception if an error occurs during payment processing
     */
    private void addPayment(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Payment payment = new Payment();

        String transactionId = request.getParameter("transactionId");
        String paymentTypeStr = request.getParameter("paymentType");
        String amount = request.getParameter("amount");
        PaymentStrategy paymentStrategy;
        
        switch (PaymentType.valueOf(paymentTypeStr.toUpperCase())) {
                case CREDIT_CARD:
                    paymentStrategy = new CreditCardPaymentStrategy();
                    break;
                case PAYPAL:
                    paymentStrategy = new PaypalPaymentStrategy();
                    break;
                case APPLE_PAY:
                    paymentStrategy = new ApplePayPaymentStrategy();
                    break;
                case CHECK:
                    paymentStrategy = new CheckPaymentStrategy();
                    break;
                default:
                    return;
        }
        PaymentContext context = new PaymentContext(paymentStrategy);
        String paymentDetails = context.executePayment(request);
        
        payment.setTransactionId(Integer.valueOf(transactionId));
        payment.setAmount(Double.valueOf(amount));
        payment.setPaymentType(PaymentType.valueOf(paymentTypeStr.toUpperCase()));
        payment.setCredential(paymentDetails);
        
        PaymentBusiness paymentBusiness = new PaymentBusiness();
        // handle add payment process
        paymentBusiness.addPayment(payment);
    }

}