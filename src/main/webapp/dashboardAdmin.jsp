<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h2>Dashboard</h2>

<p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>
<p>Your role is: <strong><%= session.getAttribute("role") %></strong>!</p>

<!-- Form för att lägga till ny artikel -->
<form action="addNewItem" method="get">
    <input type="submit" value="Add New Item" />
</form>

<!-- Form för att redigera en artikel (kan justeras för att peka på korrekt servlet) -->
<form action="pickEditItem" method="get">
    <input type="submit" value="Edit Item" />
</form>

<!-- Länk för att logga ut -->
<a href="index.jsp">Logout</a>
</body>
</html>
