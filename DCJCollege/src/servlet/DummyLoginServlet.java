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

/**
 * Servlet implementation class DummyLoginServlet
 */
@WebServlet("/DummyLogin")
public class DummyLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger("DummyLoginServlet");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DummyLoginServlet() {}

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
		
		response.getOutputStream().println("<h1>Good!</h1>");
	}

}
