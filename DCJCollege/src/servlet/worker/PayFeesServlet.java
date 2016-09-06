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
import data.util.DbHelperException;

/**
 * Servlet implementation class PayFeesServlet
 */
@WebServlet("/PayFeesServlet")
public class PayFeesServlet extends HttpServlet {
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
		if (!reqOrigin.equals("StudentServicesServlet")
				|| !reqType.equals("PayFees")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("StudentServicesServlet");
			return;
		}
		AccountBean account = (AccountBean) session.getAttribute("account");
		double amount = Double.parseDouble(request.getParameter("amount"));

		if (ObjValidator.emptyStrings(reqOrigin, reqType)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("StudentFunctions.jsp");
			return;
		}
		if (ObjValidator.anyNull(account, amount)) {
			response.sendRedirect("index.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		try {
			instance.payFees(account, amount);
		} catch (DbHelperException e) {
			session.setAttribute("errText", "Unable to pay fees at this time.");
			e.printStackTrace();
			response.sendRedirect("StudentFunctions.jsp");
		}

		response.sendRedirect("StudentFunctions.jsp");
	}

}
