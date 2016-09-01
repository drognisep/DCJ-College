package servlet.ajax;

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
 * Servlet implementation class RegistrationUpdate
 */
@WebServlet("/ajaxRegistrationUpdate")
public class RegistrationUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String reqOrigin = (String)request.getAttribute("reqOrigin"); // S/b "StudentServicesServlet" here
		String reqType = request.getParameter("reqType");
		AccountBean account = (AccountBean)session.getAttribute("account");
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String phone = request.getParameter("phone");
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		
		
		if(ObjValidator.emptyStrings(reqOrigin, reqType, fname, lname, street, city, state, zip, phone)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("StudentFunctions.jsp");
			return;
		} else if(!reqOrigin.equals("StudentServicesServlet")) {
			session.setAttribute("errText", "Attempted request forgery");
			response.sendRedirect("StudentFunctions.jsp");
			return;
		}
		if(ObjValidator.anyNull(account)) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print("Success!");
	}

}
