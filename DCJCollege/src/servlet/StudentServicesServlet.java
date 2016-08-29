package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.TransformHtml;
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
		
		HttpSession session = request.getSession();
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "Nothing to report...");
		AccountBean account = (AccountBean)session.getAttribute("account");
		if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		try {
			String[] myCourses = helper.getMyCourses(account);
			String[] availCourses = helper.getAvailableCourses(account);
			String[] mySections;
			String[] availSections;
			ArrayList<String> alMySections = new ArrayList<>(100);
			ArrayList<String> alAvailSections = new ArrayList<>(100);
			session.setAttribute("myCourses", myCourses);
			session.setAttribute("availCourses", availCourses);
			
			// My Sections
			for(String course : myCourses) {
				String[] sections = helper.getCourseSections(course);
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
				String[] sections = helper.getCourseSections(course);
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
			session.setAttribute("feesPaid", helper.getFees(account));
			session.setAttribute("feesDue", helper.getFees(account));
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
		
		switch(reqType) {
		case "updateRegistration":
			break;
		default:
			session.setAttribute("errText", "Unrecognized request type");
		}
		
		log.info("Request Type: " + reqType);
		response.sendRedirect("StudentFunctions.jsp");
	}

}
