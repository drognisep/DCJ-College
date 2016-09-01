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
import data.util.Course;
import data.util.DbHelperException;
import data.util.Section;

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
		
		if(ObjValidator.emptyStrings(reqType, reqOrigin)) {
			response.sendRedirect("StudentFunctions.jsp");
			return;
		} else if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		
/*		try {
			List<Course> myCourses = helper.getMyCourses(account, 1);
			List<Course> availCourses = helper.getAvailableCourses(account);
			ArrayList<Section> availSections = new ArrayList<>();
			List<Section> alMySections = new ArrayList<>(100);
			session.setAttribute("myCourses", myCourses);
			session.setAttribute("availCourses", availCourses);
			
			// My Sections
			for(Course course : myCourses) {
				alMySections = helper.getCourseSections(course.getCourse_id());
			}
//			mySections = new String[alMySections.size()];
//			for(int i = 0; i < mySections.length; i++) {
//				mySections[i] = alMySections.get(i);
//			}
			session.setAttribute("mySections", alMySections);
			
			// Available Sections
			for(Course course : availCourses) {
				List<Section> sections = helper.getCourseSections(course.getCourse_id());
				for(Section s : sections) {
					availSections.add(s);
				}
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
		}*/
		
		request.setAttribute("reqOrigin", "StudentServicesServlet");
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
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().print("Missing request parameters");
					return;
				} else {
					request.getRequestDispatcher("ajaxRegistrationUpdate").forward(request, response);
					return;
				}
			case "UpdateRegistration":
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
					return;
				} else {
					request.getRequestDispatcher("UpdateRegistrationServlet").forward(request, response);
					return;
				}
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
				// FIXME: Add request handler for Transcript
				session.setAttribute("infoText", "Bounced back from unimplemented request handler");
				break;
			default:
				session.setAttribute("errText", "Unrecognized request type: " + reqType);
			}
			break;
		case "TranscriptServlet":
			switch(reqType) {
			case "Transcript":
				// FIXME: Setup receiver for session attribute "reqReturn"
				break;
			default:
				session.setAttribute("errText", "Unrecognized request type: " + reqType);
			}
		default:
			session.setAttribute("errText", "Unrecognized request originator: " + reqOrigin);
		}
		
		log.info("Request Type: " + reqType);
		response.sendRedirect("StudentFunctions.jsp");
	}
}
