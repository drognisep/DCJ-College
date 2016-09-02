package servlet.ajax;

import inval.object.ObjValidator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

/**
 * Servlet implementation class RegistrationUpdate
 */
@WebServlet("/ajaxRegistrationUpdate")
public class RegistrationUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger("RegistrationUpdate");
	
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
		Connection con = null;
		boolean autocom = true;
		
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
		
		try {
			con = AccountBeanHelper.getInstance().getConnection();
		} catch (DbHelperException dbhx) {
			dbhx.printStackTrace();
			log.log(Level.SEVERE, "Error occurred connecting to database");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
					"Error occurred connecting to database");
			return;
		}
		
		if(account.getId().charAt(0) != 's') {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Cannot perform this operation as an instructor");
			return;
		}
		
		try {
			autocom = con.getAutoCommit();
			con.setAutoCommit(false);
			String query = "UPDATE students set "
						 	+ "first_name = ?, "
						 	+ "last_name = ?, "
						 	+ "street = ?, "
						 	+ "city = ?, "
						 	+ "state = ?, "
						 	+ "zip = ?, "
						 	+ "phone = ? "
						 + "where student_id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, street);
			ps.setString(4, city);
			ps.setString(5, state);
			ps.setString(6, zip);
			ps.setString(7, phone);
			ps.setString(8, account.getId());
			
			int updates = ps.executeUpdate();
			if(updates != 1) {
				log.log(Level.SEVERE, "rows updated = " + updates);
				con.rollback();
				con.setAutoCommit(autocom);
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
						"Unable to update registration");
				return;
			}
			con.commit();
			con.setAutoCommit(autocom);
		} catch (SQLException sqlx) {
			try {
				con.rollback();
				con.setAutoCommit(autocom);
			} catch (Exception any) { /* ignore */ }
			sqlx.printStackTrace();
			log.log(Level.SEVERE, "Error occurred updating registration");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
					"Error occurred updating registration");
			return;
		}
		
		session.setAttribute("infoText", "Success!");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print("Success!");
	}
}
