<%@page
	import="org.apache.catalina.authenticator.SpnegoAuthenticator.SpnegoTokenFixer"%>
<%@page import="com.project.user.SavedNotes"%>
<%@page import="java.util.List"%>
<%@page import="com.project.user.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HomePage</title>
</head>
<body>
	<hr>
<h1 style='color:red'>Random Vision</h1>

<h5 >By Sakshi Ghardale(July-2024)</h5>
<hr><br>
<h2 >Welcome To Random Vision!!</h2>
<hr>
	<%
	User user = (User) session.getAttribute("user");
	String name = user.getUsername();
	System.out.println(session.getAttribute("userNote"));
	String currentNote = (String) session.getAttribute("userNote");
	%>
	<h1>
		Welcome
		<%=name%></h1>
	<hr>
	<h1>
		Your current reflection quote :
		<%=currentNote%></h1>

	<h3>
		<a href="Save">Save Quote</a>
	</h3>

	<%
	Integer stateOfMailSent = (Integer)session.getAttribute("stateOfMailSent");
	System.out.println(stateOfMailSent);
	String mailSentOrNot = (String) session.getAttribute("MailSentOrNot");
	System.out.println(mailSentOrNot);
	if (mailSentOrNot != null &&stateOfMailSent != null && stateOfMailSent == 1) {
	%>
	<h2 style='color: pink'><%=mailSentOrNot%></h2>
	<%
	} else {
	%>
	<h3>
		<a href="Sendmail">Send Current Quote To Gmail</a>
	</h3>
	<%
	}
	%>
	<h3>
		<a href="Logout">Logout</a>
	</h3>
	<%
	List<SavedNotes> notes = (List<SavedNotes>) request.getAttribute("notes_info");
	String StatementForSaving = (String) request.getAttribute("StatementForSaving");

	System.out.println("StatementForSaving :" + StatementForSaving);

	if (notes != null) {
	%>
	<h2 style='color: orange'><%=StatementForSaving%></h2>
	<%
	for (SavedNotes note : notes) {
	%>
	<h2 style='color: green'>
		<b>Quote : <%=note.getNote()%></b>
	</h2>
	Time :
	<%=note.getTime()%><hr>
	<br>
	<%
	}

	}
	%>

</body>
</html>