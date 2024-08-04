/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
 *
 * @author Carri
 */
public class InventoryAddServlet extends HttpServlet {

  

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
       try{  
       int userId = Integer.parseInt(request.getParameter("userId").trim());
      
       FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
       List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
       request.setAttribute("foodItems", foodItems);
       
       LMSLogger.getInstance().saveLogInformation("Fetched food items for userId = " + userId, InventoryAddServlet.class.getName(), LogLevel.INFO);
       
       RequestDispatcher dispatcher = request.getRequestDispatcher("views/addInventory.jsp");
       dispatcher.forward(request, response);
            
      
        } catch (Exception ex) {
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryAddServlet doGet: " + ex.getMessage(), InventoryAddServlet.class.getName(), LogLevel.ERROR);
            throw new ServletException(ex);
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


        // Create a new FoodItemDTO object
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
        
         LMSLogger.getInstance().saveLogInformation("Added inventory for userId = " + userId, InventoryAddServlet.class.getName(), LogLevel.INFO);
        
        // Redirect to list all current inventory
        // Redirect to InventoryServlet
        response.sendRedirect(request.getContextPath() + "/inventory?userId=" + userId);
                
        } catch (Exception ex) {
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryAddServlet doPost: " + ex.getMessage(), InventoryAddServlet.class.getName(), LogLevel.ERROR);
            throw new ServletException(ex);
        }
    }

   
}
