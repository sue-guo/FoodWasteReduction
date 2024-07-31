package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author yaoyi
 */
//@WebServlet(value = "/charitableOrganization/claim")
public class ClaimServlet extends HttpServlet {

    private final InventoryBusiness inventoryBusiness = new InventoryBusiness();
    private final FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
    private final TransactionBusiness transactionBusiness = new TransactionBusiness();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int inventoryID = Integer.parseInt(request.getParameter("inventoryId"));
            InventoryDTO inventory = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryID);
            FoodItemDTO foodItem = (FoodItemDTO) foodItemBusiness.getFoodItemsByRetailerID(inventory.getRetailerId());

            request.setAttribute("inventory", inventory);
            request.setAttribute("foodItem", foodItem);
            request.getRequestDispatcher("/views/claim.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            Integer inventoryID = Integer.parseInt(request.getParameter("inventoryId"));
            transactionBusiness.claimInventory(inventoryID, user.getUserID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/charitableOrganization");
    }


}

