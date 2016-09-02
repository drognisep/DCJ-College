<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="bean.account.AccountBean"%>
<%
	AccountBean account = null;
	Object o = session.getAttribute("account");
	if(o == null) {
		session.setAttribute("errText", "Unauthorized access, please login");
		session.setAttribute("infoText", "");
		response.sendRedirect("index.jsp");
		return;
	}
	
	account = (AccountBean)o;
%>