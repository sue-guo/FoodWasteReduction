/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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
 * @author Hongxiu Guo
 */
public class SignupServlet extends HttpServlet {


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
     * Handle user addition into the database
     * @param request
     * @param response 
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
