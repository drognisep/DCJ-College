package servlet;

import inval.object.ObjValidator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Servlet implementation class InstructorServicesServlet
 */
@WebServlet("/InstructorServicesServlet")
public class InstructorServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		String reqOrigin = request.getAttribute("reqOrigin") == null ? request.getParameter("reqOrigin") : (String)request.getAttribute("reqOrigin");
		String myOrigin = "InstructorServicesServlet";
		String jspOrigin = "InstructorFunctions.jsp";
		HttpSession session = request.getSession();
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");
		AccountBean account = (AccountBean)session.getAttribute("account");
		
		if(ObjValidator.emptyStrings(reqType, reqOrigin)) {
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		} else if(account == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
//		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		
		/*
		Branching origin check
			if(reqOrigin.equals(jspOrigin)) {
				
			} else if(reqOrigin.equals(reqType + "Servlet")) {
				
			} else {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
			}
		*/
		/*
		Handler w/ Objects
			case "CreateCourses":
				if(!reqOrigin.equals(jspOrigin)) {
					session.setAttribute("errText", "Invalid request origin");
					response.sendRedirect(jspOrigin);
				}
				if(ObjValidator.noneNull(request.getAttribute("course"))) {
					request.setAttribute("reqOrigin", myOrigin);
					forward(reqType, request, response);
					return;
				} else {
					session.setAttribute("errText", "Missing parameters");
					response.sendRedirect(reqOrigin);
                  return;
				}
		*/
		/*
		Handler w/ Strings
			case "AddInstructor":
				if(!reqOrigin.equals(jspOrigin)) {
					session.setAttribute("errText", "Invalid request origin");
					response.sendRedirect(jspOrigin);
					return;
				}
				if(ObjValidator.notEmptyStrings(
						request.getParameter("instr_id"),
						request.getParameter("section_id")
						)) {
					request.setAttribute("reqOrigin", myOrigin);
					forward(reqType, request, response);
					return;
				} else {
					session.setAttribute("errText", "Missing parameters");
					response.sendRedirect(reqOrigin);
		            return;
				}
		 */
		
		switch(reqType) {
		case "AJAX_GetCourseSections":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("course_id")
					)) {
				request.setAttribute("reqOrigin", myOrigin);
				request.getRequestDispatcher("GetCourseSections")
						.forward(request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
	            return;
			}
		case "CreateCourse":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("course_id"),
					request.getParameter("course_name"),
					request.getParameter("hours"),
					request.getParameter("dept_id")
					)) {
				request.setAttribute("course", new Course(
						request.getParameter("course_id"),
						request.getParameter("course_name"),
						Integer.parseInt(request.getParameter("hours")),
						Integer.parseInt(request.getParameter("dept_id"))
						));
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		case "AddInstructor":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("instr_id"),
					request.getParameter("section_id")
					)) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		case "DropInstructor":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("instr_id"),
					request.getParameter("section_id")
					)) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		case "AddSection":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("course_id"),
					request.getParameter("section_id"),
					request.getParameter("term"),
					request.getParameter("room"),
					request.getParameter("schedule_id"),
					request.getParameter("capacity"),
					request.getParameter("instr_id")
					)) {
				Section s = new Section(
						Integer.parseInt(request.getParameter("term")),
						request.getParameter("section_id"),
						request.getParameter("course_id"),
						Integer.parseInt(request.getParameter("room")), 
						Integer.parseInt(request.getParameter("schedule_id")), 
						request.getParameter("instr_id"));
				session.setAttribute("section", s);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(jspOrigin);
				return;
			}
		case "RemoveSection":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.emptyStrings(
					request.getParameter("section_id")
					)) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		case "UpdateSection":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("section_id"),
					request.getParameter("course_id"),
					request.getParameter("term"),
					request.getParameter("room"),
					request.getParameter("schedule_id"),
					request.getParameter("instr_id"),
					request.getParameter("capacity")
					)) {
				request.setAttribute("section", getSection(request.getParameter("section_id")));
				if(request.getAttribute("section") == null) {
					session.setAttribute("errText", "Unable to retrieve Section");
					response.sendRedirect(jspOrigin);
					return;
				} else {
					String course_id = request.getParameter("course_id");
					int term = Integer.parseInt(request.getParameter("term"));
					int room = Integer.parseInt(request.getParameter("room"));
					int schedule_id = Integer.parseInt(request.getParameter("schedule_id"));
					String instr_id = request.getParameter("instr_id");
					int capacity = Integer.parseInt(request.getParameter("capacity"));
					Section s = (Section)request.getAttribute("section");
					s.setCourse_id(course_id);
					s.setTerm(term);
					s.setRoom(room);
					s.setSchedule_id(schedule_id);
					s.setInstr_id(instr_id);
					s.setCapacity(capacity);
					
					request.setAttribute("reqOrigin", myOrigin);
					forward(reqType, request, response);
					return;
				}
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		case "UpdateGrades":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.notEmptyStrings(
					request.getParameter("student_id"),
					request.getParameter("section_id"),
					request.getParameter("grade")
					)) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
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
	
	private Section getSection(String section_id) {
		Connection con = null;
		if(section_id == null) {
			Logger.getLogger("InstructorServicesServlet").log(Level.SEVERE, "Invalid parameter: section_id");
			return null;
		}
		try {
			con = AccountBeanHelper.getInstance().getConnection();
		} catch (DbHelperException e) {
			e.printStackTrace();
			Logger.getLogger("InstructerServiceServlet").log(Level.SEVERE, "Unable to get connection to database");
			return null;
		}
		
		try {
			PreparedStatement ps = con.prepareStatement("select * from sections where section_id = ?");
			ps.setString(1, section_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int term = rs.getInt("term");
				String course_id = rs.getString("course_id");
				int room = rs.getInt("room");
				int schedule_id = rs.getInt("schedule_id");
				String instr_id = rs.getString("instr_id");
				return new Section(term, section_id, course_id, room, schedule_id, instr_id);
			}
		} catch(SQLException sqlx) {
			sqlx.printStackTrace();
			Logger.getLogger("InstructorServicesServlet").log(Level.SEVERE, "Unable to retrieve Section information");
		}
		return null;
	}
}
