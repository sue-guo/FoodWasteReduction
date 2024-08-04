<%-- 
    Document   : foodItem
    Created on : Jul 27, 2024, 5:42:11 PM
    Author     : WANG JIAYUN
--%>

<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page import="org.cst8288.foodwastereduction.model.FoodItemDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retailer's Food Item Management</title>
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
        <h2>Current Food Items</h2>
        <%
            // Get the list of food items from the request attribute
            List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
            // Check if the list is empty or null
            if (foodItems == null || foodItems.isEmpty()) {
        %>
            <p>No Food Items Now!</p>
        <%
            } else {
        %>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Category</th>
                    <th>Brand</th>
                    <th>Unit</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Loop through each food item and create a table row
                    for (FoodItemDTO foodItem : foodItems) {
                %>
                <tr>
                    <td><%= foodItem.getName() %></td>
                    <td><%= foodItem.getDescription() %></td>
                    <td><%= foodItem.getCategory() %></td>
                    <td><%= foodItem.getBrand() %></td>
                    <td><%= foodItem.getUnit() %></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
            }
        %>
        <!-- Button to navigate to the add food item page -->
        <button onclick="window.location.href='views/addFoodItem.jsp'">Add Food Item</button>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
</body>
</html>