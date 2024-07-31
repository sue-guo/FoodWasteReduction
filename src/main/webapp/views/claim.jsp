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
    <title>Claim page</title>
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
    <%
        InventoryDTO inventory = (InventoryDTO) request.getAttribute("inventory");
        FoodItemDTO foodItem = (FoodItemDTO) request.getAttribute("foodItem");
        if (inventory != null) {
    %>
    <form action="${pageContext.request.contextPath}/charitableOrganization/claim?userId=<%= user.getUserID() %>" method="post">
        <input type="hidden" name="inventoryId" value="<%= inventory.getInventoryId() %>">
        <p><strong>Item Name:</strong> <%= foodItem.getName() %></p>
        <p><strong>Regular Price:</strong> <%= inventory.getRegularPrice() %></p>
        <p><strong>Quantity:</strong> <%= inventory.getQuantity() %></p>
        <br>
        <button type="submit">Claim</button>
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