package servlet;

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
 * Servlet implementation class InstructorServicesServlet
 */
@WebServlet("/InstructorServicesServlet")
public class InstructorServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		String reqOrigin = request.getParameter("reqOrigin");
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
		
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		
//		Branching origin check
//			if(reqOrigin.equals(jspOrigin)) {
//				
//			} else if(reqOrigin.equals(reqType + "Servlet")) {
//				
//			} else {
//				session.setAttribute("errText", "Invalid request origin");
//				response.sendRedirect(jspOrigin);
//			}
		
//		Handler w/ Objects
//			case "CreateCourses":
//				if(!reqOrigin.equals(jspOrigin)) {
//					session.setAttribute("errText", "Invalid request origin");
//					response.sendRedirect(jspOrigin);
//				}
//				if(ObjValidator.noneNull(request.getAttribute("course"))) {
//					request.setAttribute("reqOrigin", myOrigin);
//					forward(reqType, request, response);
//					return;
//				} else {
//					session.setAttribute("errText", "Missing parameters");
//					response.sendRedirect(reqOrigin);
//                  return;
//				}
		
//		Handler w/ Strings
//			case "AddInstructor":
//				if(!reqOrigin.equals(jspOrigin)) {
//					session.setAttribute("errText", "Invalid request origin");
//					response.sendRedirect(jspOrigin);
//					return;
//				}
//				if(ObjValidator.notEmptyStrings(
//						request.getParameter("instr_id"),
//						request.getParameter("section_id")
//						)) {
//					request.setAttribute("reqOrigin", myOrigin);
//					forward(reqType, request, response);
//					return;
//				} else {
//					session.setAttribute("errText", "Missing parameters");
//					response.sendRedirect(reqOrigin);
//		            return;
//				}
		
		switch(reqType) {
		case "CreateCourse":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
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
			if(ObjValidator.noneNull(request.getAttribute("section"))) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
			} else {
				session.setAttribute("errText", "Missing parameters");
				response.sendRedirect(reqOrigin);
				return;
			}
		case "RemoveSection":
			if(!reqOrigin.equals(jspOrigin)) {
				session.setAttribute("errText", "Invalid request origin");
				response.sendRedirect(jspOrigin);
				return;
			}
			if(ObjValidator.noneNull(request.getAttribute("section"))) {
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
			if(ObjValidator.noneNull(request.getAttribute("section"))) {
				request.setAttribute("reqOrigin", myOrigin);
				forward(reqType, request, response);
				return;
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
}
