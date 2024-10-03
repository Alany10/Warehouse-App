<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>Login with your username and password:</h2>
<!-- Ett formulär som skickar en POST-begäran till WelcomeServlet -->
<form action="login" method="post">
    <input type="text" name="username" placeholder="Username" required />
    <input type="password" name="password" placeholder="Password" required />

    <!-- Select-fält för att välja en roll eller typ -->
    <label for="role">Select your role:</label>
    <select name="role" id="role" required>
        <option value="" disabled selected>Select your role</option>
        <option value="CUSTOMER">Customer</option>
        <option value="ADMIN">Admin</option>
        <option value="WAREHOUSE">Warehouse</option>
    </select>

    <input type="submit" value="Submit" />
</form>

<%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
</body>
</html>