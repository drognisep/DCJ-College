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
 * Servlet implementation class DropInstructorServlet
 */
@WebServlet("/DropInstructorServlet")
public class DropInstructorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String reqOrigin =(String) request.getAttribute("reqOrigin");
		String reqType = request.getParameter("reqType");

		
		if (!reqOrigin.equals("InstructorServicesServlet")
				|| !reqType.equals("DropInstructor")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("InstructorServicesServlet");
			return;
		}

		AccountBean account = (AccountBean) session.getAttribute("account");

		String instr_id = request.getParameter("instr_id");
		String section_id = request.getParameter("section_id");
		

		if (ObjValidator.emptyStrings(reqOrigin, reqType, instr_id, section_id)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		}
		if (ObjValidator.anyNull(account)) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");

		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		boolean i = instance.dropInstructor(account, 
				section_id,instr_id);
		if (i == true) {
			session.setAttribute("infoText",
					"Instructor dropped from this course successfully");
			response.sendRedirect("InstructorFunctions.jsp");
		} else {
			session.setAttribute("errText",
					"Instructor could not be dropped from this course.");
			response.sendRedirect("InstructorFunctions.jsp");
		}

	}

}
