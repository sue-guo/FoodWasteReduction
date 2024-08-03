<%-- 
    Document   : payment
    Created on : Jul. 30, 2024, 3:10:02 p.m.
    Author     : Hongxiu Guo
--%>

<%@page import="org.cst8288.foodwastereduction.model.Transaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.cst8288.foodwastereduction.model.UserType"%>
<%@page import="org.cst8288.foodwastereduction.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Inventory Management System</title>
    <base href="${pageContext.request.contextPath}/" />
        <link rel="stylesheet" href="styles/payment.css">
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
        <h2>Payment</h2>
        <form action="payment" method="post">
            <div>
               <%
                Transaction transaction = (Transaction)request.getAttribute("transaction");
              %>
                <label for="amount">Amount:</label>
                <input type="text" id="amount" name="amount" readonly value="<%= request.getParameter("total") %>">
                <input type="text" id="transactionId" name="transactionId" hidden value="<%= transaction.getTransactionID() %>">
            </div>
            <div>
                <label for="paymentType">Payment Method:</label>
                <select id="paymentType" name="paymentType" required>
                    <option value="">Please select a payment method</option>
                    <option value="check">Check</option>
                    <option value="credit_card">Credit Card</option>
                    <option value="paypal">PayPal</option>
                    <option value="apple_pay">Apple Pay</option>
                </select><br><br>
            </div>
            <!-- Check Payment Details -->
             <div id="checkDetails" class="payment-details" style="display:none;">
                <div>
                    <label for="checkNumber">Check Number*:</label>
                    <input type="text" id="checkNumber" name="checkNumber" required>
                </div>
                <div>
                    <label for="bankName">Bank Name*:</label>
                    <input type="text" id="bankName" name="bankName" required>
                </div>
                <div>
                    <label for="accountNumber">Account Number*:</label>
                    <input type="number" id="accountNumber" name="accountNumber" required>
                </div>
                <div>
                    <label for="routingNumber">Routing Number*:</label>
                    <input type="number" id="routingNumber" name="routingNumber" required>
                </div>
                <div>
                    <label for="payee">Payee:</label>
                    <input type="text" id="payee" name="payee">
                </div>
                <div>
                    <label for="date">Date:</label>
                    <input type="date" id="date" name="date">
                </div>
                <div>
                    <label for="memo">Memo:</label>
                    <input type="text" id="memo" name="memo">
                </div>
            </div>
            
            <!-- Credit Card Details -->
            <div id="creditCardDetails" class="payment-details"  style="display:none;">
                <div>
                    <label for="cardNumber">Card Number*:</label>
                    <input type="number" id="cardNumber" name="cardNumber" required>
                </div>
                <div>
                    <label for="cardHolder">Card Holder*:</label>
                    <input type="text" id="cardHolder" name="cardHolder" required>
                </div>
                <div>
                    <label for="expiryDate">Expiry Date*:</label>
                    <input type="date" id="expiryDate" name="expiryDate" required>
                </div>
                <div>
                    <label for="cvv">CVV*:</label>
                    <input type="text" id="cvv" name="cvv" required>
                </div>
            </div>

            <!-- PayPal Details -->
            <div id="paypalDetails" class="payment-details" style="display:none;">
                <div>
                    <label for="email">Email*:</label>
                    <input type="email" id="email" name="email" required>
                </div>
                <div>
                    <label for="password">Password*:</label>
                    <input type="password" id="password" name="password" required>
                </div>
            </div>

            <!-- Apple Pay Details -->
            <div id="applePayDetails" class="payment-details"  style="display:none;">
                <div>
                    <label for="deviceToken">Device Token*:</label>
                    <input type="text" id="deviceToken" name="deviceToken" required>
                </div>
            </div>

             <button type="submit">Pay</button>
        </form>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script>
        
        document.getElementById('paymentType').addEventListener('change', function() {
            var paymentType = this.value;
            var paymentDetails = document.querySelectorAll('.payment-details');
            
            // Hide all payment details and disable their inputs
            paymentDetails.forEach(function(detail) {
                detail.style.display = 'none';
                detail.querySelectorAll('input').forEach(function(input) {
                    input.disabled = true;
                    input.value = ''; // Clear the input values
                });
            });
            
            // Show and enable inputs for the selected payment type
            if (paymentType == 'credit_card') {
                document.getElementById('creditCardDetails').style.display = 'block';
                document.getElementById('creditCardDetails').querySelectorAll('input').forEach(function(input) {
                    input.disabled = false;
                });
            } else if (paymentType == 'paypal') {
                document.getElementById('paypalDetails').style.display = 'block';
                document.getElementById('paypalDetails').querySelectorAll('input').forEach(function(input) {
                    input.disabled = false;
                });
            } else if (paymentType == 'apple_pay') {
                document.getElementById('applePayDetails').style.display = 'block';
                document.getElementById('applePayDetails').querySelectorAll('input').forEach(function(input) {
                    input.disabled = false;
                });
            } else if (paymentType == 'check') {
                document.getElementById('checkDetails').style.display = 'block';
                document.getElementById('checkDetails').querySelectorAll('input').forEach(function(input) {
                    input.disabled = false;
                });
            }
        });
    </script>
</body>
</html>
