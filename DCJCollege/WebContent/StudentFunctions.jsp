<%@page import="util.TransformHtml"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="StudentFunctionsErr.jsp"
    import="data.account.*,java.util.*,data.util.*,bean.account.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% AccountBeanHelper.getInstance().closeConnection(); %>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="include/headInclude.jsp"></jsp:include>
<jsp:useBean id="account" scope="session" class="bean.account.AccountBean"></jsp:useBean>
<%
AccountBeanHelper helper = AccountBeanHelper.getInstance();
List<Course> availCourses = helper.getAvailableCourses(account);
%>

<title>Student Portal</title>
<script type="text/javascript" src="js/StudentFunctions.js"></script> 
</head>
<body>
	<nav class="top-nav">
		<a href="#" onclick="showFunction(this)">0</a>
		<a href="#" onclick="showFunction(this)">1</a>
		<a href="#" onclick="showFunction(this)">2</a>
		<a href="#" onclick="showFunction(this)">3</a>
		<a href="MainMenu.jsp">&lt;Back</a>
	</nav>
	<h1 class="banner">Student Functions</h1>
	<p>Please select one of the options above.</p>
	<p class="errText">${errText}</p>
	<p class="infoText"><strong>${infoText}</strong></p>
	<div id="div0" class="hidden-modal-div">
		<div class="inner-modal-div" id="AddDropDiv">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Registration Form</h3>
			<form action="StudentServices" method="post" onsubmit="event.preventDefault();updateRegistration(this);">
				<p>Please confirm your information is correct and make changes as needed</p>
				<p>
					<label for="fname">First Name</label>
					<input type="text" name="fname" required value="<jsp:getProperty name="account" property="fname" />" />
				</p>
				<p>
					<label for="lname">Last Name</label>
					<input type="text" name="lname" required value="<jsp:getProperty name="account" property="lname" />" />
				</p>
				<p>Address</p>
				<input type="text" name="street" required placeholder="Street" size="50" value="<jsp:getProperty name="account" property="street" />" /><br />
				<input type="text" name="city" required placeholder="City" value="<jsp:getProperty name="account" property="city" />" />
				<input type="text" name="state" required placeholder="ST" maxlength="2" size="2" value="<jsp:getProperty name="account" property="state" />" />
				<input type="text" name="zip" required placeholder="Zip" maxlength="5" size="5" value="<%= (account.getZip() == 0 ? "" : account.getZip()) %>" />
				<p>
					<label for="phone">Phone Number</label>
					<input type="text" name="phone" required maxlength="10" size="10" value="<%= (account.getPhone() == 0 ? "" : account.getPhone()) %>" />
				</p>
				<input type="hidden" name="reqType" value="updateRegistration" />
				<input type="hidden" name="reqOrigin" value="StudentFunctions.jsp" />
				<input type="submit" value="Update" />
			</form>
		</div>
	</div>
	<div id="div1" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Add/Drop a Course</h3>
			<table class="course-listing" style="border:1px #333 solid">
				<tr><th colspan="5">Courses Available</th></tr>
				<%=
					TransformHtml.getTableRows(5, helper.getAvailableCourses(account))
				%>
			</table>
			<form action="StudentServices" method="post">
				<table>
				<tr><th>Course</th><th>Section to Add</th></tr>
				<tr>
				<td><select name="courseAddSelection" style="width:100%;">
					<option>---</option> 
					<%=
						TransformHtml.getOptionList(helper.getAvailableCourses(account))
					%>
				</select></td>
				<td><select name="sectionAddSelection" style="width:100%;">
					<option>---</option>
					<%
						List<Course> courses = helper.getAvailableCourses(account);
						for(Course c : courses) {
							out.print("<options>---</option>\n" + TransformHtml.getOptionList(helper.getCourseSections(c.getCourse_id())));
						}
					%>
				</select>
				</td></tr>
				<tr><td colspan="2"><input type="submit" value="Add" /></td></tr>
				</table>
				<input type="hidden" name="reqType" value="AddCourse" />
				<input type="hidden" name="reqOrigin" value="StudentFunctions.jsp" />
			</form>
			
			<form action="StudentServices" method="post">
				<table>
				<tr><th>Section to Drop</th></tr>
				<tr>
				<td><select name="courseDropSelection" style="width:100%;">
					<option>---</option>
					<%
						List<Course> courses2 = helper.getMyCourses(account);
						for(Course c : courses) {
							out.print("<option>---</option>" + TransformHtml.getOptionList(helper.getCourseSections(c.getCourse_id())));
						}
					%>
				</select></td>
				<td><input type="submit" value="Submit" /></td>
				</table>
				<input type="hidden" name="reqType" value="DropCourse" /> 
				<input type="hidden" name="reqOrigin" value="StudentFunctions.jsp" />
			</form>
		</div>
	</div>
	
	<div id="div2" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Transcript</h3>
			<form action="StudentServices" method="post">
				<input type="submit" value="Refresh Transcript" />
				<input type="hidden" name="reqType" value="Transcript" />
				<input type="hidden" name="reqOrigin" value="StudentFunctions.jsp" />
			</form>
		</div>
	</div>
	
	<div id="div3" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<h3>Pay Fees</h3>
			<p>Fees Due: <%= String.format("$%0,1.2f", helper.getTotalFees(account) - helper.getPaidFees(account)) %></p>
			<form>
				<p>
					<label for="amount">Amount</label>
					<input type="text" name="amount" />
				</p>
				<input type="submit" value="Submit" />
				<input type="hidden" name="reqType" value="PayFees" />
				<input type="hidden" name="reqOrigin" value="StudentFunctions.jsp" />
			</form>
		</div>
	</div>
</body>
</html>