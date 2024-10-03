<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Greeting Form</title>
</head>
<body>
<h2>Enter your username and password:</h2>
<!-- Ett formulär som skickar en POST-begäran till WelcomeServlet -->
<form action="login" method="post">
    <input type="text" name="username" placeholder="Username" />
    <input type="password" name="password" placeholder="Password" />
    <input type="submit" value="Submit" />
</form>

<% String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
</body>
</html>
