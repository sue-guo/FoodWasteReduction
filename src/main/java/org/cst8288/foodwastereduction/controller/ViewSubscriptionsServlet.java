package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.notification.SubscriptionService;
import org.cst8288.foodwastereduction.notification.SubscriptionServiceImpl;

/**
 * File: ViewSubscriptionsServlet.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-31
 * Modified: 2024-08-03 
 * Description: This Servlet is used for retailer to view subscriptions
 * only doGet is used to get information from database.
 */
@WebServlet(name = "ViewSubscriptionsServlet", urlPatterns = {"/viewSubscriptions"})
public class ViewSubscriptionsServlet extends HttpServlet {
    /**
     * private attribute subsriptionService
     */
    private SubscriptionService subscriptionService;
    
    /**
     * private attribute userDao
     */
    private UserDao userDao;

    /**
     * initiation the servlet
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        subscriptionService = new SubscriptionServiceImpl(new SubscriptionDAOImpl(), new UserDaoImpl());
        userDao = new UserDaoImpl();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        String logMessage;
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        User user = userDao.getUserById(userId);

        if (user == null) {
            logMessage = "User not found for userId: " + userId;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, logMessage);
            return;
        }
        
        System.out.println("User found: " + user.getName() + ", Type: " + user.getUserType());
        if (user.getUserType() == UserType.RETAILER) {
            List<SubscriberDTO> subscribers = subscriptionService.getSubscribersByRetailerId(userId);
//            System.out.println("Number of subscribers found: " + subscribers.size());
//            System.out.println("Subscribers set in request: " + subscribers);
            request.setAttribute("subscribers", subscribers);
        } else {
            // For now, we'll just return an empty list for other user types
            // Later, we'll implement this for CONSUMER and CHARITABLE_ORGANIZATION
            System.out.println("User is not a RETAILER. UserType: " + user.getUserType());
        }

//        System.out.println("UserType set in request: " + user.getUserType());

        request.setAttribute("userType", user.getUserType());
        request.getRequestDispatcher("views/viewSubscriptions.jsp").forward(request, response);
    }

    
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
            out.println("<title>Servlet ViewSubscriptionsServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewSubscriptionsServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
