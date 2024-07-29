<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page import="org.cst8288.foodwastereduction.model.FoodItemDTO"%>
<%@page import="org.cst8288.foodwastereduction.model.InventoryDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventory Update Page</title>
        <base href="${pageContext.request.contextPath}/" />
        <link rel="stylesheet" href="styles/add.css">
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
            <form action="inventoryUpdate" method="post">
              
                <div>
                    <label for="foodItemId">Food Item:</label>
                    <select id="foodItemId" name="foodItemId" required>
                        <%
                            List<FoodItemDTO> foodItems = (List<FoodItemDTO>) request.getAttribute("foodItems");
                            InventoryDTO inventory = (InventoryDTO) request.getAttribute("inventory");
                            for (FoodItemDTO foodItem : foodItems) {
                                String selected = foodItem.getFoodItemId() == inventory.getFoodItemId() ? "selected" : "";
                                out.print("<option value=\"" + foodItem.getFoodItemId() + "\" " + selected + ">" + foodItem.getName() + "</option>");
                            }
                        %>
                    </select>
                </div>
                
                <div>
                    <label for="batchNumber">Batch Number:</label>
                    <input type="text" id="batchNumber" name="batchNumber" value="${inventory.batchNumber}" required>
                </div>
                
                <div>
                    <label for="quantity">Quantity:</label>
                    <input type="number" id="quantity" name="quantity" value="${inventory.quantity}" required>
                </div>
                
                <div>
                    <label for="regularPrice">Regular Price:</label>
                    <input type="number" step="0.01" id="regularPrice" name="regularPrice" value="${inventory.regularPrice}" required>
                </div>
                
                <div>
                    <label for="discountRate">Discount Rate:</label>
                    <input type="number" step="0.01" id="discountRate" name="discountRate" value="${inventory.discountRate}" required>
                </div>
                
                <div>
                    <label for="expirationDate">Expiration Date:</label>
                    <input type="date" id="expirationDate" name="expirationDate" value="${inventory.expirationDate}" required>
                </div>
                
                <div>
                    <label for="receiveDate">Receive Date:</label>
                    <input type="date" id="receiveDate" name="receiveDate" value="${inventory.receiveDate}" required>
                </div>
                
                <input type="hidden" name="inventoryId" value="${inventory.inventoryId}">
                
                <input type="hidden" name="userId" value="<%= user != null ? user.getUserID() : "" %>">
                
                <button type="submit">Update Inventory</button>
            </form>
        </main>
    </body>
</html>