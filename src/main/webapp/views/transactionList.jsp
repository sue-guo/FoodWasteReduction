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
    <table id="order-table">
        <thead>
        <%-- Table header row --%>
        <tr>
            <th>OrderID</th>
            <th>Item Name</th>
            <th>Retailer</th>
            <th>Regular Price</th>
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
            List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
            DecimalFormat df = new DecimalFormat("#.00");
            if( transactions!=null && transactions.size() != 0 ){
            
                for (Transaction transaction : transactions) {
               
                    if (transaction.getTransactionType() == TransactionType.Purchase) {

        %>
        <tr>
            <td><%= transaction.getTransactionID() %></td>
            <td><%= transaction.getFoodItem() %></td>
            <td><%= transaction.getRetailer() %></td>
            <td><%= transaction.getRegularPrice() %></td>
            <td><%= transaction.getQuantity() %></td>
            <td><%= df.format(transaction.getTotalAmount()) %></td>
            <td><%= transaction.getTransactionDate() %></td>
            <td><%= transaction.getPayStatus() %></td>
            <td>
                <% if(transaction.getPayStatus().equalsIgnoreCase("unpaid")){   %>
                <button type="button" onclick="window.location.href= '${pageContext.request.contextPath}/payment?transactionId=<%= transaction.getTransactionID() %>&total=<%= transaction.getTotalAmount() %>'">Pay</button>
                <% } %>
            </td>
        </tr>
        <%
                    }else{                   
         %>           
        <tr>
            <td><%= transaction.getTransactionID() %></td>
            
            <td><%= transaction.getFoodItem() %></td>
            <td><%= transaction.getRetailer() %></td>
            <td><%= transaction.getRegularPrice() %></td>
            <td><%= transaction.getQuantity() %></td>
            <td>None</td>
            <td><%= transaction.getTransactionDate() %></td>
            <td>No Need</td>
            <td>
                
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