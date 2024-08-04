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
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;

/**
 * Servlet class for handling inventory update requests.
 * This servlet handles both GET and POST requests to update existing inventory items.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN and Ryan Xu
 */
public class InventoryUpdateServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves the inventory item and associated food items for a specific retailer and forwards the request to the update inventory view.
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
            // Parse inventoryId from request parameters
            int inventoryId = Integer.parseInt(request.getParameter("inventoryId").trim());

            // Create an instance of InventoryBusiness to interact with the business layer
            InventoryBusiness inventoryBusiness = new InventoryBusiness();
            // Retrieve the inventory item by its ID
            InventoryDTO inventory = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryId);
            int retailerId = inventory.getRetailerId();

            // Create an instance of FoodItemBusiness to interact with the business layer
            FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
            // Retrieve the list of food items for the given retailer ID
            List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(retailerId);

            // Set the inventory and food items as request attributes to be accessed in the JSP
            request.setAttribute("inventory", inventory);
            request.setAttribute("foodItems", foodItems);

            // Log the successful retrieval of inventory and food items
            LMSLogger.getInstance().saveLogInformation("Fetched inventory and food items for inventoryId = " + inventoryId, InventoryUpdateServlet.class.getName(), LogLevel.INFO);

            // Forward the request to the updateInventory.jsp view
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/updateInventory.jsp");
            dispatcher.forward(request, response);

        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryUpdateServlet doGet: " + ex.getMessage(), InventoryUpdateServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Updates an existing inventory item based on the form data submitted.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve form parameters
            int userId = Integer.parseInt(request.getParameter("userId").trim());
            int inventoryId = Integer.parseInt(request.getParameter("inventoryId").trim());
            int foodItemId = Integer.parseInt(request.getParameter("foodItemId"));
            String batchNumber = request.getParameter("batchNumber");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double regularPrice = Double.parseDouble(request.getParameter("regularPrice"));
            double discountRate = Double.parseDouble(request.getParameter("discountRate"));
            Date expirationDate = Date.valueOf(request.getParameter("expirationDate"));
            Date receiveDate = Date.valueOf(request.getParameter("receiveDate"));
            boolean isSurplus = Boolean.parseBoolean(request.getParameter("isSurplus"));
            SurplusStatusEnum surplusStatus = SurplusStatusEnum.valueOf(request.getParameter("surplusStatus"));
            boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

            // Create a new InventoryDTO object
            InventoryDTO inventory = new InventoryDTO();
            inventory.setRetailerId(userId);
            inventory.setInventoryId(inventoryId);
            inventory.setFoodItemId(foodItemId);
            inventory.setBatchNumber(batchNumber);
            inventory.setQuantity(quantity);
            inventory.setRegularPrice(regularPrice);
            inventory.setDiscountRate(discountRate);
            inventory.setExpirationDate(expirationDate);
            inventory.setReceiveDate(receiveDate);
            inventory.setIsSurplus(isSurplus);
            inventory.setSurplusStatus(surplusStatus);
            inventory.setIsActive(isActive);

            // Business logic to update the inventory
            InventoryBusiness inventoryBusiness = new InventoryBusiness();
            inventoryBusiness.updateInventory(inventory);

            // Log the successful update of inventory
            LMSLogger.getInstance().saveLogInformation("Updated inventory for inventoryId = " + inventoryId, InventoryUpdateServlet.class.getName(), LogLevel.INFO);

            // Redirect to list all current inventory
            response.sendRedirect(request.getContextPath() + "/inventory?userId=" + userId);

        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryUpdateServlet doPost: " + ex.getMessage(), InventoryUpdateServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }
}