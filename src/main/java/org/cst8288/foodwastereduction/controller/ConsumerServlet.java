package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@WebServlet("/consumer")
public class ConsumerServlet extends HttpServlet {
    private InventoryBusiness inventoryBusiness = new InventoryBusiness();
    private FoodItemBusiness foodItemBusiness = new FoodItemBusiness();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userIdStr = request.getParameter("userId");
        int userId = Integer.parseInt(userIdStr);

        List<InventoryDTO> inventories = inventoryBusiness.getAllInventories();
        List<FoodItemDTO> foodItems = foodItemBusiness.getAllFoodItems();

        // Set attributes for JSP
        request.setAttribute("inventories", inventories);
        request.setAttribute("foodItems", foodItems);

        // Forward to JSP page
        request.getRequestDispatcher("/views/consumer.jsp").forward(request, response);
    }
}
