/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.RequestDispatcher;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;

/**
 *
 * @author Carri
 */
public class InventoryServlet extends HttpServlet {

  
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
     
   
       int userId = Integer.parseInt(request.getParameter("userId").trim());
       InventoryBusiness inventoryBusiness = new InventoryBusiness();
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

        // Update isSurplus and isActive fields
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
             inventoryBusiness.updateInventory(inventory);
        }
        
      
       request.setAttribute("inventories", inventories);
       
       FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
       List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
       request.setAttribute("foodItems", foodItems);
       
   
       RequestDispatcher dispatcher = request.getRequestDispatcher("views/inventory.jsp");
       dispatcher.forward(request, response);
        
       
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
