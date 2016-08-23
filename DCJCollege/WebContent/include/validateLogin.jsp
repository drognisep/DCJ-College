<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.account.AccountBean"%>
<%
	AccountBean account = null;
	Object o = session.getAttribute("login-data");
	if(o == null) {
		response.sendRedirect("index.jsp");
		return;
	}
	
	account = (AccountBean)o;
%>