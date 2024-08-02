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
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.SurplusStatusEnum;

/**
 *
 * @author Carri
 */
public class InventoryStatusServlet extends HttpServlet {

  

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

        int inventoryId = Integer.parseInt(inventoryIdParam);
        SurplusStatusEnum status = SurplusStatusEnum.valueOf(statusParam);
        
        InventoryBusiness inventoryBusiness = new InventoryBusiness();
         // Update the surplus status of the inventory item
         InventoryDTO inventory = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryId);
               
         inventory.setSurplusStatus(status);
         inventoryBusiness.updateInventory(inventory);

          // Redirect back to the inventory page
         response.sendRedirect(request.getContextPath() + "/inventory?userId=" + inventory.getRetailerId());
  
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
