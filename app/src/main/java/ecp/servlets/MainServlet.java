import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import ecp.servlets.service.PersonManagerHibernateImpl;

public class MainServlet extends HttpServlet {
	String openHtml;
	String closeHtml;
	Operation op;

	public void init() throws ServletException {
		openHtml = "<!DOCTYPE html> \n<html> <head> <title>Servlets Activity</title>\n"+
				   "<body>";
		closeHtml = "</body></html>";
		op = new Operation();
	}	

	public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String column = "id";
		int order = 1;
		out.println(openHtml); 
		String sort = "<form method=\"GET\">Sort By: <select name=\"column\">"
					+ "<option value=\"id\">ID</option>"
					+ "<option value=\"name.lastName\">Last Name</option>"
					+ "<option value=\"gwa\">GWA</option> </select>"
					+ " Order By: <select name=\"order\">"
					+ "<option value=\"1\">Ascending</option>"
					+ "<option value=\"2\">Descending</option> </select>"
					+ "<input type=\"submit\"></form><br>"
					+ op.listRolesWithPerson();
		out.println(sort);
		if(request.getParameter("column") != null){
			column = request.getParameter("column");
		}
		if(request.getParameter("order") != null){
			order = Integer.parseInt(request.getParameter("order"));
		}
		if(request.getParameter("roles") == null){
			out.println(op.listPersons(order,column));
		} else {
			out.println(op.listPersonWithRoles(Integer.parseInt(request.getParameter("roles"))));
		}
		out.println(closeHtml);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		if(request.getParameter("delete") != null) {
			Integer id = Integer.valueOf(request.getParameter("delete"));
			op.deletePerson(id);
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(openHtml); 
		out.println(closeHtml);
		response.sendRedirect("/Main");
	}
}