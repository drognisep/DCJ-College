<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="data.account.*"
	errorPage="ReportingFunctionsErr.jsp"%>
<jsp:include page="include/validateLogin.jsp"></jsp:include>
<% 
AccountBeanHelper.getInstance().closeConnection();
String reqOrigin = "ReportingFunctions.jsp";
String sCatalog = (String)(session.getAttribute("catalog") == null ? "<h3>Please refresh to view</h3>" : session.getAttribute("catalog"));
String sSchedule = (String)(session.getAttribute("schedule") == null ? "<h3>Please refresh to view</h3>" : session.getAttribute("schedule"));
String sHonors = (String)(session.getAttribute("honorsList") == null ? "<h3>Please refresh to view</h3>" : session.getAttribute("honorsList"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="include/headInclude.jsp"></jsp:include>
<title>Instructor Portal</title>
<script type="text/javascript" src="js/ReportingFunctions.js"></script>
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
			<p class="x-btn" onclick="hideFunction();">X</p>
			<form action="ServiceServlet" method="post">
				
				<input type="hidden" name="reqType" value="" />
				<input type="hidden" name="reqOrigin" value="" />
			</form>
		</div>
	</div>
	--%>
	<nav class="top-nav">
		<a href="#" onclick="showFunction(this)">Will be replaced</a>
		<a href="#" onclick="showFunction(this)">Same here</a>
		<a href="#" onclick="showFunction(this)">Same here</a>
		<a href="MainMenu.jsp">&lt;Back</a>
	</nav>
	<h1 class="banner">Reporting Functions</h1>
	<p class="errText">${errText}</p>
	<p class="infoText">${infoText}</p>
	<div id="div0" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<form action="ReportingServicesServlet" method="post">
				${sCatalog}
				<input type="submit" value="Refresh" />
				<input type="hidden" name="reqType" value="PrintCatalog" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
			</form>
		</div>
	</div>
	<div id="div1" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<form action="ReportingServicesServlet" method="post">
				${sSchedule}
				<input type="submit" value="Refresh" />
				<input type="hidden" name="reqType" value="PrintTermSchedule" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
			</form>
		</div>
	</div>
	<div id="div2" class="hidden-modal-div">
		<div class="inner-modal-div">
			<p class="x-btn" onclick="hideFunction();">X</p>
			<form action="ReportingServicesServlet" method="post">
				${sHonors}
				<input type="submit" value="Refresh" />
				<input type="hidden" name="reqType" value="HonorsList" />
				<input type="hidden" name="reqOrigin" value="<%= reqOrigin %>" />
			</form>
		</div>
	</div>
	<jsp:include page="include/footer.jsp"></jsp:include>
</body>
</html>