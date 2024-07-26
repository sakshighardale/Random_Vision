<%@page import="com.project.user.Notes"%>
<html>
<head>
<title>Random Vision</title>
</head>
<body>
<hr>
<h1 style='color:red;'>Random Vision</h1>

<h5>By Sakshi Ghardale(July-2024)</h5>
<hr><br>
<h2>Welcome To Random Vision!!</h2>
<hr>
<%
int min=1, max=50;
int randomNumber = (int) (Math.random() * (max - min + 1)) + min;
System.out.println("inside the index.jsp randNum "+randomNumber);
session.setAttribute("randomNumber", randomNumber);
%>
<%
	Notes notes = (Notes) request.getAttribute("YourNote");
 	 	if (notes != null) {
	String yourNote = notes.getNote();
	System.out.println("Through Session :"+session.getAttribute("randomNumber"));
	
	%>
	<div>
		<H1 style='color:purple;margin-left:80px;'>Quote of your reflection!!!</H1>
		<!-- <h2>ID: <%=  notes.getId()%></h2> -->
		
			<h1 style='color:green;'><%=yourNote%></h1>
	</div>
<form action="SaveTheNote" method=Post>
<h3><input type="submit" value="SAVE The Quote"></h3>
</form>
<hr>
<form action="/RandomVision/" method="post">
<h3><input type="submit" value="To Home Page"></h3>
</form>

	<%} %>
<form action="Note" method=post>
<h3><input type="submit" value="Get Your Reflection"></h3>
</form>

</body>
</html>
