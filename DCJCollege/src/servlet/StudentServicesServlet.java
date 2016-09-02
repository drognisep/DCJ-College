package servlet;

import inval.object.ObjValidator;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.account.AccountBean;
import data.util.TranscriptEntry;

/**
 * Servlet implementation class StudentServicesServlet
 */
@WebServlet("/StudentServices")
public class StudentServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentServicesServlet() {
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "unchecked" })
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		String reqOrigin = request.getParameter("reqOrigin");
		String myOrigin = "StudentServicesServlet";
		String jspOrigin = "StudentFunctions.jsp";
		HttpSession session = request.getSession();
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		AccountBean account = (AccountBean) session.getAttribute("account");

		if (ObjValidator.emptyStrings(reqType, reqOrigin)) {
			response.sendRedirect("StudentFunctions.jsp");
			return;
		} else if (account == null) {
			response.sendRedirect("index.jsp");
			return;
		}

		// Switch for reqOrigin and reqType
		switch (reqType) {
		case "AJAX_UpdateRegistration":
			if (ObjValidator.anyNull(request.getParameter("fname"),
					request.getParameter("lname"),
					request.getParameter("street"),
					request.getParameter("city"),
					request.getParameter("state"), request.getParameter("zip"),
					request.getParameter("phone"))) {
				session.setAttribute("errText", "Missing request parameters");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print("Missing request parameters");
				return;
			} else {
				request.setAttribute("reqOrigin", myOrigin);
				request.getRequestDispatcher("ajaxRegistrationUpdate").forward(
						request, response);
				return;
			}
			// case "UpdateRegistration":
			// if (ObjValidator.anyNull(request.getParameter("fname"),
			// request.getParameter("lname"),
			// request.getParameter("street"),
			// request.getParameter("city"),
			// request.getParameter("state"), request.getParameter("zip"),
			// request.getParameter("phone"))) {
			// session.setAttribute("errText", "Missing request parameters");
			// response.sendRedirect("StudentFunctions.jsp");
			// return;
			// } else {
			// request.setAttribute("reqOrigin", myOrigin);
			// request.getRequestDispatcher("UpdateRegistrationServlet")
			// .forward(request, response);
			// return;
			// }
		case "AddCourse":
			if (!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
			}
			if (ObjValidator.emptyStrings(
					request.getParameter("courseAddSelection"),
					request.getParameter("sectionAddSelection"))) {
				session.setAttribute("errText", "Missing request parameters");
				response.sendRedirect("StudentFunctions.jsp");
			} else {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			}
			break;
		case "DropCourse":
			if (!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
			}
			if (ObjValidator.emptyStrings(request.getParameter("course_id"),
					request.getParameter("section_id"))) {
				session.setAttribute("errText", "Missing request parameters");
				response.sendRedirect("StudentFunctions.jsp");
			} else {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			}
			break;
		case "Transcript":
			if (reqOrigin.equals(jspOrigin)) {
				// FIXME: Get request parameters!
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else if (reqOrigin.equals(reqType + "Servlet")) {
				Object o = session.getAttribute("transcript");
				List<TranscriptEntry> items;
				if (o == null || !(o instanceof List<?>)) {
					session.setAttribute("errText", "No response object found");
					response.sendRedirect(jspOrigin);
					return;
				} else {
					items = (List<TranscriptEntry>) session.getAttribute("transcript");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("<table>");
				sb.append("<tr><th>Last Name</th><th>Course ID</th><th>Course Name</th><th>Grade</th></tr>");
				for (TranscriptEntry e : items) {
					sb.append("<tr>");
					sb.append("<td>").append(e.getLast_name()).append("</td>");
					sb.append("<td>").append(e.getCourse_id()).append("</td>");
					sb.append("<td>").append(e.getCourse_name())
							.append("</td>");
					sb.append("<td>").append(e.getSection_gpa())
							.append("</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");
				session.setAttribute("transcript", sb.toString());
				response.sendRedirect(jspOrigin);
				return;
			} else {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
			}
			break;
		case "PayFees":
			if (!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
			}
			if (ObjValidator.notEmptyStrings(
					request.getParameter("amount")
					)) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		default:
			session.setAttribute("errText", "Unrecognized request type: "
					+ reqType);
		}

		response.sendRedirect(jspOrigin);
	}

	private void forward(String reqType, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher(reqType + "Servlet").forward(req, res);
	}
}
