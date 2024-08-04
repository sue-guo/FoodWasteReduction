/* File: UserServlet.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This Servlet class for managing user-related operations such as authentication and registration.
 *
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.UserBusiness;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.utility.PasswordUtil;

/**
 * Servlet for managing user-related operations such as authentication and registration.
 * 
 * This servlet processes both GET and POST requests. For GET requests, it authenticates users
 * based on the provided email and password and forwards to the appropriate JSP page. For POST
 * requests, it handles user registration by checking if the user already exists and either 
 * registering the new user or forwarding to the signup page with an error message.
 * 
 * @author Hongxiu Guo
 */
public class UserServlet extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles HTTP GET requests for user authentication.
     * 
     * Retrieves the email and password from request parameters, uses the UserBusiness class
     * to authenticate the user, and forwards to the appropriate JSP page based on the result.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserBusiness userBusiness = new UserBusiness();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userBusiness.getUserByEmail(email);
        
        if (!userBusiness.authenticateUser(email, password)) {
            // If user is not found and the password is not correct, set error message and forward to login.jsp
            request.setAttribute("errorMessage", "User does not exist or password is wrong. Please try again.");
            request.setAttribute("email", email); // Preserve the email entered by the user
            request.setAttribute("password", password); // Preserve the password entered by the user
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/login.jsp");
            dispatcher.forward(request, response);
        } else {
            // Forward to user.jsp if user is found
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/user.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Handles HTTP POST requests for user registration.
     * 
     * Retrieves the email from request parameters, checks if the user already exists, 
     * and either forwards to the signup page with an error message or registers the new user.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserBusiness userBusiness = new UserBusiness();
        String email = request.getParameter("email");
        User user = userBusiness.getUserByEmail(email);
        
        if (user != null) {
            // If user already exists, forward to signup page with error message
            request.setAttribute("errorMessage", "User already exists. Please try with a different email.");
            request.getRequestDispatcher("views/signup.jsp").forward(request, response);
        } else {
            // insert new user
            addUser(request, response);
            // Redirect to login page after successful signup
            response.sendRedirect("views/login.jsp");
        }
    }

    /**
     * Creates a User object from request parameters and adds it to the database.
     * 
     * This method retrieves user details from the request, creates a User object,
     * hashes the password, and then adds the user to the database using the UserBusiness class.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(PasswordUtil.hashPassword(request.getParameter("password")));
        user.setUserType(UserType.valueOf(request.getParameter("userType")));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setAddress(request.getParameter("address"));
        user.setCity(request.getParameter("city"));
        
        UserBusiness userBusiness = new UserBusiness();
        
        userBusiness.addUser(user);
    }

}
