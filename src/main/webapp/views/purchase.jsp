<%--
  Created by IntelliJ IDEA.
  User: yaoyi
  Date: 2024-07-25
  Time: 6:01 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%--Modify this file,
dynamically get values from database and print rows,
and contain table and popup window in the same form--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase</title>
    <link rel="stylesheet" href="../styles/purchase.css">
</head>
<body>
<header>
    <h1>Food Waste Reduction Platform</h1>
    <nav>
        <ul>
            <li><a href="home.jsp">Home</a></li>
            <li><a href="purchase.jsp">Purchase</a></li>
            <li><a href="subscribe.jsp">Subscribe</a></li>
        </ul>
    </nav>
</header>
<main>
    <h2>Food Items</h2>
    <table id="purchase-table">
        <thead>
        <tr>
            <th>Item Name</th>
            <th>Available Qty</th>
            <th>Regular Price</th>
            <th>Discounted Price</th>
            <th>Expiration Date</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>

        <%-- examples of the list --%>
        <tr>
            <td>Apple</td>
            <td>50</td>
            <td>2.49</td>
            <td>1.99</td>
            <td>2024-01-01</td>
            <td>
                <button onclick="document.getElementById('purchase-window').style.display='block'">Purchase</button>
            </td>
        </tr>
        <tr>
            <td>Orange</td>
            <td>20</td>
            <td>3.99</td>
            <td>2.99</td>
            <td>2024-01-01</td>
            <td>
                <button>Purchase</button>
            </td>
        </tr>
        <tr>
            <td>Chili</td>
            <td>30</td>
            <td>4.29</td>
            <td>2.69</td>
            <td>2024-01-01</td>
            <td>
                <button>Purchase</button>
            </td>
        </tr>
        <tr>
            <td>Bread</td>
            <td>20</td>
            <td>3.89</td>
            <td>2.49</td>
            <td>2024-01-01</td>
            <td>
                <button>Purchase</button>
            </td>
        </tr>
        <tr>
            <td>Milk</td>
            <td>40</td>
            <td>7.99</td>
            <td>4.99</td>
            <td>2024-01-01</td>
            <td>
                <button>Purchase</button>
            </td>
        </tr>
        </tbody>
    </table>
    <br>
    <button onclick="purchaseHistory()">Purchase History</button>

    <!-- Popup window for consumers to edit quantity and confirm purchase -->
    <div id="purchase-window" class="window">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('purchase-window').style.display='none'">&times;</span>
            <br>
            <br>
            <h3>Please enter the quantity you wish to purchase</h3>
            <br>
            <label for="purchase-qty">Quantity:</label>
            <input type="number" id="purchase-qty" name="purchase-qty" required>
            <br>
            <br>
            <br>
            <button onclick="makePurchase()">Confirm Purchase</button>
        </div>
    </div>
</main>
<footer>
    <p>&copy; 2024 Food Waste Reduction Platform</p>
</footer>
</body>
</html>