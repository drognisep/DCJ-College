<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="random" class="util.RandomBean" scope="application" />
<c:set var="csrfToken" scope="session" value="${random.getToken()}"></c:set>
