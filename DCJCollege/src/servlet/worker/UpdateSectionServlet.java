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
import data.util.Section;

/**
 * Servlet implementation class UpdateSectionServlet
 */
@WebServlet("/UpdateSectionServlet")
public class UpdateSectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String reqOrigin = (String) request.getAttribute("reqOrigin");
		String reqType = request.getParameter("reqType");

		if (!reqOrigin.equals("InstructorServicesServlet")
				|| !reqType.equals("UpdateSection")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("InstructorServicesServlet");
			return;
		}

		AccountBean account = (AccountBean) session.getAttribute("account");
		Section section = (Section) request.getAttribute("section");

		if (ObjValidator.emptyStrings(reqOrigin, reqType)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		}
		if (ObjValidator.anyNull(account, section)) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");

		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		boolean i = instance.updateSection(account, section);
		if (i == true) {
			session.setAttribute("infoText", "Section updated successfully");
			response.sendRedirect("InstructorFunctions.jsp");
		} else {
			session.setAttribute("errText", "Section could not be updated.");
			response.sendRedirect("InstructorFunctions.jsp");
		}

	}

}
