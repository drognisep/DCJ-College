<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../css/style.css" type="text/css" rel="stylesheet" />
<title>Error</title>
</head>
<body>
<h1>An error occurred on the Instructor Functions page</h1>
<h3>${pageContext.errorData.throwable }</h3>
<p>${pageContext.errorData.throwable.cause}</p>
<a href="InstructorFunctions.jsp"><button>Go back</button></a>
<a href="Logout"><button>Logout</button></a>
</body>
</html>