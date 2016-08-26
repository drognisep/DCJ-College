<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="include/headInclude.jsp"></jsp:include>
<title>Student Portal</title>
<script type="text/javascript" src="js/StudentFunctions.js"></script>
</head>
<body>
	<nav class="top-nav">
		<a href="#" onclick="showFunction(this)">0</a>
		<a href="#" onclick="showFunction(this)">1</a>
		<a href="#" onclick="showFunction(this)">2</a>
		<a href="#" onclick="showFunction(this)">3</a>
		<a href="MainMenu.jsp">Back</a>
	</nav>
	<h1 class="banner">Student Functions</h1>
	<p>Please select one of the options above.</p>
	<div id="div0" class="hidden-modal-div">
		<div class="inner-modal-div">
			<h3>Registration Form</h3>
		</div>
	</div>
	<div id="div1" class="hidden-modal-div">
		<div class="inner-modal-div">
			<h3>Add/Drop a Course</h3>
		</div>
	</div>
	<div id="div2" class="hidden-modal-div">
		<div class="inner-modal-div">
			<h3>Request Transcript</h3>
		</div>
	</div>
	<div id="div3" class="hidden-modal-div">
		<div class="inner-modal-div">
			<h3>Pay Fees</h3>
		</div>
	</div>
</body>
</html>