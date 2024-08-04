package org.cst8288.foodwastereduction.controller;

import org.cst8288.foodwastereduction.businesslayer.FoodItemBusiness;
import org.cst8288.foodwastereduction.businesslayer.InventoryBusiness;
import org.cst8288.foodwastereduction.logger.LMSLogger;
import org.cst8288.foodwastereduction.logger.LogLevel;
import org.cst8288.foodwastereduction.model.FoodItemDTO;
import org.cst8288.foodwastereduction.model.InventoryDTO;
import org.cst8288.foodwastereduction.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Handles HTTP GET requests for the consumer interface of the application.
 * This servlet processes GET requests by checking user authentication,
 * retrieving inventory and food item data, and forwarding the request
 * to the appropriate JSP page for rendering.
 *
 * <p>It utilizes {@link InventoryBusiness} and {@link FoodItemBusiness}
 * to fetch the necessary data and sets this data as request attributes
 * before forwarding the request to {@code /views/consumer.jsp}.</p>
 *
 * @author yaoyi
 */
public class ConsumerServlet extends HttpServlet {
    /**
     * Business logic object for managing inventory operations.
     */
    private InventoryBusiness inventoryBusiness = new InventoryBusiness();
    /**
     * Business logic object for managing food item operations.
     */
    private FoodItemBusiness foodItemBusiness = new FoodItemBusiness();

    /**
     * Handles HTTP GET requests to retrieve and display inventory and food items.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Checks if the user is authenticated by retrieving the user
     *       object from the HTTP session.</li>
     *   <li>If the user is not authenticated, redirects to the login page.</li>
     *   <li>Parses the user ID from request parameters.</li>
     *   <li>Retrieves all inventory items and food items from the business logic.</li>
     *   <li>Sets the retrieved data as request attributes.</li>
     *   <li>Forwards the request to the {@code /views/consumer.jsp}
     *       JSP page for rendering.</li>
     * </ul>
     *
     * @param request  the {@link HttpServletRequest} object that contains the
     *                 request the client has made of the servlet
     * @param response the {@link HttpServletResponse} object that will
     *                 contain the response the servlet sends to the client
     * @throws ServletException if an input or output error is detected
     *                           when the servlet handles the GET request
     * @throws IOException      if the request cannot be handled or if
     *                           an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            LMSLogger.getInstance().saveLogInformation("User not found", this.getClass().getName(), LogLevel.ERROR);
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
