<%-- 
    Document   : home
    Created on : Jul. 24, 2024, 11:38:27 a.m.
    Author     : Hongxiu Guo
--%>

<%@page import="org.cst8288.foodwastereduction.model.UserType"%>
<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory Management System</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/home.css">
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
                            out.print("<p>Welcome, " + user.getName() + "! </p>" + 
                            "<a href=\"logout\" class=\"logout\">[ Logout ]</a>");
                        }
                    %>
                
                </li>
            </ul>

        </nav>
    </header>
    <main>
        <h2>Welcome to the Food Inventory Management System</h2>
        <p>Manage your food inventory efficiently and reduce waste.</p>
        
        <% 
            if (user != null && user.getUserType() == UserType.RETAILER) {
        %>
            <button onclick="loadInventory(<%= user.getUserID() %>)">Inventory Management</button>
            <button onclick="loadFoodItem(<%= user.getUserID() %>)"> Food Item Management</button>
        <%
            }
         %>
        <% 
            if (user != null && user.getUserType() == UserType.CONSUMER) {
        %>
            <button onclick="loadInventory(<%= user.getUserID() %>)">Inventory Management</button>
            <button onclick="loadFoodItem(<%= user.getUserID() %>)"> Food Item Management</button>
        <%
            }
         %>

    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script>
        function loadInventory(userId) {
            // load the inventory management page
            window.location.href = "inventory?userId="+userId;
        }

        function loadFoodItem(userId) {
            // load the food item management page
            window.location.href = "foodItem?userId="+userId;
        }
    </script>
</body>
</html>