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

/**
 * Servlet implementation class AddDropCourseServlet
 */
@WebServlet("/DropCourseServlet")
public class DropCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String reqOrigin = (String) request.getAttribute("reqOrigin"); 
		String reqType = request.getParameter("reqType");
		
		
		if(!reqOrigin.equals("StudentServicesServlet") || !reqType.equals("DropCourse")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("StudentServices.jsp");
			return;
		}
		
		AccountBean account = (AccountBean)session.getAttribute("account");
		
		String course_id = request.getParameter("course_id");
		String section_id = request.getParameter("section_id");
		
		if(ObjValidator.emptyStrings(reqOrigin, reqType, course_id, section_id)) {
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
		boolean i = instance.dropSection(account,course_id, section_id);
		if(i == true){
			session.setAttribute("infoText", "Course dropped successfully");
			response.sendRedirect("StudentFunctions.jsp");
		}else{
			session.setAttribute("errText", "Course could not be dropped");
			response.sendRedirect("StudentFunctions.jsp");
		}
		
	}
}
