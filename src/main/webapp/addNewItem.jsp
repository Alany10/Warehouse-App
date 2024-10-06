<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Item</title>
</head>
<body>
<h2>Add New Item</h2>

<form action="addNewItem" method="post">
    <label for="name">Item Name:</label>
    <input type="text" id="name" name="name" required><br><br>

    <label for="description">Description:</label>
    <input type="text" id="description" name="description" required><br><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" required><br><br>

    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" required><br><br>

    <label for="category">Category:</label>
    <select id="category" name="category" required>
        <option value="COMPUTER">Computer</option>
        <option value="MOBILE">Mobile</option>
        <option value="ACCESSORY">Accessory</option>
        <option value="TV">TV</option>
        <option value="HEADPHONE">Headphone</option>
    </select><br><br>

    <input type="submit" value="Add Item">
</form>

<p style="color: red;">
    <%= request.getParameter("error") != null ? request.getParameter("error") : "" %>
</p>

<p style="color: green;">
    <%= request.getParameter("success") != null ? request.getParameter("success") : "" %>
</p>

<a href="dashboardAdmin.jsp">Back to Dashboard</a>
</body>
</html>
