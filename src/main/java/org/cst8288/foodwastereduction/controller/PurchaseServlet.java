package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
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
@WebServlet("/consumer/*")
public class PurchaseServlet extends HttpServlet {

    private final InventoryBusiness inventoryBusiness = new InventoryBusiness();
    private final TransactionBusiness transactionBusiness = new TransactionBusiness();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int inventoryID = Integer.parseInt(request.getParameter("inventoryID"));

        try {
            request.setAttribute("item", inventoryBusiness.getInventoryById(inventoryID));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/views/purchase.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            int inventoryID = Integer.parseInt(request.getParameter("inventoryID"));
            Integer quantity = Integer.parseInt(request.getParameter("quantity"));
            transactionBusiness.purchaseInventory(inventoryID, user.getUserID(), quantity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/consumer");
    }

}