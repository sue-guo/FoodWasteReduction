package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
import org.cst8288.foodwastereduction.model.User;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class TransactionServlet extends HttpServlet {

    private final InventoryBusiness inventoryBusiness = new InventoryBusiness();
    private final TransactionBusiness transactionBusiness = new TransactionBusiness();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        int inventoryID = Integer.parseInt(request.getParameter("inventoryID"));

        try {
            request.setAttribute("item", inventoryBusiness.getInventoryById(inventoryID));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if ("/claim".equals(action)) {
            request.getRequestDispatcher("/views/claim.jsp").forward(request, response);
        } else if ("/purchase".equals(action)) {
            request.getRequestDispatcher("/views/purchase.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String action = request.getPathInfo();
        Integer inventoryID = Integer.parseInt(request.getParameter("inventoryID"));
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            if ("/claim".equals(action)) {
                transactionBusiness.claimInventory(inventoryID, user.getUserID(), quantity);
                response.sendRedirect(request.getContextPath() + "/charitableOrganization");
            } else if ("/purchase".equals(action)) {
                transactionBusiness.purchaseInventory(inventoryID, user.getUserID(), quantity);
                response.sendRedirect(request.getContextPath() + "/consumer");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}