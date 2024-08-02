/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
import org.cst8288.foodwastereduction.model.CategoryEnum;
import org.cst8288.foodwastereduction.model.SubscriberDTO;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.model.UserType;
import org.cst8288.foodwastereduction.notification.SubscriptionService;
import org.cst8288.foodwastereduction.notification.SubscriptionServiceImpl;

/**
 * This Servlet is used to subscription management for Consumers and Charitable_Organizations
 * doGet is used to get SubscriberDTO to create the form in subscribe page
 * doPost is used to submit users' update/delete/add operation to subscription table in database
 * @author Ryan Xu
 */
@WebServlet(name = "SubscribeServlet", urlPatterns = {"/subscribe"})
public class SubscribeServlet extends HttpServlet {
    private UserDao userDao;
    private SubscriptionDAO subscriptionDAO;
    private SubscriptionService subscriptionService;
    
    @Override
    public void init() throws ServletException {
        userDao = new UserDaoImpl();
        subscriptionDAO = new SubscriptionDAOImpl();
        subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, userDao);
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
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userDao.getUserById(userId);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            return;
        }

        if (user.getUserType() != UserType.CONSUMER && user.getUserType() != UserType.CHARITABLE_ORGANIZATION) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
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

//        request.getRequestDispatcher("views/subscribe.jsp").forward(request, response);

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
        int userId = Integer.parseInt(request.getParameter("userId"));
        int retailerId = Integer.parseInt(request.getParameter("retailerId"));
        String communicationPrefString = request.getParameter("communicationPreference");
        String[] selectedFoodPreferences = request.getParameterValues("foodPreferences");
        boolean noFoodPreference = Boolean.parseBoolean(request.getParameter("noFoodPreference"));

        CommunicationPreference communicationPreference = CommunicationPreference.valueOf(communicationPrefString);
        
        Set<String> foodPreferences = new HashSet<>();
        if (selectedFoodPreferences != null && !noFoodPreference) {
            for (String pref : selectedFoodPreferences) {
                try {
                    foodPreferences.add(CategoryEnum.valueOf(pref).toString());
                } catch (IllegalArgumentException e) {
//                logger?
                }
            }
        }
        
        try {
            if (noFoodPreference) {
                subscriptionService.removeSubscription(userId, retailerId);
            } else {
                subscriptionService.updateUserSubscriptions(userId, retailerId, communicationPreference, foodPreferences);
            }
            request.getSession().setAttribute("successMessage", "Subscription updated successfully.");
            response.sendRedirect(request.getContextPath() + "/subscribe?userId=" + userId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating subscriptions: " + e.getMessage());
        }
    }

}
