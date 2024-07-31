<%-- 
    Document   : login
    Created on : Jul. 22, 2024, 1:39:22 p.m.
    Author     : Hongxiu Guo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
        <base href="${pageContext.request.contextPath}/" />
        <link rel="stylesheet" href="styles/login.css">
    </head>
    <body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
    </header>
    <main class="login">
        <h2>Login</h2>
        <form id="login-form" action="login" method="post">
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required value="${param.email}">
            </div>
            <div>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required value="${param.password}">
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
            <button type="submit">Log In</button>
<!--            <a href="views/payment.jsp">Payment</a>-->
        </form>

        <p>Don't have an account? <a href="views/signup.jsp">Sign up here</a>.</p>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script src="scripts.js"></script>
</body>
</html>
