package servlet.worker;

import inval.object.ObjValidator;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.account.AccountBean;
import data.account.AccountBeanHelper;
import data.util.DbHelperException;

/**
 * Servlet implementation class PrintHonorsListServlet
 */
@WebServlet("/HonorsListServlet")
public class HonorsListServlet extends HttpServlet {
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
		if (!reqOrigin.equals("ReportingServicesServlet")
				|| !reqType.equals("HonorsList")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("ReportingServicesServlet");
			return;
		}
		AccountBean account = (AccountBean) session.getAttribute("account");
		int dept_id = Integer.parseInt(request.getParameter("dept_id"));
		int term = Integer.parseInt(request.getParameter("term"));

		if (ObjValidator.emptyStrings(reqOrigin, reqType)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("ReportingFunctions.jsp");
			return;
		}
		if (ObjValidator.anyNull(account, dept_id, term)) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		List<String> names = null;
		try {
			names = instance.getHonorsList(dept_id, term);
		} catch (DbHelperException e) {
			session.setAttribute("errText",
					"Unable to obtain honors list for given department.");
			e.printStackTrace();
			response.sendRedirect("ReportingFunctions.jsp");
			return;
		}
		session.setAttribute("reqReturn", names);
		request.setAttribute("reqOrigin", "HonorsListServlet");

		RequestDispatcher rd = request
				.getRequestDispatcher("ReportingServicesServlet");
		rd.forward(request, response);
	}

//	    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//			Enumeration<String> names = request.getParameterNames();
//			StringBuilder sb = new StringBuilder();
//			response.setContentType("text/html");
//			sb.append("<h1>Request parameter listing</h1>");
//			sb.append("<ul>");
//			while(names.hasMoreElements()) {
//				String cur = names.nextElement();
//				sb.append("<li>").append(cur).append(" in request equals: ")
//					.append(request.getParameter(cur));
//				sb.append("</li>");
//			}
//			sb.append("</ul>");
//			sb.append("<h1>All reqOrigin parameters</h1>\n<ul>");
//			for(String s : request.getParameterValues("reqOrigin")) {
//				sb.append("<li>" + s + "</li>");
//			}
//			sb.append("<li>Current reqOrigin: " + request.getAttribute("reqOrigin") + "</li></ul>");
//			
//			sb.append("<h1>Attribute names found:</h1><ul>");
//			Enumeration<String> attNames = request.getAttributeNames();
//			while(attNames.hasMoreElements()) {
//				sb.append("<li>").append(attNames.nextElement()).append("</li>");
//			}
//			sb.append("</ul>");
//			
//			Logger.getLogger("AddCourseServlet").info("In working servlet for: " + request.getParameter("reqType") + "\n" + sb.toString());
//			response.getWriter().print(sb.toString());
//		}
}
