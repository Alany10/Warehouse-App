<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Item" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Remove Item</title>
</head>
<body>
<h2>Select item to remove</h2>

<p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>

<%
    String message = request.getParameter("message");
    String error = request.getParameter("error");

    if (message != null) {
%>
<p style="color: green;"><%= message %></p>
<%
} else if (error != null) {
%>
<p style="color: red;"><%= error %></p>
<%
    }
%>

<%
    List<Item> items = (List<Item>) session.getAttribute("myItems");
    if (items != null && !items.isEmpty()) {
%>

<form action="removeItem" method="post">
    <% for (Item item : items) { %>
    <input type="radio" name="itemId" value="<%= item.getId() %>" />
    <%= item.toString() %><br>
    <% } %>
    <input type="submit" value="Remove Selected Item" />
</form>

<%
} else {
%>
<p>No items available.</p>
<%
    }
%>

<a href="dashboardCustomer.jsp">Back to Dashboard</a>
</body>
</html>
