package servlet.worker;

import inval.object.ObjValidator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.account.AccountBean;
import data.account.AccountBeanHelper;
import data.util.Course;

/**
 * Servlet implementation class CreateCourseServlet
 */
@WebServlet("/CreateCourseServlet")
public class CreateCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void service(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	 HttpSession session = request.getSession();
 		String reqOrigin = (String) request.getAttribute("reqOrigin"); 
 		String reqType = request.getParameter("reqType");
 		AccountBean account = (AccountBean)session.getAttribute("account");
    
 		if(!reqOrigin.equals("InstructorServicesServlet") || !reqType.equals("CreateCourse")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("InstructorServicesServlet");
			return;
		}
		
		
		Course course = (Course)request.getAttribute("course");
		
		if(ObjValidator.emptyStrings(reqOrigin, reqType)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		}
		if(ObjValidator.anyNull(account, course)) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		
		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		boolean i = instance.addCourse(account, course);
		if(i == true){
			session.setAttribute("infoText", "Course created successfully");
			response.sendRedirect("InstructorFunctions.jsp");
		}else{
			session.setAttribute("errText", "Course could not be created");
			response.sendRedirect("InstructorFunctions.jsp");
		}
		
     
     
     }
}
