<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
<h1>Login</h1>
<hr>
<form action="Login" method="post">
Username : <input type="text" placeholder="Enter username."name="username1" ><br>
Password : <input type="password" placeholder="Enter password."name="password1" required><br>
<input type="submit" value="login"> 
</form>
<% 
        String error = (String) request.getAttribute("errorLogin");
        if (error != null) { 
    %>
        <div style="color: red;"><%= error %></div>
    <% 
        } 
    %>

</body>
</html>