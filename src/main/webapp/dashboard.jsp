<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h2>Dashboard</h2>

<p>Welcome, <strong><%= session.getAttribute("name") %></strong>!</p>

<form action="addItem.jsp">
    <input type="submit" value="Add Item" />
</form>
<form action="removeItem.jsp">
    <input type="submit" value="Remove Item" />
</form>
<form action="showItems.jsp">
    <input type="submit" value="Show Items" />
</form>

<a href="logout">Logout</a> <!-- Länk för att logga ut, om det behövs -->
</body>
</html>
