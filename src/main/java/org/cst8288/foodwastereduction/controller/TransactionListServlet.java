package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.businesslayer.TransactionBusiness;
import org.cst8288.foodwastereduction.model.Transaction;
import org.cst8288.foodwastereduction.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * The {@code TransactionListServlet} class is a servlet that handles HTTP GET requests
 * to retrieve and display a list of transactions for a specific user. It ensures that
 * the user is authenticated by checking the session. If the user is not authenticated,
 * it redirects to the login page. Otherwise, it fetches and sets the necessary attributes
 * for the JSP page to display a list of transactions, inventories, and food items.
 *
 * @author yaoyi
 */
public class TransactionListServlet extends HttpServlet {
    /**
     * Business logic object for managing inventory operations.
     */
    private InventoryBusiness inventoryBusiness = new InventoryBusiness();
    /**
     * Business logic object for managing food item operations.
     */
    private FoodItemBusiness foodItemBusiness = new FoodItemBusiness();
    /**
     * Business logic object for managing transaction operations.
     */
    private TransactionBusiness transactionBusiness = new TransactionBusiness();

    /**
     * Handles the HTTP GET request to fetch and display transactions for a specific user.
     * <p>
     * This method performs the following steps:
     * <ul>
     *     <li>Checks if the user is authenticated by verifying the session.</li>
     *     <li>Redirects to the login page if the user is not authenticated.</li>
     *     <li>Retrieves the user ID from the request parameters.</li>
     *     <li>Fetches lists of inventories, food items, and transactions from the respective business classes.</li>
     *     <li>Sets these lists as request attributes for the JSP page.</li>
     *     <li>Forwards the request to the JSP page for rendering.</li>
     * </ul>
     * </p>
     *
     * @param request  The {@code HttpServletRequest} object that contains the request the client has made
     *                 to the servlet.
     * @param response The {@code HttpServletResponse} object that contains the response the servlet sends
     *                 to the client.
     * @throws ServletException If an input or output error is detected when the servlet handles the GET request.
     * @throws IOException      If an input or output error is detected when the servlet handles the GET request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userIdStr = request.getParameter("userId");
        int userId = Integer.parseInt(userIdStr);

//        List<InventoryDTO> inventories = inventoryBusiness.getAllInventories();
//        List<FoodItemDTO> foodItems = foodItemBusiness.getAllFoodItems();
        List<Transaction> transactions = transactionBusiness.getTransactionByUser(userId);

//        // Set attributes for JSP
//        request.setAttribute("inventories", inventories);
//        request.setAttribute("foodItems", foodItems);
        request.setAttribute("transactions", transactions);

        // Forward to JSP page
        request.getRequestDispatcher("/views/transactionList.jsp").forward(request, response);
    }


}
