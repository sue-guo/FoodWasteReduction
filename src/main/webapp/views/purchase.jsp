<%@ page import="org.cst8288.foodwastereduction.model.User" %>
<%@ page import="org.cst8288.foodwastereduction.model.InventoryDTO" %>
<%@ page import="org.cst8288.foodwastereduction.model.FoodItemDTO" %><%--
  Created by IntelliJ IDEA.
  User: yaoyi
  Date: 2024-07-30
  Time: 1:45 a.m.
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Purchase page</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/purchase.css">
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
    <h2>Purchase Details</h2>
    <%
        InventoryDTO inventory = (InventoryDTO) request.getAttribute("inventory");
        FoodItemDTO foodItem = (FoodItemDTO) request.getAttribute("foodItem");
        if (inventory != null) {
    %>
    <form action="${pageContext.request.contextPath}/consumer/purchase?userId=<%= user.getUserID() %>" method="post">
        <input type="hidden" name="inventoryId" value="<%= inventory.getInventoryId() %>">
        <p><strong>Item Name:</strong> <%= foodItem.getName() %></p>
        <p><strong>Description:</strong> <%= foodItem.getDescription() %></p>
        <p><strong>Regular Price:</strong> <%= inventory.getRegularPrice() %></p>
        <p><strong>Discounted Price:</strong> <%= inventory.getRegularPrice() * inventory.getDiscountRate() %></p>
        <p><strong>Quantity Available:</strong> <%= inventory.getQuantity() %></p>
        <br>
        <label for="quantity">Enter the quantity you want to purchase:</label>
        <input type="number" id="quantity" name="quantity" min="1" max="<%= inventory.getQuantity() %>" required>
        <br>
        <button type="submit">Purchase</button>
    </form>
    <%
    } else {
    %>
    <p>Item not found.</p>
    <%
        }
    %>
</main>
<footer>
    <p>&copy; 2024 Food Waste Reduction Platform</p>
</footer>
</body>
</html>