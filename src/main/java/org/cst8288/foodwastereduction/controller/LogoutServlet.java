/* File: LogoutServlet.java
 * Author: Hongxiu Guo
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Date: 2024.07
 * Modified: 
 * Description: This Servlet class for handling user logout operations.
 *
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet for handling user logout operations.
 * 
 * This servlet handles both GET and POST requests to log out the user by invalidating
 * the current session and redirecting to the login page.
 * 
 * @author Hongxiu Guo
 */
public class LogoutServlet extends HttpServlet {


    /**
     * Handles HTTP GET requests for user logout.
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
     * Handles HTTP POST requests for user logout.
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
        // Invalidate the session if exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("user", null); // Clear user attribute
            session.invalidate(); // Invalidate the session
        }
        // Redirect to login page
        response.sendRedirect("views/login.jsp");
    }

}
