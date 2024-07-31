<%-- 
    Document   : viewSubscriptions
    Created on : Jul 30, 2024, 4:04:42 p.m.
    Author     : ryany
--%>
<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Subscriptions</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/home.css">
    <link rel="stylesheet" href="styles/fooditemlist.css">
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
        <h2>Subscriptions</h2>
        <c:choose>
            <c:when test="${userType == 'RETAILER'}">
                <h3>Subscribers to Your Services</h3>
                <table>
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>User Name</th>
                            <th>User Type</th>
                            <th>Communication Preference</th>
                            <th>Food Preferences</th>
                            <th>Subscribed Since</th>
                            <th>Last Updated</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="subscriber" items="${subscribers}">
                            <tr>
                                <td>${subscriber.userID}</td>
                                <td>${subscriber.userName}</td>
                                <td>${subscriber.userType}</td>
                                <td>${subscriber.communicationPreference}</td>
                                <td>${subscriber.foodPreferences}</td>
                                <td>${subscriber.createdAt}</td>
                                <td>${subscriber.lastUpdated}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h3>Your Subscriptions</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Retailer Name</th>
                            <th>Communication Preference</th>
                            <th>Food Preferences</th>
                            <th>Subscribed Since</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="subscriber" items="${subscribers}">
                            <tr>
                                <td>${subscriber.userName}</td>
                                <td>${subscriber.communicationPreference}</td>
                                <td>${subscriber.foodPreferences}</td>
                                <td>${subscriber.createdAt}</td>
                                <td>
                                    <button onclick="editSubscription(${subscriber.subscriptionId})">Edit</button>
                                    <button onclick="deleteSubscription(${subscriber.subscriptionId})">Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script>
        function editSubscription(subscriptionId) {
            // Implement edit functionality
            console.log("Edit subscription: " + subscriptionId);
        }

        function deleteSubscription(subscriptionId) {
            // Implement delete functionality
            console.log("Delete subscription: " + subscriptionId);
        }
    </script>
</body>
</html>
