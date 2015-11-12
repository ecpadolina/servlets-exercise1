import java.util.*;
import ecp.servlets.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import java.text.SimpleDateFormat;

public class AddContactServlet extends HttpServlet{
	Person person;
	ContactInfo contact;
	Operation op;
	String openHtml;
	String closeHtml;
	Set<ContactInfo> contacts;

	public void init() throws ServletException{
		op = new Operation();
		openHtml = "<!DOCTYPE html> \n<html> <head> <title>Servlets Activity - Add Contact</title>\n"+
				   "<body><h3><a href=\"/Main\">Home</a></h3><h1>Add Contact</h1><br>";
		closeHtml = "</body></html>";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		contact = new ContactInfo();

		person = op.getPerson(Integer.parseInt(request.getParameter("add")));
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String form = "<br><h2>Adding contact to " + person.getName().toString() + "</h2><br> <form method=\"POST\"> <select name=\"contactType\"> "
					+ "<option value=\"landline\">Landline</option>"
					+ "<option value=\"mobile\">Mobile</option>"
					+ "<option value=\"email\">Email</option>"
					+ "</select> <input type=\"text\" name=\"contact\"/>"
					+ " <input type=\"submit\"></form>";
		out.println(openHtml);
		out.println(form);
		out.println(closeHtml);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		contacts = person.getContacts();
		contact.setContactType(request.getParameter("contactType"));
		contact.setContactInfo(request.getParameter("contact"));
		contacts.add(contact);
		person.setContacts(contacts);
		op.updatePerson(person);
		response.sendRedirect("/ManageContacts?manage="+person.getId());
	}
}