/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import java.util.ArrayList;
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
       request.setAttribute("inventories", inventories);
       
       FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
       List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
       request.setAttribute("foodItems", foodItems);
       
       
     // set the is surplus to true if the expiretiondate is within 7 days from toda
       
       
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
