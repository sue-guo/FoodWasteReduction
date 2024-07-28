<%-- 
    Document   : foodItem
    Created on : Jul 27, 2024, 5:42:11 PM
    Author     : WANG JIAYUN
--%>
<%@page import="org.cst8288.foodwastereduction.model.UserType"%>
<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retailer's Food Item Management</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/common.css">
</head>
<body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
        <nav>
            <ul>
                <li><a href="views/home.jsp">Home</a></li>
                <li>
                    <%
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
        <h2>Manage Your Food Items</h2>
        <%
            if (user != null && user.getUserType() == UserType.RETAILER) {
        %>
        <section>
            <h3>Add New Food Item</h3>
            <form action="addFoodItem" method="post">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required><br>
                <label for="description">Description:</label>
                <input type="text" id="description" name="description"><br>
                <label for="category">Category:</label>
                <select id="category" name="category" required>
                    <option value="Dairy">Dairy</option>
                    <option value="Egg">Egg</option>
                    <option value="Beef">Beef</option>
                    <option value="Pork">Pork</option>
                    <option value="Poultry">Poultry</option>
                    <option value="Fruit">Fruit</option>
                    <option value="Vegetable">Vegetable</option>
                    <option value="Seafood">Seafood</option>
                    <option value="Grain">Grain</option>
                    <option value="Nut">Nut</option>
                    <option value="Legume">Legume</option>
                    <option value="Bakery">Bakery</option>
                    <option value="Beverage">Beverage</option>
                    <option value="Snack">Snack</option>
                    <option value="Condiment">Condiment</option>
                    <option value="Other">Other</option>
                </select><br>
                <label for="brand">Brand:</label>
                <input type="text" id="brand" name="brand"><br>
                <label for="unit">Unit:</label>
                <input type="text" id="unit" name="unit"><br>
                <input type="hidden" name="retailerId" value="<%= user.getUserID() %>">
                <button type="submit">Add Food Item</button>
            </form>
        </section>
        <section>
            <h3>Your Food Items</h3>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Category</th>
                        <th>Brand</th>
                        <th>Unit</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Connection conn = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourDatabaseName", "root", "yourPassword");
                            String sql = "SELECT * FROM FoodItems WHERE RetailerID = ?";
                            ps = conn.prepareStatement(sql);
                            ps.setInt(1, user.getUserID());
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                out.print("<tr>");
                                out.print("<td>" + rs.getString("Name") + "</td>");
                                out.print("<td>" + rs.getString("Description") + "</td>");
                                out.print("<td>" + rs.getString("Category") + "</td>");
                                out.print("<td>" + rs.getString("Brand") + "</td>");
                                out.print("<td>" + rs.getString("Unit") + "</td>");
                                out.print("</tr>");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (rs != null) rs.close();
                            if (ps != null) ps.close();
                            if (conn != null) conn.close();
                        }
                    %>
                </tbody>
            </table>
        </section>
        <%
            } else {
                out.print("<p>You do not have access to this page.</p>");
            }
        %>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
</body>
</html>