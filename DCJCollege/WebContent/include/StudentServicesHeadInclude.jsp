<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%!
Object o;
String[] myCourses, availCourses, mySections, availSections;
String transcript;
Double feesPaid, feesDue;	
%>
<%
o = session.getAttribute("myCourses");
myCourses = (o==null? new String[] {"Error retrieving your courses"} : (String[])o);
o = session.getAttribute("availCourses");
availCourses = (o==null? new String[] {"Error retrieving available courses"} : (String[])o);
o = session.getAttribute("mySections");
mySections = (o==null? new String[] {"Error retrieving your sections"} : (String[])o);
o = session.getAttribute("availSections");
availSections = (o==null? new String[] {"Error retrieving available sections"} : (String[])o);
o = session.getAttribute("transcript");
transcript = (o==null? "Error retrieving transcript" : (String)o);
o = session.getAttribute("feesPaid");
feesPaid = (o==null? 0.0 : (Double)o);
o = session.getAttribute("feesDue");
feesDue = (o==null? 0.0 : (Double)o);
%>