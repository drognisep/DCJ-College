package servlet.ajax;

import inval.object.ObjValidator;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.account.AccountBeanHelper;
import data.util.DbHelperException;
import data.util.Section;

/**
 * Servlet implementation class GetCourseSections
 */
@WebServlet("/GetCourseSections")
public class GetCourseSections extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCourseSections() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		if(ObjValidator.emptyStrings(request.getParameter("course_id"))) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Missing argument");
			return;
		}
		
		String course_id = request.getParameter("course_id");
		if(course_id.equals("---")) {
			// Silently ignore this
			response.sendRedirect(request.getParameter("reqOrigin"));
			return;
		}
		List<Section> sections = null;
		StringBuilder sb = new StringBuilder();
		try {
			sections = AccountBeanHelper.getInstance().getCourseSections(course_id);
//			sb.append("\"response\":[");
			sb.append("[{\"section_id\":\"---\"}");
			
			for(Section s : sections) {
				sb.append(", {\"section_id\":\"").append(s.getSection_id()).append("\"}");
			}
			sb.append("]");
			response.getWriter().print(sb.toString());
		} catch (DbHelperException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
					"Unable to get course sections");
			return;
		}
	}

}
