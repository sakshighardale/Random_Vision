<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
</head>
<body>
<h1>Registration</h1>
<hr>
<form action="SuccessRegistration" method="post">
Username : <input type="text" placeholder="Enter username."name="username" ><br>
Email : <input type="email" placeholder="Enter email."name="email"  required><br>
Password : <input type="password" placeholder="Enter password of max length of 8."name="password" required><br>
<input type="submit" value="Register" >

<br><hr>
<h4>You will receive a mail once registration is done.</h4>
</form>
</body>
</html>