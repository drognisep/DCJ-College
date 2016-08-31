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

/**
 * Servlet implementation class AddDropCourseServlet
 */
@WebServlet("/AddDropCourseServlet")
public class AddDropCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * This is a template to work off of for working servlets, all of them will follow these steps:
		 *   - Check for valid 'reqOrigin' from request
		 *   - Check for valid 'reqType' from request
		 *   - Check for valid 'account' from session
		 *   - Pull required data from request
		 *   - Check reqOrigin to ensure the request is coming from the right place
		 *   - If all resources are found, set reqOrigin to the name of this working servlet
		 *     (in this case it would be "AddDropCourseServlet")
		 *   - Set 'errText' and 'infoText' session attributes to "" (empty String)
		 *   - If something wasn't available (data is missing, something didn't match, etc) set the
		 *     session attribute "errText" to something that describes the error that the user will
		 *     see, and use response.sendRedirect back to the calling JSP.
		 *   - Process data
		 */
		
		HttpSession session = request.getSession();
		String reqOrigin = request.getParameter("reqOrigin"); // S/b "StudentServicesServlet" here
		String reqType = request.getParameter("reqType");
		AccountBean account = (AccountBean)session.getAttribute("account");
		// Get required data
		if(ObjValidator.emptyStrings(reqOrigin, reqType/*, data, from, request */)) {
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
		//Process data
		session.setAttribute("reqReturn", null /* whatever response */);
	}
}
