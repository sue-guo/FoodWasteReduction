
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
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
public class UserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
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
        
        if (userBusiness.authenticateUser(email, password)) {
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
