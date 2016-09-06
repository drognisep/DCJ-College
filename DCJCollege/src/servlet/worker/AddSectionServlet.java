package servlet.worker;

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
import data.util.DbHelperException;
import data.util.Section;

/**
 * Servlet implementation class AddSectionServlet
 */
@WebServlet("/AddSectionServlet")
public class AddSectionServlet extends HttpServlet {
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

		// Same for all working servlets in this group
		if (!reqOrigin.equals("InstructorServicesServlet")
				|| !reqType.equals("AddSection")) {
			session.setAttribute("errText", "Invalid request");
			response.sendRedirect("InstructorServicesServlet");
			return;
		}

		AccountBean account = (AccountBean) session.getAttribute("account");

		Section section = (Section) session.getAttribute("section");

		if (ObjValidator.emptyStrings(reqOrigin, reqType)) {
			session.setAttribute("errText", "Missing request metadata");
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		}
		if (ObjValidator.anyNull(account, section)) {
			response.sendRedirect("index.jsp");
			return;
		}
		try {
			if(sectionIdExists(section.getSection_id())) {
				session.setAttribute("errText", "Section ID already exists!");
				response.sendRedirect("InstructorFunctions.jsp");
				return;
			}
		} catch (DbHelperException e) {
			e.printStackTrace();
			Logger.getLogger("AddSectionServlet").log(Level.SEVERE, e.getMessage());
			session.setAttribute("errText", e.getMessage());
			response.sendRedirect("InstructorFunctions.jsp");
			return;
		}
		session.setAttribute("errText", "");
		session.setAttribute("infoText", "");

		AccountBeanHelper instance = AccountBeanHelper.getInstance();
		boolean i = instance.addSection(account, section);
		if (i) {
			session.setAttribute("infoText", "Section successfully added");
			response.sendRedirect("InstructorFunctions.jsp");
		} else {
			session.setAttribute("errText", "Section could not be added.");
			response.sendRedirect("InstructorFunctions.jsp");
		}
	}
	
	private boolean sectionIdExists(String section_id) throws DbHelperException {
		if(section_id == null) throw new DbHelperException("Null section_id parameter");
		AccountBeanHelper helper = AccountBeanHelper.getInstance();
		Connection con = helper.getConnection();
		PreparedStatement ps = null;
		boolean result = false;
		
		if(con == null) return true;
		
		String query = "SELECT section_id FROM sections WHERE section_id = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, section_id);
			ResultSet rs = ps.executeQuery();
			result = rs.next();
		} catch(SQLException sqlx) {
			sqlx.printStackTrace();
			throw new DbHelperException("SQL error occurred");
		} finally {
			try {
				ps.close();
			} catch(Exception any) { /* ignore */ }
		}
		
		return result;
	}
}
