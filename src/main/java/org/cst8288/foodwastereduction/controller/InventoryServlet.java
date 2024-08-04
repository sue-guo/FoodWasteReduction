package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import java.util.Calendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * Servlet class for handling inventory-related requests.
 * This servlet handles both GET and POST requests to manage inventories.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN
 */
public class InventoryServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves inventories and food items for a specific retailer and forwards the request to the view.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse userId from request parameters
            int userId = Integer.parseInt(request.getParameter("userId").trim());
            
            // Create an instance of InventoryBusiness to interact with the business layer
            InventoryBusiness inventoryBusiness = new InventoryBusiness();
            // Retrieve the list of inventories for the given retailer ID
            List<InventoryDTO> inventories = inventoryBusiness.getInventoriesByRetailerId(userId);

            // Get today's date without time
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = new Date(calendar.getTimeInMillis());

            // Calculate the date 7 days from today
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            Date sevenDaysFromToday = new Date(calendar.getTimeInMillis());

            // Update isSurplus and isActive fields for each inventory item
            for (InventoryDTO inventory : inventories) {
                Date expirationDate = inventory.getExpirationDate();
                if (expirationDate != null) {
                    if (expirationDate.before(today)) {
                        inventory.setIsActive(false);
                    } else {
                        inventory.setIsActive(true);
                    }
                    if (expirationDate.before(sevenDaysFromToday) && !expirationDate.before(today)) {
                        inventory.setIsSurplus(true);
                    } else {
                        inventory.setIsSurplus(false);
                    }
                }
                // Update the inventory in the database
                inventoryBusiness.updateInventory(inventory);
            }

            // Set the inventories as a request attribute to be accessed in the JSP
            request.setAttribute("inventories", inventories);

            // Create an instance of FoodItemBusiness to interact with the business layer
            FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
            // Retrieve the list of food items for the given retailer ID
            List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
            // Set the food items as a request attribute to be accessed in the JSP
            request.setAttribute("foodItems", foodItems);

            // Log the successful retrieval of inventories and food items
            LMSLogger.getInstance().saveLogInformation("Fetched inventories and food items for userId=" + userId, InventoryServlet.class.getName(), LogLevel.INFO);

            // Forward the request to the inventory.jsp view
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/inventory.jsp");
            dispatcher.forward(request, response);

        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryServlet doGet: " + ex.getMessage(), InventoryServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Currently not implemented.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Method not implemented
    }
}