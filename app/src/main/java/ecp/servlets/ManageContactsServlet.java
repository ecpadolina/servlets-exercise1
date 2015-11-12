import java.util.*;
import ecp.servlets.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import java.text.SimpleDateFormat;

public class ManageContactsServlet extends HttpServlet{
	Person person;
	ContactInfo contact;
	Operation op;
	String openHtml;
	String closeHtml;
	Set<ContactInfo> contacts;

	public void init() throws ServletException{
		op = new Operation();
		person = new Person();
		openHtml = "<!DOCTYPE html> \n<html> <head> <title>Servlets Activity - Manage Contacts</title>\n"+
				   "<body><h3><a href=\"/Main\">Home</a></h3><h1>Manage Contacts</h1><br>";
		closeHtml = "</body></html>";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		person = op.getPerson(Integer.parseInt(request.getParameter("manage")));
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		contacts = person.getContacts();
		String addContactButton = "<form method=\"GET\" action=\"/ManageContacts/AddContact\"><button name=\"add\" type=\"submit\" "
				 + "value=\"" + person.getId() + "\">Add Contact</button></form>";
		String form = "<h2>" + person.getName().toString() + "'s Contacts </h2> <br><table><tr><th>Contact Type</th><th>Contact Detail</th><th>Actions</th></tr>";
		for(ContactInfo contact : contacts){
			form = form + "<tr><td><form method=\"POST\"><input type=\"hidden\" name=\"contactId\" value=\"" + contact.getId() + "\">" 
				 + "<input type=\"hidden\" name=\"contactType\" value=\"" + contact.getContactType() + "\">" 
				 + "" + contact.getContactType() + "</td><td><input type=\"text\" name=\"contact\" value=\"" + contact.getContactInfo() + "\"></td>"
				 + "<td><button type=\"submit\">Edit</button></form> <form method=\"POST\" action=\"/ManageContacts\"><button name=\"delete\" type=\"submit\" "
				 + "value=\"" + contact.getId() + "\">Delete</button></form></td></tr>";
		}

		form = form + "</table> <br>" + addContactButton;
		out.println(openHtml);
		out.println(form);
		out.println(closeHtml);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(request.getParameter("delete") != null){
			int id = Integer.parseInt(request.getParameter("delete"));
			for(ContactInfo contact : contacts){
				if(id == contact.getId()){
					contacts.remove(contact);
					break;
				}
			}
		person.setContacts(contacts);
		op.updatePerson(person);
		response.sendRedirect("/ManageContacts?manage=" + person.getId());
		}
		
		if(request.getParameter("contactId") != null){
			int id = Integer.parseInt(request.getParameter("contactId"));
			ContactInfo newContact = new ContactInfo();
			ContactInfo oldContact = new ContactInfo();
			for(ContactInfo contact : contacts){
				if(contact.getId() == id){
					newContact = contact;
					oldContact = contact;
				}
			}
			newContact.setContactInfo(request.getParameter("contact"));
			contacts.remove(oldContact);
			contacts.add(newContact);
			person.setContacts(contacts);
			op.updatePerson(person);
			response.sendRedirect("/Main");
		}
	}
}