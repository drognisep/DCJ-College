<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="include/headInclude.jsp"></jsp:include>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<title>DCJ College - Main Menu</title>
</head>
<body>
	<nav class="top-nav">
		<a href="StudentFunctions.jsp">Student</a>
		<a href="AdministrativeFunctions.jsp">Administrative</a>
		<a href="ReportingFunctions.jsp">Reporting</a>
		<a href="Logout">Logout</a>
	</nav>
	<h1>Welcome to your home page</h1>
	<h3>Pick one of the options above to continue</h3>
</body>
</html>