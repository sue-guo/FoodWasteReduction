<%-- 
    Document   : inventory
    Created on : [Current Date]
    Author     : [Your Name]
--%>
<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page import="org.cst8288.foodwastereduction.model.InventoryDTO"%>
<%@page import="org.cst8288.foodwastereduction.model.FoodItemDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retailer's Inventory Management</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/fooditemlist.css">
</head>
<body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
        <nav>
            <ul>
                <li><a href="views/home.jsp">Home</a></li>
                <li>
                    <%
                        User user = (User) session.getAttribute("user");
                        if (user != null) {
                            out.print("<p>Welcome, " + user.getName() + "! </p>" + 
                            "<a href=\"logout\" class=\"logout\">[ Logout ]</a>");
                        }
                    %>
                </li>
            </ul>
        </nav>
    </header>
    
    <main>
        <h2>Current Inventory</h2>
        <%
            List<InventoryDTO> inventories = (List<InventoryDTO>) request.getAttribute("inventories");
            List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
            if (inventories == null || inventories.isEmpty()) {
        %>
            <p>No Inventory Items Now!</p>
        <%
            } else {
        %>
        <table>
            <thead>
                <tr>
                    <th>Food Item Name</th>
                    <th>Category</th>
                    <th>Quantity</th>
                    <th>Regular Price</th>
                    <th>Expiration Date</th>
                    <th>Is Active</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (InventoryDTO inventory : inventories) {
                        FoodItemDTO foodItem = foodItems.stream()
                            .filter(item -> item.getFoodItemId().equals(inventory.getFoodItemId()))
                            .findFirst()
                            .orElse(null);
                        if (foodItem != null) {
                %>
                <tr>
                    <td><%= foodItem.getName() %></td>
                    <td><%= foodItem.getCategory() %></td>
                    <td><%= inventory.getQuantity() %></td>
                    <td><%= inventory.getRegularPrice() %></td>
                    <td><%= inventory.getExpirationDate() %></td>
                    <td><%= inventory.getIsActive() ? "Yes" : "No" %></td>
                    
                     <%
                           if (inventory.getIsActive()) {
                     %>
                    <td>
                       <%
                           if (inventory.getIsSurplus()) {
                        %>
                        <button onclick="updateSurplusStatus(<%= inventory.getInventoryId() %>, 'Donation')">Donation</button>
                        <button onclick="updateSurplusStatus(<%= inventory.getInventoryId() %>, 'Discount')">Sale</button>
                        <%
                            } else {
                        %>
                        <span>None</span>
                        <%
                            }
                        %>
                    </td>
                    <td>
                        <button onclick="editInventory(<%= inventory.getInventoryId() %>)">Edit</button>
                    </td>
                    <%
                            }else{
                    %>
                    
                    <td></td>
                    <td></td>
                   
                    <%
                               }
                    %>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
        <%
            }
        %>
        <button onclick="loadFoodItem(<%= user.getUserID() %>)">Add Inventory</button>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
   
    <script>
        function loadFoodItem(userId) {
            // load the food item management page
            window.location.href = "inventoryAdd?userId="+userId;
        }
          function editInventory(inventoryId) {
            // redirect to the InventoryUpdateServlet with the inventoryId
            window.location.href = "inventoryUpdate?inventoryId=" + inventoryId;
        }

        function updateSurplusStatus(inventoryId, status) {
            // redirect to the InventoryStatusServlet with the inventoryId and status
            window.location.href = "inventoryStatus?inventoryId=" + inventoryId + "&status=" + status;
        }
    </script>
</body>
</html>