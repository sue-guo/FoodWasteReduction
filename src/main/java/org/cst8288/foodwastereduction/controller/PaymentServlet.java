
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
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
        
        String paymentType = request.getParameter("paymentType");
        JSONObject paymentDetails = new JSONObject();

        paymentDetails.put("type", paymentType);

        if ("creditCard".equals(paymentType)) {
            paymentDetails.put("cardNumber", request.getParameter("cardNumber"));
            paymentDetails.put("cardHolder", request.getParameter("cardHolder"));
            paymentDetails.put("expiryDate", request.getParameter("expiryDate"));
            paymentDetails.put("cvv", request.getParameter("cvv"));
        } else if ("paypal".equals(paymentType)) {
            paymentDetails.put("email", request.getParameter("email"));
            paymentDetails.put("password", request.getParameter("password"));
        } else if ("applePay".equals(paymentType)) {
            paymentDetails.put("deviceToken", request.getParameter("deviceToken"));
        } else if ("check".equals(paymentType)) {
            paymentDetails.put("checkNumber", request.getParameter("checkNumber"));
            paymentDetails.put("bankName", request.getParameter("bankName"));
            paymentDetails.put("accountNumber", request.getParameter("accountNumber"));
            paymentDetails.put("routingNumber", request.getParameter("routingNumber"));
            paymentDetails.put("payee", request.getParameter("payee"));
            paymentDetails.put("date", request.getParameter("date"));
            paymentDetails.put("memo", request.getParameter("memo"));
        }

        // Redirect to login page
        response.sendRedirect("views/login.jsp");
    }

}
