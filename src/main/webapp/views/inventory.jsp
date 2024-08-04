<%-- 
    Document   : inventory
    Created on : Jul 27, 2024, 5:42:11 PM
    Author     : WANG JIAYUN
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
        <nav>
            <ul>
                <li><a href="views/home.jsp">Home</a></li>
                <li>
                    <%
                        // Get the session attribute
                        User user = (User) session.getAttribute("user");
                        if (user != null) {
                            // Display welcome message and logout link if user is logged in
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
            // Get the list of inventories and food items from the request attribute
            List<InventoryDTO> inventories = (List<InventoryDTO>) request.getAttribute("inventories");
            List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
            // Check if the inventory list is empty or null
            if (inventories == null || inventories.isEmpty()) {
        %>
            <p>No Inventory Items Now!</p>
        <%
            } else {
        %>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Category</th>
                    <th>Quantity</th>
                    <th>Regular Price</th>
                    <th>Expiration Date</th>
                    <th>Is Active</th>
                    <th>Status</th>
                    <th>Actions</th>
                    <th> </th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Loop through each inventory item
                    for (InventoryDTO inventory : inventories) {
                        // Find the corresponding food item for the inventory
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
                        // Check if the inventory is active
                        if (inventory.getIsActive()) {
                    %>
                    <td><%= inventory.getIsSurplus() ? inventory.getSurplusStatus() : "N/A" %></td>
                    <td>
                        <%
                            // Check if the inventory is surplus and its status
                            if (inventory.getIsSurplus()) {
                                if (inventory.getSurplusStatus().name().equals("None")) {
                        %>
                            <button onclick="updateSurplusStatus(<%= inventory.getInventoryId() %>, 'Donation')">Donation</button>
                            <button onclick="updateSurplusStatus(<%= inventory.getInventoryId() %>, 'Discount')">Sale</button>
                        <%
                                } else {
                        %>
                            <span>Actions Already Made</span>
                        <%
                                }
                            } else {
                        %>
                            <span>No Action Need Now</span>
                        <%
                            }
                        %>
                    </td>
                    <td>
                        <button onclick="editInventory(<%= inventory.getInventoryId() %>)">Edit</button>
                    </td>
                    <%
                        } else {
                    %>
                    <td></td>
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
        <!-- Button to navigate to the add inventory page -->
        <button onclick="addInventory(<%= user.getUserID() %>)">Add Inventory</button>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
   
    <script>
        // Function to navigate to the add inventory page
        function addInventory(userId) {
            window.location.href = "inventoryAdd?userId=" + userId;
        }

        // Function to navigate to the edit inventory page
        function editInventory(inventoryId) {
            window.location.href = "inventoryUpdate?inventoryId=" + inventoryId;
        }

        // Function to update the surplus status of an inventory item
        function updateSurplusStatus(inventoryId, status) {
            $.ajax({
                url: 'inventoryStatus',
                type: 'GET',
                data: {
                    inventoryId: inventoryId,
                    status: status
                },
                success: function(response) {
                    if (response.success) {
                        var message = "Status updated successfully.\nNotified users are " + 
                                response.userTypeNotified  + ":\n" + 
                                response.notifiedUsers.join(", ");
                        alert(message);  
                        location.reload();
                    } else {
                        alert("Error: " + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    alert("An error occurred: " + error);
                }
            });
        }          
    </script>
</body>
</html>