/* File: SignupServlet.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This  Servlet class for handling user registration (signup) requests.
 *
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.UserBusiness;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.model.UserType;
import org.cst8288.foodwastereduction.utility.PasswordUtil;

/**
 * 
 * This servlet processes HTTP POST requests to register new users. It checks
 * if the user already exists based on the provided email, and if not, it adds
 * the new user to the database. After successful registration, it redirects
 * the user to the login page.
 * 
 * @author Hongxiu Guo
 */
public class SignupServlet extends HttpServlet {


    /**
     * Handles HTTP POST requests for user registration.
     * 
     * Retrieves user details from the request parameters, checks if the user 
     * already exists, and if not, adds the user to the database. It then 
     * redirects to the login page upon successful registration or forwards 
     * to the signup page with an error message if the user already exists.
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
     * hashes the password, sets the user type, and then adds the user to the database
     * through the UserBusiness class.
     * 
     * @param request the HttpServletRequest object containing the request data
     * @param response the HttpServletResponse object used to send a response to the client
     */
    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        
        User user = new User();
        user.setName(request.getParameter("name").trim());
        user.setEmail(request.getParameter("email").trim());
        user.setPassword(PasswordUtil.hashPassword(request.getParameter("password").trim()));
        user.setUserType(UserType.valueOf(request.getParameter("userType")));
        user.setPhoneNumber(request.getParameter("phoneNumber").trim());
        user.setAddress(request.getParameter("address").trim());
        user.setCity(request.getParameter("city").trim());
        
        UserBusiness userBusiness = new UserBusiness();
        
        userBusiness.addUser(user);
    }

}
