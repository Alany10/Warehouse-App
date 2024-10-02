<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Item" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Add Item</title>
</head>
<body>
<h2>Add Item</h2>

<p>Welcome, <strong><%= session.getAttribute("name") %></strong>!</p>

<%
    List<Item> items = (List<Item>) session.getAttribute("items");
    if (items != null && !items.isEmpty()) {
%>
<form action="addItem" method="post">
    <select name="itemId">
        <% for (Item item : items) { %>
        <option value="<%= item.getId() %>"><%= item.getName() %></option>
        <% } %>
    </select>
    <input type="submit" value="Add Item" />
</form>
<%
} else {
%>
<p>No items available.</p>
<%
    }
%>

<a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>
