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

import util.TransformHtml;
import bean.account.AccountBean;
import data.account.AccountBeanHelper;
import data.util.Course;
import data.util.Section;

/**
 * Servlet implementation class ReportingServicesServlet
 */
@WebServlet("/ReportingServicesServlet")
public class ReportingServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		String reqOrigin = request.getParameter("reqOrigin");
		String myOrigin = "ReportingServicesServlet";
		String jspOrigin = "ReportingFunctions.jsp";
		HttpSession session = request.getSession();
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		AccountBean account = (AccountBean)session.getAttribute("account");
		
		if(ObjValidator.emptyStrings(reqType, reqOrigin)) {
			response.sendRedirect(jspOrigin);
			return;
		} else if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		switch(reqType) {
		case "PrintCatalog":
			if(reqOrigin.equals(jspOrigin)) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else if(reqOrigin.equals(reqType + "Servlet")) {
				if(ObjValidator.noneNull(session.getAttribute("reqReturn"))
						&& (session.getAttribute("reqReturn") instanceof List<?>) 
						){
					session.setAttribute("catalog",
							TransformHtml.prettyPrintCatalog((List<Course>)session.getAttribute("reqReturn")));
					response.sendRedirect(jspOrigin);
					return;
				} else {
					session.setAttribute("errText", "Missing parameters");
					response.sendRedirect(reqOrigin);
                  return;
				}
			} else {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
		case "PrintTermSchedule":
			if(reqOrigin.equals(jspOrigin)) {
				if(ObjValidator.notEmptyStrings(
						request.getParameter("term")
						)) {
					request.setAttribute("reqOrigin", myOrigin);
					forward(reqType, request, response);
					return;
				} else {
					session.setAttribute("errText", "Missing parameters");
					response.sendRedirect(reqOrigin);
		            return;
				}
			} else if(reqOrigin.equals(reqType + "Servlet")) {
				// FIXME: Figure out the format that this needs to be displayed in.
				if(ObjValidator.noneNull(session.getAttribute("reqReturn"))
						&& (session.getAttribute("reqReturn") instanceof List<?>) 
						) {
					session.setAttribute("schedule",
							TransformHtml.prettyPrintCatalog((List<Course>)session.getAttribute("reqReturn")));
					response.sendRedirect(jspOrigin);
					return;
				} else {
					session.setAttribute("errText", "Missing parameters");
					response.sendRedirect(reqOrigin);
                  return;
				}
			} else {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
		case "HonorsList":
			if(reqOrigin.equals(jspOrigin)) {
				if(ObjValidator.notEmptyStrings(
						request.getParameter("dept_id"),
						request.getParameter("term")
						)) {
					request.setAttribute("reqOrigin", myOrigin);
					forward(reqType, request, response);
					return;
				}
			} else if(reqOrigin.equals(reqType + "Servlet")) {
				if(ObjValidator.noneNull(session.getAttribute("reqReturn"))) {
					session.setAttribute("honorsList", "<table><tr><th>Honors List</th></tr>" + TransformHtml.getTableRows(1, 
							(List<String>)session.getAttribute("reqReturn")) + "</table>");
					response.sendRedirect(jspOrigin);
					return;
				} else {
					session.setAttribute("errText", "Missing parameters");
					response.sendRedirect(reqOrigin);
                  return;
				}
			} else {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
		default:
			session.setAttribute("errText", "Unrecognized request");
		}
		response.sendRedirect(jspOrigin);
	}
	private void forward(String reqType, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher(reqType + "Servlet").forward(req, res); 
	}
}