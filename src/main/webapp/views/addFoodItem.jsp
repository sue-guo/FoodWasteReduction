<%-- 
    Document   : addFoodItem
    Created on : Jul 27, 2024, 10:42:57 PM
    Author     : Carri
--%>

<%@page import="org.cst8288.foodwastereduction.model.User"%>
<%@page import="org.cst8288.foodwastereduction.model.UserType"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food Item Add Page</title>
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
    <form action="foodItem" method="post">
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${param.name}" required>
        </div>
        
        <div>
            <label for="description">Description:</label>
            <input type="text" id="description" name="description" value="${param.description}" required>
        </div>
        
        <div>
          <label for="category">Category:</label>
          <select id="category" name="category" required>
             <option value="Dairy" ${param.category == 'Dairy' ? 'selected' : ''}>Dairy</option>
             <option value="Egg" ${param.category == 'Egg' ? 'selected' : ''}>Egg</option>
             <option value="Beef" ${param.category == 'Beef' ? 'selected' : ''}>Beef</option>
             <option value="Pork" ${param.category == 'Pork' ? 'selected' : ''}>Pork</option>
             <option value="Poultry" ${param.category == 'Poultry' ? 'selected' : ''}>Poultry</option>
             <option value="Fruit" ${param.category == 'Fruit' ? 'selected' : ''}>Fruit</option>
             <option value="Vegetable" ${param.category == 'Vegetable' ? 'selected' : ''}>Vegetable</option>
             <option value="Seafood" ${param.category == 'Seafood' ? 'selected' : ''}>Seafood</option>
             <option value="Grain" ${param.category == 'Grain' ? 'selected' : ''}>Grain</option>
             <option value="Nut" ${param.category == 'Nut' ? 'selected' : ''}>Nut</option>
             <option value="Legume" ${param.category == 'Legume' ? 'selected' : ''}>Legume</option>
             <option value="Bakery" ${param.category == 'Bakery' ? 'selected' : ''}>Bakery</option>
             <option value="Beverage" ${param.category == 'Beverage' ? 'selected' : ''}>Beverage</option>
             <option value="Snack" ${param.category == 'Snack' ? 'selected' : ''}>Snack</option>
             <option value="Condiment" ${param.category == 'Condiment' ? 'selected' : ''}>Condiment</option>
             <option value="Other" ${param.category == 'Other' ? 'selected' : ''}>Other</option>
        </select>
       </div>
        
        <div>
            <label for="brand">Brand:</label>
            <input type="text" id="brand" name="brand" value="${param.brand}" required>
        </div>
        
        <div>
            <label for="unit">Unit:</label>
            <select id="unit" name="unit" required>
                <option value="kg" ${param.unit == 'kg' ? 'selected' : ''}>Kg</option>
                <option value="lb" ${param.unit == 'lb' ? 'selected' : ''}>Lb</option>
                <option value="L" ${param.unit == 'L' ? 'selected' : ''}>L</option>
                <option value="dozen" ${param.unit == 'dozen' ? 'selected' : ''}>Dozen</option>
                <option value="each" ${param.unit == 'each' ? 'selected' : ''}>Each</option>
                <option value="other" ${param.unit == 'other' ? 'selected' : ''}>Other</option>
            </select>
        </div>

        <input type="hidden" name="userId" value="<%= user != null ? user.getUserID() : "" %>">
        
        <button type="submit">Add Food Item</button>
    </form>
</main>
        
</body>
</html>