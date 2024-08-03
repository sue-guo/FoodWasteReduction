<%@ page import="java.util.List" %>
<%@ page import="org.cst8288.foodwastereduction.model.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%--
  File: transactionList.jsp
  Created by IntelliJ IDEA.
  User: yaoyi
  Date: 2024-08-02
  Time: 4:49 p.m.
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
    <h2>Orders History</h2>
    <%
        List<InventoryDTO> inventories = (List<InventoryDTO>) request.getAttribute("inventories");
        List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
        List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
    %>

    <table id="order-table">
        <thead>
        <%-- Table header row --%>
        <tr>
            <th>OrderID</th>
            <th>Item Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total Amount</th>
            <th>Date</th>
            <th>Payment Status</th>
            <th>Operation</th>
        </tr>
        </thead>
        <%-- Table body --%>
        <tbody>
        <%
            DecimalFormat df = new DecimalFormat("#.00");
            for (Transaction transaction : transactions) {
                InventoryDTO inventory = inventories.stream()
                        .filter(inv -> inv.getInventoryId().equals(transaction.getInventoryID()))
                        .findFirst()
                        .orElse(null);

                if (inventory != null) {
                    FoodItemDTO foodItem = foodItems.stream()
                            .filter(item -> item.getFoodItemId().equals(inventory.getFoodItemId()))
                            .findFirst()
                            .orElse(null);

                    if (transaction.getTransactionType() == TransactionType.Purchase) {
                        assert foodItem != null;
                        assert user != null;
                        double price = inventory.getRegularPrice() * inventory.getDiscountRate();
                        double totalAmount = price * transaction.getQuantity();
        %>
        <tr>
            <td><%= transaction.getTransactionID() %></td>
            <td><%= foodItem.getName() %></td>
            <td><%= price %></td>
            <td><%= transaction.getQuantity() %></td>
            <td><%= df.format(totalAmount) %></td>
            <td><%= transaction.getTransactionDate() %></td>
            <td><%= transaction.getPayStatus() %></td>
            <td>
                <% if(transaction.getPayStatus().equalsIgnoreCase("unpaid")){   %>
                <button type="button" onclick="window.location.href= '${pageContext.request.contextPath}/payment?transactionId=<%= transaction.getTransactionID() %>&total=<%= totalAmount %>'">Pay</button>
                <% } %>
            </td>
        </tr>
        <%
                    }
                }
            }
        %>
        </tbody>
    </table>
</main>
<footer>
    <p>&copy; 2024 Food Waste Reduction Platform</p>
</footer>
</body>
</html>