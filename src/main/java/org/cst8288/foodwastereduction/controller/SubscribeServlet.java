package org.cst8288.foodwastereduction.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.constants.CommunicationPreference;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.SubscriptionDAOImpl;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDao;
import org.cst8288.foodwastereduction.dataaccesslayer.UserDaoImpl;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.constants.UserType;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.notification.SubscriptionService;
import org.cst8288.foodwastereduction.notification.SubscriptionServiceImpl;

/**
 * File: SubscribeServlet.java
 * @author Ryan Xu
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-31
 * Modified: 2024-08-03 
 * Description: This Servlet is used to subscription management for Consumers and Charitable_Organizations
 * doGet is used to get SubscriberDTO to create the form in subscribe page
 * doPost is used to submit users update/delete/add operation to subscription table in database
 */
@WebServlet(name = "SubscribeServlet", urlPatterns = {"/subscribe"})
public class SubscribeServlet extends HttpServlet {
    /**
     * userDao
     */
    private UserDao userDao;
    
    /**
     * subsriptionDAO
     */
    private SubscriptionDAO subscriptionDAO;
    
    /**
     * subscriptionService
     */
    private SubscriptionService subscriptionService;
    
    /**
     * Initialization the servlet
     * @throws ServletException 
     */
    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
        subscriptionDAO = new SubscriptionDAOImpl();
        subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, userDao);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            out.println("<title>Servlet SubscribeServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubscribeServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage;
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDao.getUserById(userId);

        if (user == null) {
            logMessage = "User not found.";
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);

            response.sendError(HttpServletResponse.SC_NOT_FOUND, logMessage);
            return;
        }

        if (user.getUserType() != UserType.CONSUMER && user.getUserType() != UserType.CHARITABLE_ORGANIZATION) {
            logMessage = "Access denied for user: " + userId + ", UserType: " + user.getUserType();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);

            response.sendError(HttpServletResponse.SC_FORBIDDEN, logMessage);
            return;
        }

        List<User> localRetailers = userDao.getRetailersByCity(user.getCity());
        List<SubscriberDTO> userSubscriptions = subscriptionService.getSubscriptionsByUser(userId);
        
        // Check if the request is for AJAX or for a normal page
        String acceptHeader = request.getHeader("Accept");
        boolean isAjax = acceptHeader != null && acceptHeader.contains("application/json");
        
        if (isAjax) {
            // Convert multiple objects to a JSON response
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", user);
            responseData.put("localRetailers", localRetailers);
            responseData.put("userSubscriptions", userSubscriptions);
            responseData.put("foodCategories", CategoryEnum.values());

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseData);
            
            System.out.println("JSON Response: " + jsonResponse);

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        } else {
            // Forward to JSP for normal page request
            request.setAttribute("user", user);
            request.setAttribute("localRetailers", localRetailers);
            request.setAttribute("userSubscriptions", userSubscriptions);
            request.setAttribute("foodCategories", CategoryEnum.values());

            request.getRequestDispatcher("views/subscriptionManagement.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logMessage;
        int userId = Integer.parseInt(request.getParameter("userId"));
        int retailerId = Integer.parseInt(request.getParameter("retailerId"));
        String communicationPrefString = request.getParameter("communicationPreference");
        String[] selectedFoodPreferences = request.getParameterValues("foodPreferences");
        boolean noFoodPreference = Boolean.parseBoolean(request.getParameter("noFoodPreference"));

        CommunicationPreference communicationPreference;
        try {
            communicationPreference = CommunicationPreference.valueOf(communicationPrefString);
        } catch (IllegalArgumentException e) {
            logMessage = "Invalid communication preference: " + communicationPrefString;
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid communication preference");
            return;
        }
        
        Set<String> foodPreferences = new HashSet<>();
        if (selectedFoodPreferences != null && !noFoodPreference) {
            for (String pref : selectedFoodPreferences) {
                try {
                    foodPreferences.add(CategoryEnum.valueOf(pref).toString());
                } catch (IllegalArgumentException e) {
                    logMessage = "Invalid food preference: " + pref;
                    LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.WARN);
                }
            }
        }
        
        try {
            if (noFoodPreference) {
                subscriptionService.removeSubscription(userId, retailerId);
                logMessage = "Removed subscription for userId: " + userId + ", retailerId: " + retailerId;
                LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.INFO);
            } else {
                subscriptionService.updateUserSubscriptions(userId, retailerId, communicationPreference, foodPreferences);
            }
            request.getSession(false).setAttribute("successMessage", "Subscription updated successfully.");
            response.sendRedirect(request.getContextPath() + "/subscribe?userId=" + userId);
        } catch (Exception e) {
            logMessage = "Error updating subscriptions: " + e.getMessage();
            LMSLogger.getInstance().saveLogInformation(logMessage, this.getClass().getName(), LogLevel.ERROR);

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating subscriptions: " + e.getMessage());
        }
    }

}
