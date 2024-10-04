<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.User" %>
<%@ page import="bo.Item" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Shopping Bags for Packing</title>
</head>
<body>
<h2>Shopping Bags for Packing</h2>

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
    List<User> customersWithCarts = (List<User>) session.getAttribute("customersWithCarts");
    if (customersWithCarts != null && !customersWithCarts.isEmpty()) {
%>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Items</th>
        <th>Choose</th>
    </tr>
    <% for (User customer : customersWithCarts) { %>
    <tr>
        <td><%= customer.getUsername() %></td>
        <td>
            <ul>
                <% for (Item item : customer.getShoppingBag()) { %>
                <li><%= item.getName() %></li>
                <% } %>
            </ul>
        </td>
        <td>
            <form action="selectCart" method="post">
                <input type="hidden" name="customerId" value="<%= customer.getId() %>">
                <input type="submit" value="Done">
            </form>
        </td>
    </tr>
    <% } %>
</table>

<%
} else {
%>
<p>No shopping bags available.</p>
<%
    }
%>

<a href="index.jsp">Logout</a>
</body>
</html>
