<%-- 
    Document   : claimList
    Created on : Jul. 24, 2024, 11:20:08 p.m.
    Author     : Hongxiu Guo
--%>

<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claim List</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/inventory.css">
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
        <h2>Available Claim List</h2>
        <table id="inventory-table">
            <thead>
                <tr>
                    <th>Food Name</th>
                    <th>Brand</th>
                    <th>Retailer</th>
                    <th>Quantity</th>
                    <th>Expiration Date</th>
                    <th>Status</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Apples</td>
                    <td>ss</td>
                    <td>sgsdfg</td>
                    <td>10</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Claim</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>ss</td>
                    <td>sgsdfg</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Claim</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>ss</td>
                    <td>sgsdfg</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Claim</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>ss</td>
                    <td>sgsdfg</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Claim</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>ss</td>
                    <td>sgsdfg</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Claim</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <button onclick="loadClaimHistory(<%= user.getUserID() %>)">Load Claim History</button>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script>
        function loadClaimHistory(userId) {
            // load the food item management page
            window.location.href = "claimFood?userId="+userId;
        }
    </script>
</body>
</html>
