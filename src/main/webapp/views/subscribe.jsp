<%--
  Created by IntelliJ IDEA.
  User: yaoyi
  Date: 2024-07-25
  Time: 6:06 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subscribe</title>
    <link rel="stylesheet" href="../styles/subscribe.css">
</head>
<body>
<header>
    <h1>Food Waste Reduction Platform</h1>
    <nav>
        <ul>
            <li><a href="home.jsp">Home</a></li>
            <li><a href="purchase.jsp">Purchase</a></li>
            <li><a href="subscribe.jsp">Subscribe</a></li>
        </ul>
    </nav>
</header>
<main>
    <h2>Choose Your Subscription Preferences</h2>
    <form id="subscribe-form">
        <div>
            <label for="retailer-pref">Receive notification from:</label>
            <select id="retailer-pref" name="retailer-pref" required>
                <option value=" ">Retailer1</option>
                <option value=" ">Retailer2</option>
                <option value=" ">Retailer3</option>
            </select>
        </div>
        <div>
            <p>Food preference:</p>

            <div class="checkbox-container">
                <div class="checkbox-item">
                    <input type="checkbox" id="bakery" name="food-pref" value="Bakery">
                    <label for="bakery">Bakery</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="beef" name="food-pref" value="Beef">
                    <label for="beef">Beef</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="beverage" name="food-pref" value="Beverage">
                    <label for="beverage">Beverage</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="condiment" name="food-pref" value="Condiment">
                    <label for="condiment">Condiment</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="dairy" name="food-pref" value="Dairy">
                    <label for="dairy">Dairy</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="egg" name="food-pref" value="Egg">
                    <label for="egg">Egg</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="fruit" name="food-pref" value="Fruit">
                    <label for="fruit">Fruit</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="grain" name="food-pref" value="Grain">
                    <label for="grain">Grain</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="legume" name="food-pref" value="Legume">
                    <label for="legume">Legume</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="nut" name="food-pref" value="Nut">
                    <label for="nut">Nut</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="pork" name="food-pref" value="Pork">
                    <label for="pork">Pork</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="poultry" name="food-pref" value="Poultry">
                    <label for="poultry">Poultry</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="seafood" name="food-pref" value="Seafood">
                    <label for="seafood">Seafood</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="snack" name="food-pref" value="Snack">
                    <label for="snack">Snack</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="vegetable" name="food-pref" value="Vegetable">
                    <label for="vegetable">Vegetable</label>
                </div>
                <div class="checkbox-item">
                    <input type="checkbox" id="other" name="food-pref" value="Other">
                    <label for="other">Other</label>
                </div>
            </div>
        </div>
        <div>
            <label for="comm-pref">Receive notification by:</label>
            <select id="comm-pref" name="comm-pref" required>
                <option value="email">Email</option>
                <option value="sms">SMS</option>
            </select>
        </div>
        <br>
        <button type="submit">Subscribe</button>
    </form>
</main>
<footer>
    <p>&copy; 2024 Food Waste Reduction Platform</p>
</footer>
<script src="scripts.js"></script>
</body>
</html>


