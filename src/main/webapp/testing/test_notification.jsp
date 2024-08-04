<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Update Inventory</title>
    <base href="${pageContext.request.contextPath}/" />
    <link rel="stylesheet" href="styles/login.css">
</head>
    <h2>Update Inventory Status</h2>
    <form id="updateForm">
        <label for="inventoryId">Inventory ID:</label>
        <input type="number" id="inventoryId" name="inventoryId" required min="1"><br><br>
        
        <label for="status">New Status:</label>
        <select id="status" name="status" required>
            <option value="DONATION">Donation</option>
            <option value="DISCOUNT">Discount</option>
        </select><br><br>
        
        <input type="submit" value="Update">
    </form>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var updateForm = document.getElementById('updateForm');
            if (updateForm) {
                updateForm.addEventListener('submit', function(e) {
                    e.preventDefault();
                    var formData = new FormData(this);
                    var urlEncodedData = new URLSearchParams(formData).toString();
                    fetch('${pageContext.request.contextPath}/NotificationServlet', {
                        method: 'POST',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded',},
                        body: urlEncodedData
                    })
                    .then(response => response.text())
                    .then(result => {
                        alert(result);
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('An error occurred: ' + error);
                    });
                });
            } else {
                console.error("Form with id 'updateForm' not found.");
            }
        });
    </script>
</body>
</html>
