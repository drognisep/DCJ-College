package servlet.worker;

import java.io.IOException;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetFeesOwedServlet
 */
@WebServlet("/GetFeesOwedServlet")
public class GetFeesOwedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> names = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		response.setContentType("text/html");
		sb.append("<h1>Request parameter listing</h1>");
		sb.append("<ul>");
		while(names.hasMoreElements()) {
			String cur = names.nextElement();
			sb.append("<li>").append(cur).append(" in request equals: ")
				.append(request.getParameter(cur));
			sb.append("</li>");
		}
		sb.append("</ul>");
		sb.append("<h1>All reqOrigin parameters</h1>\n<ul>");
		for(String s : request.getParameterValues("reqOrigin")) {
			sb.append("<li>" + s + "</li>");
		}
		sb.append("<li>Current reqOrigin: " + request.getAttribute("reqOrigin") + "</li></ul>");
		
		sb.append("<h1>Attribute names found:</h1><ul>");
		Enumeration<String> attNames = request.getAttributeNames();
		while(attNames.hasMoreElements()) {
			sb.append("<li>").append(attNames.nextElement()).append("</li>");
		}
		sb.append("</ul>");
		
		Logger.getLogger("AddCourseServlet").info("In working servlet for: " + request.getParameter("reqType") + "\n" + sb.toString());
		response.getWriter().print(sb.toString());
	}

}
