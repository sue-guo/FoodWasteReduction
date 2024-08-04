package org.cst8288.foodwastereduction.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import java.util.List;
import org.cst8288.foodwastereduction.constants.CategoryEnum;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;

/**
 * Servlet class for handling food item-related requests.
 * This servlet handles both GET and POST requests to manage food items.
 * 
 * @version 1.0
 * @since 2024-07-27
 * @author WANG JIAYUN
 * 
 */
public class FoodItemServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves food items for a specific retailer and forwards the request to the view.
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
            // Parse userId from request parameters
            int userId = Integer.parseInt(request.getParameter("userId").trim());
            
            // Create an instance of FoodItemBusiness to interact with the business layer
            FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
            // Retrieve the list of food items for the given retailer ID
            List<FoodItemDTO> foodItems = foodItemBusiness.getFoodItemsByRetailerID(userId);
            
            // Set the food items as a request attribute to be accessed in the JSP
            request.setAttribute("foodItems", foodItems);
            // Forward the request to the foodItem.jsp view
            RequestDispatcher dispatcher = request.getRequestDispatcher("views/foodItem.jsp");
            dispatcher.forward(request, response);
                  
            // Log the successful retrieval of food items
            LMSLogger.getInstance().saveLogInformation("Fetched food items for userId = " + userId, FoodItemServlet.class.getName(), LogLevel.INFO);
        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in FoodItemServlet doGet: " + ex.getMessage(), FoodItemServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Adds a new food item to the database and redirects to the list of food items.
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
            // Parse parameters from the request
            int userId = Integer.parseInt(request.getParameter("userId").trim());
            String name = request.getParameter("name").trim();
            String description = request.getParameter("description").trim();
            String category = request.getParameter("category").trim();
            String brand = request.getParameter("brand").trim();
            String unit = request.getParameter("unit").trim();

            // Create a new FoodItemDTO object and set its properties
            FoodItemDTO foodItem = new FoodItemDTO();
            foodItem.setRetailerId(userId);
            foodItem.setName(name);
            foodItem.setDescription(description);
            foodItem.setCategory(CategoryEnum.valueOf(category));
            foodItem.setBrand(brand);
            foodItem.setUnit(unit);
            
            // Create an instance of FoodItemBusiness to interact with the business layer
            FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
            // Add the new food item to the database
            foodItemBusiness.addFoodItem(foodItem);
            
            // Log the successful addition of the food item
            LMSLogger.getInstance().saveLogInformation("Added food item for userId = " + userId, FoodItemServlet.class.getName(), LogLevel.INFO);
            // Redirect to list all current food items
            response.sendRedirect(request.getContextPath() + "/foodItem?userId=" + userId);

        } catch (Exception ex) {
            // Log any exceptions that occur during the process
            LMSLogger.getInstance().saveLogInformation("Exception in FoodItemServlet doPost: " + ex.getMessage(), FoodItemServlet.class.getName(), LogLevel.ERROR);
            // Rethrow the exception as a ServletException
            throw new ServletException(ex);
        }
    }
}