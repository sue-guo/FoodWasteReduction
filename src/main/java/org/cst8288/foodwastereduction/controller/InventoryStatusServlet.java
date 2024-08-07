package org.cst8288.foodwastereduction.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAO;
import org.cst8288.foodwastereduction.dataaccesslayer.InventoryDAOImpl;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;


/**
 * File: InventoryStatusServlet.java
 * @author WANG JIAYUN & Xu Ryan
 * Course: CST8288
 * Assignment: Final project (Food Waste Reduction)
 * Created: 2024-07-31
 * Modified: 2024-08-03 
 * Description: This servlet is used to respond to the Donation / Sale buttons in Inventory page.
 * Two events will triggered by the buttons: Inventory update and Notification
 * Inventory part: Wang, Notification part: Xu
 */
public class InventoryStatusServlet extends HttpServlet {
    private InventoryDAO inventoryDAO;
    private NotificationServlet notificationServlet;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();
        inventoryDAO = new InventoryDAOImpl();   // Inventory part
        notificationServlet = new NotificationServlet(); // Notification part
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
        
          // Get parameters from the request
        String inventoryIdParam = request.getParameter("inventoryId");
        String statusParam = request.getParameter("status");
        
//        InventoryBusiness inventoryBusiness = new InventoryBusiness();
         // Update the surplus status of the inventory item
//        InventoryDTO inventory = inventoryBusiness.getInventoryById(inventoryId);
        try {
            int inventoryId = Integer.parseInt(inventoryIdParam);
            SurplusStatusEnum status = SurplusStatusEnum.valueOf(statusParam);
            
            InventoryDTO inventory = inventoryDAO.getInventoryById(inventoryId);
            if (inventory != null) {
                // Update the inventory
                inventory.setSurplusStatus(status);
                inventoryDAO.updateInventory(inventory);

                // Call notificationServlet to deal with notification
                List<String> notifiedUsers = notificationServlet.processNotification(inventoryId, status);
                String userTypeNotified = (status == SurplusStatusEnum.Discount) ? "CONSUMER" : "CHARITABLE_ORGANIZATION";
                
                // If only update inventory, redirect is enough
                // But for Notification part, the notifiedUsers List need to pass by JSON
                // So comment redirect instead of JSON
                sendSuccessResponse(response, notifiedUsers, userTypeNotified);
                // Redirect back to the inventory page, or by JSON, because need NotifiedUsers
//                response.sendRedirect(request.getContextPath() + "/inventory?userId=" + inventory.getRetailerId());
            } else {
                sendErrorResponse(response, HttpServletResponse.SC_NOT_FOUND, "Inventory not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating inventory: " + e.getMessage());
        }
  
    }
    
    private void sendSuccessResponse(HttpServletResponse response, List<String> notifiedUsers, String userTypeNotified) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("message", "Status updated successfully");
        responseData.put("notifiedUsers", notifiedUsers);
        responseData.put("userTypeNotified", userTypeNotified);
        
        sendJsonResponse(response, HttpServletResponse.SC_OK, responseData);
    }
    
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", false);
        responseData.put("message", message);

        sendJsonResponse(response, statusCode, responseData);
    }

    private void sendJsonResponse(HttpServletResponse response, int statusCode, Map<String, Object> data) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(data));
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
        
    }

   
}