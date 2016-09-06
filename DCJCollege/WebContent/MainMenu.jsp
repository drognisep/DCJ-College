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
		<a href="StudentServicesServlet">Student</a>
		<a href="InstructorServicesServlet">Instructor</a>
		<a href="ReportingServicesServlet">Reporting</a>
		<a href="Logout">Logout</a>
	</nav>
	<h1 class="banner">Welcome to your home page</h1>
	<h3>Pick one of the options above to continue</h3>
	<table class="news-window">
		<tr><th><h1>The DCJ News Feed</h1></th></tr>
		<tr><td>
			<h3>Everyone getting along!</h3>
			<hr />
			<p>People everywhere are raving about DCJ's harmonious environment. With a beautiful campus and a fantastic course offering, we're giving you 
				more than you bargained for, for a bargain!</p>
			<h3>DCJ voted nation's #1 most <u>awesome</u> college</h3>
			<hr />
			<p>The New York Times has <a href="https://www.google.com/#q=do+you+click+on+everything%3F">voted DCJ as this nation's most awesome college</a>
				for the 5<sup>th</sup> consecutive year! It's an honor to be recognized, but we couldn't have done it without our great staff and active 
				student body.</p>
			<h3>Thank you! :-)</h3>
		</td></tr>
	</table>
	<jsp:include page="include/footer.jsp"></jsp:include>
</body>
</html>