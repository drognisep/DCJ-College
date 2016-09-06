package servlet.worker;

import inval.object.ObjValidator;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.account.AccountBean;
import data.account.AccountBeanHelper;

/**
 * Servlet implementation class AddCourseServlet
 */
@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String reqOrigin = (String)request.getAttribute("reqOrigin"); 
		String reqType = request.getParameter("reqType");
		
		if(!reqOrigin.equals("StudentServicesServlet") || !reqType.equals("AddCourse")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("StudentServices.jsp");
			return;
		}
		
		AccountBean account = (AccountBean)session.getAttribute("account");
		
		String courseAddSelection = request.getParameter("courseAddSelection");
		String sectionAddSelection = request.getParameter("sectionAddSelection");
		
		if(ObjValidator.emptyStrings(reqOrigin, reqType, courseAddSelection, sectionAddSelection)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("StudentFunctions.jsp");
			return;
		}
		if(ObjValidator.anyNull(account)) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		
		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		boolean i = instance.enrollSection(account, courseAddSelection, sectionAddSelection);
		if(i == true){
			session.setAttribute("infoText", "Course added successfully");
			response.sendRedirect("StudentFunctions.jsp");
		}else{
			session.setAttribute("errText", "Course could not be added");
			response.sendRedirect("StudentFunctions.jsp");
		}
		
	}
}
