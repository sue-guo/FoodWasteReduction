<%-- 
    Document   : inventory
    Created on : Jul 27, 2024, 10:41:48 PM
    Author     : Carri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory</title>
    <link rel="stylesheet" href="styles/inventory.css">
</head>
<body>
    <header>
        <h1>Food Waste Reduction Platform</h1>
        <nav>
            <ul>
                <li><a href="home.html">Home</a></li>
                <li><a href="inventory.html">Inventory</a></li>
                <li><a href="add.html">Add Item</a></li>
                <li><a href="reports.html">Reports</a></li>
                <li><a href="login.html">Login</a></li>
            </ul>
        </nav>
    </header>
    <main>
        <h2>Inventory List</h2>
        <table id="inventory-table">
            <thead>
                <tr>
                    <th>Item Name</th>
                    <th>Quantity</th>
                    <th>Expiration Date</th>
                    <th>Status</th>
                    <th>Operation</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Apples</td>
                    <td>10</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
                <tr>
                    <td>Oranges</td>
                    <td>20</td>
                    <td>2024-01-01</td>
                    <td>Good</td>
                    <td>
                        <button>Edit</button>
                        <button>Delete</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <button onclick="loadInventory()">Load Inventory</button>
    </main>
    <footer>
        <p>&copy; 2024 Food Waste Reduction Platform</p>
    </footer>
    <script src="scripts.js"></script>
</body>
</html>