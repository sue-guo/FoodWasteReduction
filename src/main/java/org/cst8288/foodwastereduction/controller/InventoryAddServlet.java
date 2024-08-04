package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 * Servlet class for handling inventory addition requests.
 * This servlet handles both GET and POST requests to add new inventory items.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN
 */
public class InventoryAddServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves food items for a specific retailer and forwards the request to the add inventory view.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse userId from request parameters
            int userId = Integer.parseInt(request.getParameter("userId").trim());

            // Create an instance of FoodItemBusiness to interact with the business layer
            FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
            // Retrieve the list of food items for the given retailer ID
            List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
            // Set the food items as a request attribute to be accessed in the JSP
            request.setAttribute("foodItems", foodItems);

            // Log the successful retrieval of food items
            LMSLogger.getInstance().saveLogInformation("Fetched food items for userId = " + userId, InventoryAddServlet.class.getName(), LogLevel.INFO);

            // Forward the request to the addInventory.jsp view
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/addInventory.jsp");
            dispatcher.forward(request, response);

        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryAddServlet doGet: " + ex.getMessage(), InventoryAddServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Adds a new inventory item based on the form data submitted.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve form parameters
            int userId = Integer.parseInt(request.getParameter("userId").trim());
            int foodItemId = Integer.parseInt(request.getParameter("foodItemId"));
            String batchNumber = request.getParameter("batchNumber");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double regularPrice = Double.parseDouble(request.getParameter("regularPrice"));
            double discountRate = Double.parseDouble(request.getParameter("discountRate"));
            Date expirationDate = Date.valueOf(request.getParameter("expirationDate"));
            Date receiveDate = Date.valueOf(request.getParameter("receiveDate"));

            // Create a new InventoryDTO object
            InventoryDTO inventory = new InventoryDTO();
            inventory.setRetailerId(userId);
            inventory.setFoodItemId(foodItemId);
            inventory.setBatchNumber(batchNumber);
            inventory.setQuantity(quantity);
            inventory.setRegularPrice(regularPrice);
            inventory.setDiscountRate(discountRate);
            inventory.setExpirationDate(expirationDate);
            inventory.setReceiveDate(receiveDate);

            // Business logic to add the inventory
            InventoryBusiness inventoryBusiness = new InventoryBusiness();
            inventoryBusiness.addInventory(inventory);

            // Log the successful addition of inventory
            LMSLogger.getInstance().saveLogInformation("Added inventory for userId = " + userId, InventoryAddServlet.class.getName(), LogLevel.INFO);

            // Redirect to list all current inventory
            response.sendRedirect(request.getContextPath() + "/inventory?userId=" + userId);

        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryAddServlet doPost: " + ex.getMessage(), InventoryAddServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }
}