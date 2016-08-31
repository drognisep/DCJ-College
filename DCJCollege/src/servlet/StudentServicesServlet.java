package servlet;

import inval.object.ObjValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import data.util.DbHelperException;

/**
 * Servlet implementation class StudentServicesServlet
 */
@WebServlet("/StudentServices")
public class StudentServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger("StudentServicesServlet");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServicesServlet() {}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		String reqOrigin = request.getParameter("reqOrigin");
		HttpSession session = request.getSession();
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "Nothing to report...");
		AccountBean account = (AccountBean)session.getAttribute("account");
		
		if(ObjValidator.emptyStrings(reqType)) {
			response.sendRedirect("StudentFunctions.jsp");
			return;
		} else if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		} else {
			reqOrigin = "StudentServicesServlet";
		}
		
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		
		try {
			List<String> myCourses = helper.getMyCourses(account, 1);
			List<String> availCourses = helper.getAvailableCourses(account);
			String[] mySections;
			String[] availSections;
			ArrayList<String> alMySections = new ArrayList<>(100);
			ArrayList<String> alAvailSections = new ArrayList<>(100);
			session.setAttribute("myCourses", myCourses);
			session.setAttribute("availCourses", availCourses);
			
			// My Sections
			for(String course : myCourses) {
				List<String> sections = helper.getCourseSections(course);
				for(String s : sections) {
					alMySections.add(s);
				}
			}
			mySections = new String[alMySections.size()];
			for(int i = 0; i < mySections.length; i++) {
				mySections[i] = alMySections.get(i);
			}
			session.setAttribute("mySections", mySections);
			
			// Available Sections
			for(String course : availCourses) {
				List<String> sections = helper.getCourseSections(course);
				for(String s : sections) {
					alAvailSections.add(s);
				}
			}
			availSections = new String[alMySections.size()];
			for(int i = 0; i < availSections.length; i++) {
				availSections[i] = alAvailSections.get(i);
			}
			session.setAttribute("availSections", availSections);
			
			session.setAttribute("transcript", "<table><tr><td>Transcript currently unavailable</td></tr></table>");
			session.setAttribute("feesPaid", helper.getPaidFees(account));
			session.setAttribute("feesDue", helper.getTotalFees(account));
		} catch(DbHelperException dbhx) {
			log.log(Level.SEVERE, "An error occurred: " + dbhx.getMessage());
			dbhx.printStackTrace();
			session.setAttribute("errText", dbhx.getMessage());
			response.sendRedirect("StudentFunctions.jsp");
		}
		
		if(reqType == null) {
			response.sendRedirect("StudentFunctions.jsp");
			return;
		}
		
		// Switch for reqOrigin and reqType
		switch(reqOrigin) {
		case "StudentFunctions.jsp":
			switch(reqType) {
			case "AJAX_UpdateRegistration":
				if(ObjValidator.anyNull(
						request.getParameter("fname"),
						request.getParameter("lname"),
						request.getParameter("street"),
						request.getParameter("city"),
						request.getParameter("state"),
						request.getParameter("zip"),
						request.getParameter("phone")
						)) {
					session.setAttribute("errText", "Missing request parameters");
					response.sendRedirect("StudentFunctions.jsp");
				} else {
					request.getRequestDispatcher("UpdateRegistrationServlet").forward(request, response);
					return;
				}
				break;
			case "AddCourse":
				if(ObjValidator.anyNull(
						request.getParameter("courseAddSelection")
						)) {
					session.setAttribute("errText", "Missing request parameters");
					response.sendRedirect("StudentFunctions.jsp");
				} else {
					request.getRequestDispatcher("AddDropCourseServlet").forward(request, response);
					return;
				}
				break;
			case "DropCourse":
				if(ObjValidator.anyNull(
						request.getParameter("courseDropSelection")
						)) {
					session.setAttribute("errText", "Missing request parameters");
					response.sendRedirect("StudentFunctions.jsp");
				} else {
					request.getRequestDispatcher("AddDropCourseServlet").forward(request, response);
					return;
				}
				break;
			case "Transcript":
				break;
			default:
				session.setAttribute("errText", "Unrecognized request type");
			}
			break;
			
		default:
			session.setAttribute("errText", "Unrecognized request originator");
		}
		
		log.info("Request Type: " + reqType);
		response.sendRedirect("StudentFunctions.jsp");
	}

}
