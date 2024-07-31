/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
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
import org.cst8288.foodwastereduction.model.Subscription;
import org.cst8288.foodwastereduction.model.User;
import org.cst8288.foodwastereduction.model.UserType;
import org.cst8288.foodwastereduction.notification.SubscriptionService;
import org.cst8288.foodwastereduction.notification.SubscriptionServiceImpl;

/**
 *
 * @author ryany
 */
@WebServlet(name = "SubscriptionManagementServlet", urlPatterns = {"/subscriptionManagement"})
public class SubscriptionManagementServlet extends HttpServlet {
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
            out.println("<title>Servlet SubscriptionManagementServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SubscriptionManagementServlet at " + request.getContextPath() + "</h1>");
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
        List<Subscription> userSubscriptions = subscriptionService.getSubscriptionsByUser(userId);

        request.setAttribute("user", user);
        request.setAttribute("localRetailers", localRetailers);
        request.setAttribute("userSubscriptions", userSubscriptions);
        request.setAttribute("foodCategories", CategoryEnum.values());

        request.getRequestDispatcher("/WEB-INF/views/subscriptionManagement.jsp").forward(request, response);
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

        CommunicationPreference communicationPreference = CommunicationPreference.valueOf(communicationPrefString);
        
        Set<String> foodPreferences = new HashSet<>();
        if (selectedFoodPreferences != null) {
            for (String pref : selectedFoodPreferences) {
                try {
                    foodPreferences.add(CategoryEnum.valueOf(pref).toString());
                } catch (IllegalArgumentException e) {
//                logger?
                }
            }
        }
        
        try {
            subscriptionService.updateUserSubscriptions(userId, retailerId, communicationPreference, foodPreferences);
            response.sendRedirect(request.getContextPath() + "/subscriptionManagement?userId=" + userId);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating subscriptions: " + e.getMessage());
        }
    }

}
