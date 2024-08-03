/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import java.util.ArrayList;
import java.util.List;
import org.cst8288.foodwastereduction.constants.CategoryEnum;

/**
 *
 * @author WANG JIAYUN
 */
public class FoodItemServlet extends HttpServlet {

       
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
        
        FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
        List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
        
        request.setAttribute("foodItems", foodItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/foodItem.jsp");
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
        
        int userId = Integer.parseInt(request.getParameter("userId").trim());
        String name = request.getParameter("name").trim();
        String description = request.getParameter("description").trim();
        String category = request.getParameter("category").trim();
        String brand = request.getParameter("brand").trim();
        String unit = request.getParameter("unit").trim();

        FoodItemDTO foodItem = new FoodItemDTO();
        foodItem.setRetailerId(userId);
        foodItem.setName(name);
        foodItem.setDescription(description);
        foodItem.setCategory(CategoryEnum.valueOf(category));
        foodItem.setBrand(brand);
        foodItem.setUnit(unit);
        
        FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
        foodItemBusiness.addFoodItem(foodItem);
        
   
        // Redirect to list all current food items
        response.sendRedirect(request.getContextPath() + "/foodItem?userId=" + userId);
        
   
    }
    


}
