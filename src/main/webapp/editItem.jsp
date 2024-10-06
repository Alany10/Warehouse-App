<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="bo.Item" %>
<%@ page import="bo.Category" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Edit Item</title>
</head>
<body>
<h2>Edit Item</h2>

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

<%-- Hämta det valda objektet från sessionen --%>
<%
    if (session.getAttribute("id") != null) {
%>
<form action="editItem" method="post">
    <input type="hidden" name="id" value="<%= session.getAttribute("id") %>" />

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="<%= session.getAttribute("name") %>" required /><br><br>

    <label for="description">Description:</label>
    <input type="text" id="description" name="description" value="<%= session.getAttribute("description")%>" required /><br><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" value="<%= session.getAttribute("price") %>" required /><br><br>

    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="<%= session.getAttribute("quantity") %>" required /><br><br>

    <label for="category">Category:</label>
    <select id="category" name="category" required>
        <option value="COMPUTER" <%= session.getAttribute("category")  == Category.COMPUTER? "selected" : "" %>>Computer</option>
        <option value="MOBILE" <%= session.getAttribute("category") == Category.MOBILE ? "selected" : "" %>>Mobile</option>
        <option value="ACCESSORY" <%= session.getAttribute("category") == Category.ACCESSORY ? "selected" : "" %>>Accessory</option>
        <option value="TV" <%= session.getAttribute("category") == Category.TV ? "selected" : "" %>>TV</option>
        <option value="HEADPHONE" <%= session.getAttribute("category") == Category.HEADPHONE ? "selected" : "" %>>Headphone</option>
    </select><br><br>

    <input type="submit" value="Update Item" />
</form>

<%
} else {
%>
<p>No item selected for editing.</p>
<%
    }
%>

<a href="dashboardAdmin.jsp">Back to Dashboard</a>
</body>
</html>
