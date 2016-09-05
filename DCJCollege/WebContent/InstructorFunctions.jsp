<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" errorPage="InstructorFunctionsErr.jsp"
	import="data.account.AccountBeanHelper"%>
<%@ page import="util.*, bean.account.*, data.util.*"%>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<% 
AccountBeanHelper.getInstance().closeConnection();
String reqOrigin = "InstructorFunctions.jsp";
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
	<nav class="top-nav">
		<a id="nav0" href="#" onclick="showFunction(this)">0</a>
		<a id="nav1" href="#" onclick="showFunction(this)">1</a>
		<a id="nav2" href="#" onclick="showFunction(this)">2</a>
		<a id="nav3" href="#" onclick="showFunction(this)">3</a>
		<a id="nav3" href="#" onclick="showFunction(this)">4</a>
		<a href="MainMenu.jsp">&lt;Back</a>
	</nav>
	<h1 class="banner">Instructor Portal</h1>
	<p>Please select one of the options above.</p>
	<p class="errText">${errText}</p>
	<p class="infoText">${infoText}</p>
	<div id="div0" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Course creation</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="course_id">Course ID</label> 
					<input type="text" name="course_id" required maxlength="3" pattern="[A-Z][0-9]{2}"
						title="Should be in format: A00" />
				</p>
				<p>
					<label for="course_name">Course Name</label>
					<input type="text" name="course_name" required maxlength="30" pattern="[A-Za-z0-9 ]+"
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
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
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
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
				<input type="submit" value="Submit" />
			</form>
			<h3>Drop Instructor</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="section_id">Section to stop teaching</label>
					<select name="section_id">
						<option>---</option>
						<%=
							/* for(Course c : helper.getMyCourses(account)) {
								out.println(
									TransformHtml.getOptionList(
											helper.getCourseSections(c.getCourse_id())
									)
								);
							} */
							TransformHtml.getOptionList(helper.getMyCourses(account))
						%>
					</select>
				</p>
				<p>
					<label for="instr_id">Instructor ID</label>
					<input type="text" name="instr_id" />
				</p>
				<input type="hidden" name="reqType" value="DropInstructor" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>

	<div id="div2" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Alter Term Schedule</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="section_id">Section ID</label>
					<input type="text" name="section_id" />
				</p>
				<p>
					<label for="course_id">Course ID</label>
					<input type="text" name="course_id" />
				</p>
				<p>
					<label for="term">Term</label>
					<input type="text" name="term" />
				</p>
				<p>
					<label for="room">Room</label>
					<input type="text" name="room" />
				</p>
				<p>
					<label for="schedule_id">Schedule ID</label>
					<input type="text" name="schedule_id" />
				</p>
				<p>
					<label for="instr_id">Instructor ID</label>
					<input type="text" name="instr_id" />
				</p>
				<p>
					<label for="capacity">Capacity</label>
					<input type="text" name="capacity" />
				</p>
				<input type="submit" value="Submit" />
				<input type="hidden" name="reqType" value="UpdateSection" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
			</form>
		</div>
	</div>
	<div id="div3" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Update Grades</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="section_id">Section ID</label>
					<input type="text" name="section_id" />
				</p>
				<p>
					<label for="student_id">Student ID</label>
					<input type="text" name="student_id" />
				</p>
				<p>
					<label for="grade">Grade</label>
					<input type="text" name="grade" pattern="^[ABCDF][+-]?$" title="Must be a letter grade" />
				</p>
				<input type="submit" value="Submit" />
				<input type="hidden" name="reqType" value="UpdateGrades" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
			</form>
		</div>
	</div>
	
	<div id="div4" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Add Section</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="course_id">Your courses</label>
					<select name="course_id">
						<option>---</option>
						<%=
							/* for(Course c : helper.getMyCourses(account)) {
								out.println(
									TransformHtml.getOptionList(
											helper.getCourseSections(c.getCourse_id())
									)
								);
							} */
							TransformHtml.getOptionList(helper.getMyCourses(account))
						%>
					</select>
				</p>
				<p>
					<label for="section_id">New Section ID</label>
					<input type="text" name="section_id" required pattern="^[A-Z]{2}[0-9]{2}$" title="Must adhere to this regex: ^[A-Z]{2}[0-9]{2}$" />
				</p>
				<p>
					<label for="term">Term</label>
					<input type="text" name="term" required pattern="^[0-9]{4}[12]$" title="Must adhere to this regex: ^[0-9]{4}[12]$" />
				</p>
				<p>
					<label for="room">Room</label>
					<input type="text" name="room" required pattern="^[0-9]+$" title="Must adhere to this regex: ^[0-9]+$" />
				</p>
				<p>
					<label for="schedule_id">Schedule ID</label>
					<input type="text" name="schedule_id" required pattern="^[1]?[0-9]$" title="Must adhere to this regex: ^[1]?[0-9]$" />
				</p>
				<p>
					<label for="capacity">Capacity</label>
					<input type="text" name="capacity" required pattern="^[0-9]{1,3}$" title="Must adhere to this regex: ^[0-9]{1,3}$" />
				</p>
				<p>
					<label for="instr_id">Instructor ID</label>
					<input type="text" name="instr_id" required pattern="^i[0-9]{5}$" title="Must adhere to this regex: ^i[0-9]{5}$" />
				</p>
				<input type="hidden" name="reqType" value="AddSection" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
				<input type="submit" value="Submit" />
			</form>
			<h3>Remove Section</h3>
			<form action="InstructorServicesServlet" method="post">
				<p>
					<label for="course_id">Your courses</label>
					<select name="course_id" onchange="updateCourseSections(this, 'sectionDropdown')">
						<option>---</option>
						<%=
							/* for(Course c : helper.getMyCourses(account)) {
								out.println(
									TransformHtml.getOptionList(
											helper.getCourseSections(c.getCourse_id())
									)
								);
							} */
							TransformHtml.getOptionList(helper.getMyCourses(account))
						%>
					</select>
				</p>
				<p>
					<label for="section_id">Section of course to remove</label>
					<select name="section_id" id="sectionDropdown">
						<option>---</option>
					</select>
				</p>
				<input type="hidden" name="reqType" value="RemoveSection" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
				<input type="submit" value="Submit" />
			</form>
		</div>
	</div>
	<jsp:include page="include/footer.jsp"></jsp:include>
</body>
</html>