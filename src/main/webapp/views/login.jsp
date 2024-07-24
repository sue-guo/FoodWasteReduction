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
         <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/login.css">
    </head>
    <body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
    </header>
    <main class="login">
        <h2>Login</h2>
        <form id="login-form" action="${pageContext.request.contextPath}/login" method="post">
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
        </form>

        <p>Don't have an account? <a href="${pageContext.request.contextPath}/views/signup.jsp">Sign up here</a>.</p>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script src="scripts.js"></script>
</body>
</html>
