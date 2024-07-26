//Not being used in this webapp

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.project.user.Notes"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	Notes notes = (Notes) request.getAttribute("YourNote");
	String yourNote = notes.getNote();
	System.out.println("Through Session :"+session.getAttribute("randomNumber"));
	if (notes != null) {
	%>
	<div>
		<H1>Note of your reflection!!!</H1>
		<!-- <h2>ID: <%=  notes.getId()%></h2> -->
		<h2>
			Note:</h2>
			<h1><%=yourNote%></h1>
	</div>
	<%} %>
<form action="SaveTheNote" method=Post>
<input type="submit" value="SAVE The Note">
</form>
<br><br>
<form action="/2_RandomNote/" method="post">
<input type="submit" value="To Home Page">
</form>
</body>
</html>