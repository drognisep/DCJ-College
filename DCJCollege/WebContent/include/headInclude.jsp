<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String errText = "";
	Object o = session.getAttribute("errText");
	if(o != null) {
		errText = (String)o;
	}
%>
<link type="text/css" rel="stylesheet" href="css/style.css" />