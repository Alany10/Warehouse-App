<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Item" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Your Items</title>
</head>
<body>
<h2>Your Items</h2>

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

<ul>
    <% for (Item item : items) { %>
    <li>
        <%= item.toString() %>
    </li>
    <% } %>
</ul>

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
