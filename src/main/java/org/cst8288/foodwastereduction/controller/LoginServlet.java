/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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

/**
 *
 * @author Hongxiu Guo
 */
public class LoginServlet extends HttpServlet {


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserBusiness userBusiness = new UserBusiness();
        
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
            // Forward to user.jsp if user is found
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/user.jsp");
            dispatcher.forward(request, response);
        }
    }

}
