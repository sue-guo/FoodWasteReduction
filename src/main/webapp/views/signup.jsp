<%-- 
    Document   : signup
    Created on : Jul. 22, 2024, 2:07:46 p.m.
    Author     : Hongxiu Guo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/login.css">
</head>
<body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
    </header>
    <main class="login">
        <h2>Sign Up</h2>
        <form id="signup-form" action="signup" method="post">
            <div>
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required value="${param.name}">
            </div>
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required value="${param.email}">
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required value="${param.password}">
            </div>
            <div>
                <label for="phonenumber">Phone Number:</label>
                <input type="number" id="phoneNumber" name="phoneNumber" value="${param.phoneNumber}">
            </div>
            <div>
                <label for="user-type">User Type:</label>
                <select id="user-type" name="userType" required>
                    <option value="RETAILER" ${param.userType == 'RETAILER' ? 'selected' : ''}>Retailer</option>
                    <option value="CONSUMER" ${param.userType == 'CONSUMER' ? 'selected' : ''}>Consumer</option>
                    <option value="CHARITABLE_ORGANIZATION" ${param.userType == 'CHARITABLE_ORGANIZATION' ? 'selected' : ''}>Charitable Organization</option>
                </select>
            </div>
            <div>
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${param.address}">
            </div>
            <div>
                <label for="city">City:</label>
                <input type="text" id="city" name="city" value="${param.city}">
            </div>
            <!-- Display error message if it exists -->
            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <p class="errorMsg">
                    <%= errorMessage %>
                </p>
            <% 
                }
            %>
            <button type="submit">Sign Up</button>
        </form>
        <p>Already have an account? <a href="views/login.jsp">Log in here</a>.</p>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script src="scripts.js"></script>
</body>
</html>
