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
import org.cst8288.foodwastereduction.constants.SurplusStatusEnum;

/**
 *
 * @author Carri
 */
public class InventoryUpdateServlet extends HttpServlet {


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
        int inventoryId = Integer.parseInt(request.getParameter("inventoryId").trim());
         
        InventoryBusiness inventoryBusiness = new InventoryBusiness();
        InventoryDTO inventory = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryId);
        int retailerId = inventory.getRetailerId();
        
        FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
        List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(retailerId);
        
        request.setAttribute("inventory", inventory);
        request.setAttribute("foodItems", foodItems);
        
        LMSLogger.getInstance().saveLogInformation("Fetched inventory and food items for inventoryId = " + inventoryId, InventoryUpdateServlet.class.getName(), LogLevel.INFO);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/updateInventory.jsp");
        dispatcher.forward(request, response);
        
        } catch (Exception ex) {
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryUpdateServlet doGet: " + ex.getMessage(), InventoryUpdateServlet.class.getName(), LogLevel.ERROR);
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
       
        try{
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

        InventoryBusiness inventoryBusiness = new InventoryBusiness();
        inventoryBusiness.updateInventory(inventory);
        
        LMSLogger.getInstance().saveLogInformation("Updated inventory for inventoryId = " + inventoryId, InventoryUpdateServlet.class.getName(), LogLevel.INFO);
       
        response.sendRedirect(request.getContextPath() + "/inventory?userId=" + userId);
           
        } catch (Exception ex) {
            LMSLogger.getInstance().saveLogInformation("Exception in InventoryUpdateServlet doPost: " + ex.getMessage(), InventoryUpdateServlet.class.getName(), LogLevel.ERROR);
            throw new ServletException(ex);
        }
    }

   

}
