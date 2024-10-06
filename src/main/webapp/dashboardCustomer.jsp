<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h2>Dashboard</h2>

<p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>
<p>Your role is: <strong><%= session.getAttribute("role") %></strong>!</p>

<form action="addItem">
    <input type="submit" value="Add Item" />
</form>
<form action="removeItem">
    <input type="submit" value="Remove Item" />
</form>
<form action="showMyItems">
    <input type="submit" value="Show Your Items" />
</form>

<a href="index.jsp">Logout</a> <!-- Länk för att logga ut, om det behövs -->
</body>
</html>
