<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Greeting Form</title>
</head>
<body>
<h2>Enter your name:</h2>
<!-- Ett formulär som skickar en POST-begäran till WelcomeServlet -->
<form action="welcome" method="post">
    <input type="text" name="name" placeholder="Your name" />
    <input type="submit" value="Submit" />
</form>
</body>
</html>