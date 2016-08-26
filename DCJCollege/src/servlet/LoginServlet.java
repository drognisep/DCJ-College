package servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.account.AccountBean;
import data.account.AccountBeanHelper;

/**
 * Servlet implementation class DummyLoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger("DummyLoginServlet");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		AccountBean account = null;
		AccountBeanHelper helper = null;
		
		session.setAttribute("errText", "");
		
		Object o = session.getAttribute("csrfToken");
		if(o == null) {
			log.log(Level.SEVERE, "No CSRF token found");
			session.setAttribute("errText", "Access denied, failed CSRF check");
			response.sendRedirect("index.jsp");
			return;
		} else {
			String csrfToken = (String)o;
			if(!csrfToken.equals(request.getParameter("csrf"))) {
				session.setAttribute("errText", "Access denied, failed CSRF check");
				response.sendRedirect("index.jsp");
				return;
			}
		}
		
		if(name == null || pass == null) {
			session.setAttribute("errText", "Missing input parameters");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		helper = AccountBeanHelper.getInstance();
		if(helper.checkCredentials(name, pass)) {
			account = new AccountBean();
			account.setName(name);
			session.setAttribute("login-data", account);
			request.getRequestDispatcher("MainMenu.jsp").forward(request, response);
			return;
		}
		
		session.setAttribute("errText", "An error occurred");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
