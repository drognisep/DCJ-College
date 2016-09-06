package servlet.worker;

import inval.object.ObjValidator;

import java.io.IOException;
import java.util.List;

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
import data.util.TranscriptEntry;

/**
 * Servlet implementation class TranscriptServlet
 */
@WebServlet("/TranscriptServlet")
public class TranscriptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String reqOrigin = (String) request.getAttribute("reqOrigin");
		String reqType = (String) request.getParameter("reqType");
		AccountBean account = (AccountBean) session.getAttribute("account");

		if (ObjValidator.emptyStrings(reqOrigin, reqType)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("StudentFunctions.jsp");
			return;
		}
		if (ObjValidator.anyNull(account)) {
			response.sendRedirect("index.jsp");
			return;
		}
		if (!reqOrigin.equals("StudentServicesServlet")
				|| !reqType.equals("Transcript")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("StudentServices.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		List<TranscriptEntry> entries = null;
		try {
			entries = (List<TranscriptEntry>)instance.getTranscript(account);
		} catch (DbHelperException e) {
			session.setAttribute("errText",
					"Unable to generate student transcript.");
			e.printStackTrace();
			response.sendRedirect("StudentFunctions.jsp");
		}
		session.setAttribute("reqReturn", entries);
		session.setAttribute("transcript", entries);
		//student services requesting transcript, but I thought we'd decided reqReturn
		request.setAttribute("reqOrigin", "TranscriptServlet");

		RequestDispatcher rd = request
				.getRequestDispatcher("StudentServicesServlet");
		rd.forward(request, response);
	}
}
