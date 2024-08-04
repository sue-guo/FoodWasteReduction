/* File: LoginServlet.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This Servlet class for handling user login requests.
 *
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.cst8288.foodwastereduction.businesslayer.UserBusiness;
import org.cst8288.foodwastereduction.model.User;

/**
 * Servlet for handling user login requests.
 * 
 * This servlet processes HTTP POST requests to authenticate users based on
 * the provided email and password. It interacts with the UserBusiness class
 * to perform authentication and forwards the user to the appropriate JSP page
 * based on the authentication result.
 * 
 * @author Hongxiu Guo
 */
public class LoginServlet extends HttpServlet {

    private   UserBusiness userBusiness;
    
    // Default constructor for servlet container
    public LoginServlet() {
        this(new UserBusiness());
    }

    // Constructor for dependency injection
    public LoginServlet(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }
    /**
     * Handles HTTP POST requests for user login.
     * 
     * Retrieves the email and password from the request parameters, 
     * uses the UserBusiness class to authenticate the user, and forwards
     * the request to the appropriate JSP page based on the authentication result.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        User user = userBusiness.getUserByEmail(email);
        
        if (!userBusiness.authenticateUser(email, password)) {
            // If user is not found and the password is not correct, set error message and forward to login.jsp
            request.setAttribute("errorMessage", "User does not exist or password is wrong. Please try again.");
            request.setAttribute("email", email); // Preserve the email entered by the user
            request.setAttribute("password", password); // Preserve the password entered by the user
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp");
            dispatcher.forward(request, response);
        } else {
            // Forward to home.jsp if user is found, and set user in session
            HttpSession session = request.getSession(false);
            session.setAttribute("user", user);
             
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/home.jsp");
            dispatcher.forward(request, response);
        }
    }

}
