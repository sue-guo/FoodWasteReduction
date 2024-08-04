<%-- 
    Document   : updateInventory
    Created on : Jul 27, 2024, 5:42:11 PM
    Author     : WANG JIAYUN
--%>

<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page import="org.cst8288.foodwastereduction.model.FoodItemDTO"%>
<%@page import="org.cst8288.foodwastereduction.model.InventoryDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Update Page</title>
        <base href="${pageContext.request.contextPath}/" />
        <link rel="stylesheet" href="styles/add.css">
    </head>
    
    <body>
        <header>
            <h1>Food Waste Reduction Platform</h1>
            <nav>
                <ul>
                    <li><a href="views/home.jsp">Home</a></li>
                    <li>
                        <%
                            // Retrieve the user from the session attribute
                            User user = (User) session.getAttribute("user");
                            if (user != null) {
                                // If the user is logged in, display a welcome message and a logout link
                                out.print("<p>Welcome, " + user.getName() + "! </p>" + 
                                "<a href=\"logout\" class=\"logout\">[ Logout ]</a>");
                            }
                        %>
                    </li>
                </ul>
            </nav>
        </header>
                    
        <main>
            <form action="inventoryUpdate" method="post">
              
                <div>
                    <label for="foodItemId">Food Item:</label>
                    <select id="foodItemId" name="foodItemId" required>
                        <%
                            // Retrieve the list of food items and the inventory from the request attribute
                            List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
                            InventoryDTO inventory = (InventoryDTO) request.getAttribute("inventory");
                            // Loop through each food item to populate the dropdown
                            for (FoodItemDTO foodItem : foodItems) {
                                // Check if the current food item is the one associated with the inventory
                                String selected = foodItem.getFoodItemId() == inventory.getFoodItemId() ? "selected" : "";
                                // Output the option element for the dropdown
                                out.print("<option value=\"" + foodItem.getFoodItemId() + "\" " + selected + ">" + foodItem.getName() + "</option>");
                            }
                        %>
                    </select>
                </div>
                
                <div>
                    <label for="batchNumber">Batch Number:</label>
                    <!-- Input field for batch number, pre-filled with the current inventory's batch number -->
                    <input type="text" id="batchNumber" name="batchNumber" value="${inventory.batchNumber}" required>
                </div>
                
                <div>
                    <label for="quantity">Quantity:</label>
                    <!-- Input field for quantity, pre-filled with the current inventory's quantity -->
                    <input type="number" id="quantity" name="quantity" value="${inventory.quantity}" required>
                </div>
                
                <div>
                    <label for="regularPrice">Regular Price:</label>
                    <!-- Input field for regular price, pre-filled with the current inventory's regular price -->
                    <input type="number" step="0.01" id="regularPrice" name="regularPrice" value="${inventory.regularPrice}" required>
                </div>
                
                <div>
                    <label for="discountRate">Discount Rate:</label>
                    <!-- Input field for discount rate, pre-filled with the current inventory's discount rate -->
                    <input type="number" step="0.01" id="discountRate" name="discountRate" value="${inventory.discountRate}" required>
                </div>
                
                <div>
                    <label for="expirationDate">Expiration Date:</label>
                    <!-- Input field for expiration date, pre-filled with the current inventory's expiration date -->
                    <input type="date" id="expirationDate" name="expirationDate" value="${inventory.expirationDate}" required>
                </div>
                
                <div>
                    <label for="receiveDate">Receive Date:</label>
                    <!-- Input field for receive date, pre-filled with the current inventory's receive date -->
                    <input type="date" id="receiveDate" name="receiveDate" value="${inventory.receiveDate}" required>
                </div>
                
                <!-- Hidden fields to store additional inventory information -->
                <input type="hidden" name="inventoryId" value="${inventory.inventoryId}">
                
                <!-- Hidden field to store the user ID if the user is logged in -->
                <input type="hidden" name="userId" value="<%= user != null ? user.getUserID() : "" %>">
                
                <input type="hidden" name="isSurplus" value="${inventory.isSurplus}">
                
                <input type="hidden" name="surplusStatus" value="${inventory.surplusStatus}">
                
                <input type="hidden" name="isActive" value="${inventory.isActive}">
                
                <!-- Submit button to update the inventory -->
                <button type="submit">Update Inventory</button>
            </form>
        </main>
    </body>
</html>