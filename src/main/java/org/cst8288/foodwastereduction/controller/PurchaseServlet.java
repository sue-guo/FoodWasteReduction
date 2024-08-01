package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
import org.cst8288.foodwastereduction.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//@WebServlet(value = "/purchase")
public class PurchaseServlet extends HttpServlet {

    private final InventoryBusiness inventoryBusiness = new InventoryBusiness();
    private final FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
    private final TransactionBusiness transactionBusiness = new TransactionBusiness();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String inventoryIdStr = request.getParameter("inventoryId");
            int inventoryId = Integer.parseInt(inventoryIdStr);
            InventoryDTO inventory = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryId);
            if (inventory != null) {
                FoodItemDTO foodItem = foodItemBusiness.getFoodItemById(inventory.getFoodItemId());

                request.setAttribute("inventory", inventory);
                request.setAttribute("foodItem", foodItem);
                request.getRequestDispatcher("/views/purchase.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Inventory not found");
            }
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
            String inventoryIdStr = request.getParameter("inventoryId");
            int inventoryId = Integer.parseInt(inventoryIdStr);
            Integer quantity = Integer.parseInt(request.getParameter("quantity"));

            InventoryDTO inventory = (InventoryDTO) inventoryBusiness.getInventoryById(inventoryId);

            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryBusiness.updateInventory(inventory);

            Transaction transaction = new Transaction();
            transaction.setInventoryID(inventoryId);
            transaction.setUserID(user.getUserID());
            transaction.setQuantity(quantity);
            transaction.setTransactionType(TransactionType.Purchase);
            transactionBusiness.addTransaction(transaction);

            response.sendRedirect(request.getContextPath() + "/consumer?userId=" + user.getUserID());
        } catch (NumberFormatException e) {
            Logger.getLogger(PurchaseServlet.class.getName()).log(Level.SEVERE, "Invalid inventoryId or quantity format", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid inventory ID or quantity");
        }
    }


}