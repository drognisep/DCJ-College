package servlet;

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
 * Servlet implementation class InstructorServicesServlet
 */
@WebServlet("/InstructorServices")
public class InstructorServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// FIXME: Give me implementation
		String reqType = request.getParameter("reqType");
		String reqOrigin = request.getParameter("reqOrigin");
		HttpSession session = request.getSession();
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "Nothing to report...");
		AccountBean account = (AccountBean)session.getAttribute("account");
		
		if(ObjValidator.emptyStrings(reqType, reqOrigin)) {
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		} else if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		
		switch(reqType) {
		// Handle individual request types
		case "NewCourse":
			// Handle request from each valid reqOrigin
			break;
		default:
			session.setAttribute("errText", "Unrecognized request");
		}
		
		response.sendRedirect("InstructorFunctions.jsp");
	}
}
