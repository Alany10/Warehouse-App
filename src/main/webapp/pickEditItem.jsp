<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Item" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>edit item</title>
</head>
<body>
<h2>Select Item to edit</h2>

<p>Welcome, <strong><%= session.getAttribute("username") %></strong>!</p>

<%-- Visa meddelanden --%>
<%
    String message = request.getParameter("message");
    String error = request.getParameter("error");
    if (message != null) {
%>
<p style="color:green;"><%= message %></p>
<%
} else if (error != null) {
%>
<p style="color:red;"><%= error %></p>
<%
    }
%>

<%
    List<Item> items = (List<Item>) session.getAttribute("items");
    if (items != null && !items.isEmpty()) {
%>

<form action="pickEditItem" method="post">
    <% for (Item item : items) { %>
    <label>
        <input type="radio" name="itemId" value="<%= item.getId() %>" />
    </label>
    <%= item.toString() %><br>
    <% } %>
    <input type="submit" value="Edit Selected Item" />
</form>

<%
} else {
%>
<p>No items available.</p>
<%
    }
%>

<a href="dashboardAdmin.jsp">Back to Dashboard</a>
</body>
</html>