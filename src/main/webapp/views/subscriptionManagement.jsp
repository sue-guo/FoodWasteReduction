<%-- 
    Document   : subscriptionManagement
    Created on : Jul 31, 2024, 4:04:36 p.m.
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
    <h1>Subscription Management for ${user.name}</h1>
    
    <h2>Manage Your Subscription</h2>
    <form action="subscriptionManagement" method="post">
        <input type="hidden" name="userId" value="${user.userId}">
        
        <label for="retailerId">Select Retailer:</label>
        <select name="retailerId" id="retailerId">
            <c:forEach var="retailer" items="${localRetailers}">
                <option value="${retailer.userId}">${retailer.name}</option>
            </c:forEach>
        </select>
        
        <h3>Communication Preference</h3>
        <select name="communicationPreference">
            <option value="Email">Email</option>
            <option value="SMS">SMS</option>
        </select>
        
        <h3>Food Preferences</h3>
        <c:forEach var="preference" items="${FoodPreference.values()}">
            <input type="checkbox" name="foodPreferences" value="${preference}" id="${preference}">
            <label for="${preference}">${preference}</label><br>
        </c:forEach>
        
        <input type="submit" value="Update Subscription">
    </form>

    <h2>Your Current Subscriptions</h2>
    <table>
        <tr>
            <th>Retailer</th>
            <th>Communication Preference</th>
            <th>Food Preferences</th>
        </tr>
        <c:forEach var="subscription" items="${userSubscriptions}">
            <tr>
                <td>${subscription.retailerName}</td>
                <td>${subscription.communicationPreference}</td>
                <td>${subscription.foodPreferences}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
