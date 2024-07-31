<%@ page import="org.cst8288.foodwastereduction.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.cst8288.foodwastereduction.model.InventoryDTO" %>
<%@ page import="org.cst8288.foodwastereduction.model.SurplusStatusEnum" %>
<%@ page import="org.cst8288.foodwastereduction.model.FoodItemDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: yaoyi
  Date: 2024-07-25
  Time: 6:01 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consumer page</title>
    <link rel="stylesheet" href="styles/consumer.css">
</head>
<body>
<header>
    <h1>Food Waste Reduction Platform</h1>
    <nav>
        <ul>
            <li><a href="home.jsp">Home</a></li>
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
    <h2>Discounted Food Items</h2>
    <%
        List<InventoryDTO> inventories = (List<InventoryDTO>) request.getAttribute("inventories");
        List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
    %>

    <table id="purchase-table">
        <thead>
        <%-- Table header row --%>
        <tr>
            <th>Item Name</th>
            <th>Available Qty</th>
            <th>Regular Price</th>
            <th>Discounted Price</th>
            <th>Expiration Date</th>
            <th>Operation</th>
        </tr>
        </thead>
        <%-- Table body --%>
        <tbody>
        <%
            for (InventoryDTO inventory : inventories) {
                FoodItemDTO foodItem = foodItems.stream()
                        .filter(item -> item.getFoodItemId().equals(inventory.getFoodItemId()))
                        .findFirst()
                        .orElse(null);
                if (inventory != null && inventory.getSurplusStatus() == SurplusStatusEnum.Discount) {
                    assert foodItem != null;
        %>
        <tr>
            <td><%= foodItem.getName() %></td>
            <td><%= inventory.getQuantity() %></td>
            <td><%= inventory.getRegularPrice() %></td>
            <td><%= inventory.getRegularPrice() * inventory.getDiscountRate() %></td>
            <td><%= inventory.getExpirationDate() %></td>
            <td>
                <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/consumer/purchase?inventoryId=<%= inventory.getFoodItemId() %>'">Purchase</button>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>>
</main>
<footer>
    <p>&copy; 2024 Food Waste Reduction Platform</p>
</footer>
</body>
</html>