<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="include/headInclude.jsp"></jsp:include>
<jsp:include page="include/csrfToken.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to DCJ College</title>
</head>
<body>
	<h1>Welcome to DCJ College!</h1>
	<br />
	<h3>Please login to continue</h3>
	<form action="Login" method="post">
		<p>
			<label for="name">User name</label>
			<input type="text" name="name" required />
		</p>
		<p>
			<label for="pass">Password</label>
			<input type="password" name="pass" required />
		</p>
		<input type="submit" value="Login" />
		<input type="hidden" name="csrf" value='${csrfToken}' />
	</form>
	<p class="errText">${errText}</p>
</body>
</html>