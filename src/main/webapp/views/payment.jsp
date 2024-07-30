<%-- 
    Document   : payment
    Created on : Jul. 30, 2024, 3:10:02 p.m.
    Author     : Hongxiu Guo
--%>

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

        <form action="payment" method="post">
            <div>
                <label for="amount">Amount:</label>
                <input type="text" id="amount" name="amount" readonly value="222.33">
            </div>
            <div>
                <label for="paymentType">Payment Method:</label>
                <select id="paymentType" name="paymentType" required>
                    <option value="">Please select a payment</option>
                    <option value="check">Check</option>
                    <option value="creditCard">Credit Card</option>
                    <option value="paypal">PayPal</option>
                    <option value="applePay">Apple Pay</option>
                </select><br><br>
            </div>
            <!-- Check Payment Details -->
             <div id="checkDetails" class="payment-details" style="display:none;">
                <div>
                    <label for="checkNumber">Check Number:</label>
                    <input type="text" id="checkNumber" name="checkNumber" required>
                </div>
                <div>
                    <label for="bankName">Bank Name:</label>
                    <input type="text" id="bankName" name="bankName" required>
                </div>
                <div>
                    <label for="accountNumber">Account Number:</label>
                    <input type="text" id="accountNumber" name="accountNumber" required>
                </div>
                <div>
                    <label for="routingNumber">Routing Number:</label>
                    <input type="text" id="routingNumber" name="routingNumber" required>
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
            <div id="creditCardDetails" style="display:none;">
                <div>
                    <label for="cardNumber">Card Number:</label>
                    <input type="text" id="cardNumber" name="cardNumber">
                </div>
                <div>
                    <label for="cardHolder">Card Holder:</label>
                    <input type="text" id="cardHolder" name="cardHolder">
                </div>
                <div>
                    <label for="expiryDate">Expiry Date:</label>
                    <input type="text" id="expiryDate" name="expiryDate">
                </div>
                <div>
                    <label for="cvv">CVV:</label>
                    <input type="text" id="cvv" name="cvv">
                </div>
            </div>

            <!-- PayPal Details -->
            <div id="paypalDetails" style="display:none;">
                <div>
                    <label for="email">Email:</label>
                    <input type="text" id="email" name="email">
                </div>
                <div>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password">
                </div>
            </div>

            <!-- Apple Pay Details -->
            <div id="applePayDetails" style="display:none;">
                <div>
                    <label for="deviceToken">Device Token:</label>
                    <input type="text" id="deviceToken" name="deviceToken">
                </div>
            </div>

             <button type="submit">Pay</button>
        </form>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script>
        //Clear all input value
        function clearInputFields(containerId) {
            const container = document.getElementById(containerId);
            const inputs = container.querySelectorAll('input');
            inputs.forEach(input => input.value = '');
        }

        document.getElementById('paymentType').addEventListener('change', function() {
            const paymentType = this.value;
            // Clear all sections
            clearInputFields('creditCardDetails');
            clearInputFields('paypalDetails');
            clearInputFields('applePayDetails');
            clearInputFields('checkDetails');
            
            // Hide all sections
            document.getElementById('creditCardDetails').style.display = 'none';
            document.getElementById('paypalDetails').style.display = 'none';
            document.getElementById('applePayDetails').style.display = 'none';
            document.getElementById('checkDetails').style.display = 'none';
            
            // Show the selected section
            if (paymentType === 'creditCard') {
                document.getElementById('creditCardDetails').style.display = 'block';
            } else if (paymentType === 'paypal') {
                document.getElementById('paypalDetails').style.display = 'block';
            } else if (paymentType === 'applePay') {
                document.getElementById('applePayDetails').style.display = 'block';
            } else if (paymentType === 'check') {
                document.getElementById('checkDetails').style.display = 'block';
            }
        });
    </script>
</body>
</html>
