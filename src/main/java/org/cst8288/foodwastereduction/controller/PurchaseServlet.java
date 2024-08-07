package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.constants.TransactionType;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles HTTP requests for purchasing inventory items.
 * This servlet supports both GET and POST requests to manage
 * the process of purchasing inventory items, including displaying
 * inventory details and processing transactions.
 *
 * <p>It uses {@link InventoryBusiness}, {@link FoodItemBusiness},
 * and {@link TransactionBusiness} to perform the necessary operations.</p>
 *
 * @author yaoyi
 */
public class PurchaseServlet extends HttpServlet {
    /**
     * Business logic object for managing inventory operations.
     */
    private final InventoryBusiness inventoryBusiness = new InventoryBusiness();
    /**
     * Business logic object for managing food item operations.
     */
    private final FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
    /**
     * Business logic object for managing transaction operations.
     */
    private final TransactionBusiness transactionBusiness = new TransactionBusiness();

    /**
     * Handles HTTP GET requests to display details of an inventory item.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Parses the inventory ID from the request parameters.</li>
     *   <li>Retrieves the inventory item by ID.</li>
     *   <li>If the inventory item is found, retrieves associated food item details.</li>
     *   <li>Sets the inventory and food item details as request attributes.</li>
     *   <li>Forwards the request to {@code /views/purchase.jsp} for rendering.</li>
     *   <li>If the inventory item is not found, sends a 404 error response.</li>
     * </ul>
     *
     * @param request  the {@link HttpServletRequest} object that contains
     *                 the request the client has made of the servlet
     * @param response the {@link HttpServletResponse} object that will
     *                 contain the response the servlet sends to the client
     * @throws ServletException if an input or output error is detected
     *                           when the servlet handles the GET request
     * @throws IOException      if the request cannot be handled or if
     *                           an input or output error is detected
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Retrieve inventory ID from request parameters
            String inventoryIdStr = request.getParameter("inventoryId");
            int inventoryId = Integer.parseInt(inventoryIdStr);

            // Fetch inventory details by ID
            InventoryDTO inventory = inventoryBusiness.getInventoryById(inventoryId);
            if (inventory != null) {
                // Retrieve associated food item details
                FoodItemDTO foodItem = foodItemBusiness.getFoodItemById(inventory.getFoodItemId());

                // Set attributes for JSP
                request.setAttribute("inventory", inventory);
                request.setAttribute("foodItem", foodItem);

                // Forward to JSP page
                request.getRequestDispatcher("/views/purchase.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory not found");
                LMSLogger.getInstance().saveLogInformation("Inventory not with ID: " + inventoryId, this.getClass().getName(), LogLevel.ERROR);
            }
        } catch (Exception e) {
            LMSLogger.getInstance().saveLogInformation("Exception in doGet method: " + e.getMessage(), this.getClass().getName(), LogLevel.ERROR);
            throw new ServletException(e);
        }
    }

    /**
     * Handles HTTP POST requests to process a purchase of an inventory item.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Checks if the user is authenticated by retrieving the user
     *       object from the HTTP session.</li>
     *   <li>Parses the inventory ID and quantity from request parameters.</li>
     *   <li>Fetches the inventory details by ID.</li>
     *   <li>Updates the inventory to reflect the purchased quantity.</li>
     *   <li>Creates a new transaction record for the purchase.</li>
     *   <li>Redirects the user to the consumer page.</li>
     *   <li>If the input is invalid, logs the error and sends a 400 error response.</li>
     * </ul>
     *
     * @param request  the {@link HttpServletRequest} object that contains
     *                 the request the client has made of the servlet
     * @param response the {@link HttpServletResponse} object that will
     *                 contain the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected
     *                           when the servlet handles the POST request
     * @throws ServletException if an error occurs while processing the request
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        try {
            // Retrieve inventory ID from request parameters
            String inventoryIdStr = request.getParameter("inventoryId");
            int inventoryId = Integer.parseInt(inventoryIdStr);
            Integer quantity = Integer.parseInt(request.getParameter("quantity"));

            // Fetch inventory details by ID
            InventoryDTO inventory = inventoryBusiness.getInventoryById(inventoryId);

            // Update inventory to reflect purchased quantity
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryBusiness.updateInventory(inventory);
            LMSLogger.getInstance().saveLogInformation("Updated quantity of inventory with ID " + inventoryId, this.getClass().getName(), LogLevel.INFO);

            // Create a new transaction record for the purchase
            Transaction transaction = new Transaction();
            transaction.setInventoryID(inventoryId);
            transaction.setUserID(user.getUserID());
            transaction.setQuantity(quantity);
            transaction.setTransactionType(TransactionType.Purchase);
            transactionBusiness.addTransaction(transaction);
            LMSLogger.getInstance().saveLogInformation("Created a new transaction for inventory ID: " + inventoryId, this.getClass().getName(), LogLevel.INFO);

            response.sendRedirect(request.getContextPath() + "/consumer?userId=" + user.getUserID());
        } catch (NumberFormatException e) {
            LMSLogger.getInstance().saveLogInformation("Invalid inventoryId or quantity format: " + e.getMessage(), this.getClass().getName(), LogLevel.ERROR);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inventory ID or quantity");
        }
    }


}