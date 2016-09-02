<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="InstructorFunctionsErr.jsp"
	import="data.account.AccountBeanHelper"%>
<%@ page import="util.*, bean.account.*, data.util.*"%>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<% 
AccountBeanHelper.getInstance().closeConnection();
String reqOrigin = "ReportingFunctions.jsp";
AccountBeanHelper helper = AccountBeanHelper.getInstance();
AccountBean account = (AccountBean)session.getAttribute("account");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="include/headInclude.jsp"></jsp:include>
<title>Instructor Portal</title>
<script type="text/javascript" src="js/InstructorFunctions.js"></script>
</head>
<body>
	<%-- Nav Template - Number of 'a' should match js array of options
	<nav class="top-nav">
		<a href="#" onclick="showFunction(this)">Will be replaced</a>
		<a href="#" onclick="showFunction(this)">Same here</a>
		<a href="MainMenu.jsp">Back</a>
	</nav>
	--%>
	<%-- <h3 class="banner">Banner</h3> --%>
	<%-- Template div - Number of divs should match js array options
	<div id="div2" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn">X</p>
			<form action="ServiceServlet" method="post">
				
				<input type="hidden" name="reqType" value="" />
				<input type="hidden" name="reqOrigin" value="" />
			</form>
		</div>
	</div>
	--%>
	<nav class="top-nav">
		<a id="nav0" href="#" onclick="showFunction(this)">0</a>
		<a id="nav1" href="#" onclick="showFunction(this)">1</a>
		<a id="nav2" href="#" onclick="showFunction(this)">2</a>
		<a href="MainMenu.jsp">&lt;Back</a>
	</nav>
	<h1 class="banner">Instructor Portal</h1>
	<p class="errText">${errText}</p>
	<p class="infoText">${infoText}</p>
	<div id="div0" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Course creation</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="course_id">Course ID</label> <input type="text"
						name="course_id" required maxlength="3" pattern="[A-Z]{1}[0-9]{2}"
						title="Should be in format: A00" />
				</p>
				<p>
					<label for="course_name">Course Name</label> <input type="text"
						name="course_name" required maxlength="30" pattern="[A-Za-z0-9]+"
						title="Alphanumeric characters only" />
				</p>
				<p>
					<label for="hours">Course Hours</label> <input type="text"
						name="hours" value="3" readonly />
				</p>
				<p>
					<label for="dept_id">Department ID</label> <input type="text"
						name="dept_id" required maxlength="2" pattern="[0-9]{2}"
						title="Should be in format: 00" />
				</p>
				<input type="hidden" name="reqType" value="CreateCourse" />
				<input type="hidden" name="reqOrigin" value="${reqOrigin}" />
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>
	
	<div id="div1" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Add Instructor</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="section_id">Section to sign up for</label>
					<select name="section_id">
						<option>---</option>
						<%
							for(Course c : helper.getAllCourses()) {
								out.println(
									TransformHtml.getOptionList(
											helper.getCourseSections(c.getCourse_id())
									)
								);
							}
						%>
					</select>
				</p>
				<p>
					<label for="instr_id">Instructor ID</label>
					<input type="text" name="instr_id" required />
				</p>
				<input type="hidden" name="reqType" value="AddInstructor" />
				<input type="hidden" name="reqOrigin" value="${reqOrigin}" />
				<input type="submit" value="Submit" />
			</form>
			<h3>Drop Instructor</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="section_id">Section to stop teaching</label>
					<select name="section_id">
						<option>---</option>
						<%
							for(Course c : helper.getMyCourses(account)) {
								out.println(
									TransformHtml.getOptionList(
											helper.getCourseSections(c.getCourse_id())
									)
								);
							}
						%>
					</select>
				</p>
				<input type="hidden" name="reqType" value="DropInstructor" />
				<input type="hidden" name="reqOrigin" value="${reqOrigin}" />
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>

	<div id="div2" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Alter Term Schedule</h3>
			<!-- 
			private int term;
			private String section_id;
			private String course_id;
			private int room;
			private int schedule_id;
			private String instr_id;
			private int capacity;
			private int no_enrolled;
			-->
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="section_id"></label>
					<input type="text" name="section_id" />
				</p>
				<p>
					<label for="course_id"></label>
					<input type="text" name="course_id" />
				</p>
				<p>
					<label for="term"></label>
					<input type="text" name="term" />
				</p>
				<p>
					<label for="room"></label>
					<input type="text" name="room" />
				</p>
				<p>
					<label for=""></label>
					<input type="text" name="name" />
				</p>
				<p>
					<label for=""></label>
					<input type="text" name="name" />
				</p>
				<p>
					<label for=""></label>
					<input type="text" name="name" />
				</p>
				<p>
					<label for=""></label>
					<input type="text" name="name" />
				</p>
				<input type="submit" value="Submit" />
				<input type="hidden" name="reqType" value="AddInstructor" />
				<input type="hidden" name="reqOrigin" value="${reqOrigin}" />
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>
	<jsp:include page="include/footer.jsp"></jsp:include>
</body>
</html>