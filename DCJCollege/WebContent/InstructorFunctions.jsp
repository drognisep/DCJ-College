<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="include/headInclude.jsp"></jsp:include>
<title>Insert title here</title>
<script type="text/javascript" src="js/InstructorFunctions.js"></script>
</head>
<body>
	<h3>Course creation</h3>
	<form action="InstructorFunctions" method="post">
		<p>
			<label for="course_id">Course ID</label>
			<input type="text" name="course_id" required maxlength="3" pattern="[A-Z]{1}[0-9]{2}" title="Should be in format: A00" />
		</p>
		<p>
			<label for="course_name">Course Name</label>
			<input type="text" name="course_name" required maxlength="30" pattern="[A-Za-z0-9]+" title="Alphanumeric characters only" />
		</p>
		<p>
			<label for="hours">Course Hours</label>
			<input type="text" name="hours" value="3" readonly />
		</p>
		<p>
			<label for="dept_id">Department ID</label>
			<input type="text" name="dept_id" required maxlength="2" pattern="[0-9]{2}" title="Should be in format: 00" />
		</p>
		<input type="hidden" name="reqType" value="newCourse" /> 
		<input type="hidden" name="reqOrigin" value="InstructorFunctions.jsp" />
		<input type="submit" value="Submit" />
	</form>
	<h3>Add Instructor</h3>
	<form action="InstructorFunctions" method="post">
		<p>
			<label for="course_id">Course ID</label>
			<input type="text" name="course_id"  />
		</p>
		<input type="hidden" name="reqType" value="AddInstructor" /> 
		<input type="hidden" name="reqOrigin" value="InstructorFunctions.jsp" />
		<input type="submit" value="Submit" />
	</form>
	<h3>Drop Instructor</h3>
	<form action="InstructorFunctions" method="post">
		<p>
			<label for="addInstructorSelection">Section to Sign up for</label>
			
		</p>
		<input type="hidden" name="reqType" value="DropInstructor" /> 
		<input type="hidden" name="reqOrigin" value="InstructorFunctions.jsp" />
		<input type="submit" value="Submit" />
	</form>
	
	<h3></h3>
	<form action="InstructorFunctions" method="post">
		<p>
			<label for=""></label>
			<input type="text" name="name" />
		</p>
		<input type="submit" value="Submit" />
		<input type="hidden" name="reqType" value="AddInstructor" />
		<input type="hidden" name="reqOrigin" value="InstructorFunctions.jsp" />
		<input type="submit" value="Submit" />
	</form>
	<hr />
	
	<h3>Alter Term Schedule</h3>
	<form action="InstructorFunctions" method="post">
		
	</form>
</body>
</html>