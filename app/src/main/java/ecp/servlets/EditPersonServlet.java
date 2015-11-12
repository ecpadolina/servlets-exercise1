import java.util.*;
import ecp.servlets.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import java.text.SimpleDateFormat;

public class EditPersonServlet extends HttpServlet{
	String openHtml;
	String closeHtml;
	Operation op;	
	int id;
	Person person;
	Name name;
	Address address;
	ContactInfo contacts;
	Set<ContactInfo> oldContacts;

	public void init() throws ServletException{
		op = new Operation();
		name = new Name();
		address = new Address();
		openHtml = "<!DOCTYPE html> \n<html> <head> <title>Servlets Activity - Edit Person</title>\n"+
				   "<body><h3><a href=\"/Main\">Home</a></h3><h1>Edit Person</h1><br>";
		closeHtml = "</body></html>";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String id = request.getParameter("edit");
		person = op.getPerson(Integer.parseInt(id));
		String employmentStatus = person.getEmploymentStatus();
		oldContacts =  person.getContacts();
		String form = "<form method=\"POST\">"
		+ "<br><br>"
		+ "First Name: <input id=\"firstName\" value=\"" + person.getName().getFirstName() + "\" type=\"text\" name=\"firstName\"><br> "
		+ "Last Name: <input id=\"lastName\" value=\"" + person.getName().getLastName() + "\" type=\"text\" name=\"lastName\"><br> "
		+ "Middle Name: <input id=\"middleName\" value=\"" + person.getName().getMiddleName() + "\" type=\"text\" name=\"middleName\"> <br>"
		+ "Birthday: <input id=\"birthday\" value=\"" + person.getBirthday() + "\" type=\"date\" name=\"birthday\"> <br>"
		+ "House No: <input id=\"houseNo\" value=\"" + person.getAddress().getHouseNo() + "\" type=\"text\" name=\"houseNo\"><br> "
		+ "Street: <input id=\"street\" value=\"" + person.getAddress().getStreet() + "\" type=\"text\" name=\"street\"> <br>"
		+ "Barangay: <input id=\"barangay\" value=\"" + person.getAddress().getBarangay() + "\" type=\"text\" name=\"barangay\"> <br>"
		+ "Municipality: <input id=\"municipality\" value=\"" + person.getAddress().getMunicipality() + "\" type=\"text\" name=\"municipality\"> <br>"
		+ "Province: <input id=\"province\" value=\"" + person.getAddress().getProvince() + "\" type=\"text\" name=\"province\"> <br>"
		+ "Zipcode: <input id=\"zipcode\" value=\"" + person.getAddress().getZipcode() + "\" type=\"number\" name=\"zipcode\"> <br>"
		+ "GWA: <input id=\"gwa\" value=\"" + person.getGwa() + "\" type=\"number\" step=\"0.01\" min=\"1\" max=\"5\" name=\"gwa\"> <br>"
		+ "<br> Employment Status <br>"
		+ op.listEmploymentStatusForm(employmentStatus) + "<br>"
		+ "<br> Roles: " + op.listRoleForm(person.getRoles()) 
		+ "<br><input type=\"submit\">";


		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(openHtml);	
		out.println(form);
		out.println(closeHtml);		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		name.setFirstName(request.getParameter("firstName"));
		name.setLastName(request.getParameter("lastName"));
		name.setMiddleName(request.getParameter("middleName"));
		person.setName(name);
		address.setHouseNo(request.getParameter("houseNo"));
		address.setStreet(request.getParameter("street"));
		address.setBarangay(request.getParameter("barangay"));
		address.setSubdivision(request.getParameter("subdivision"));
		address.setMunicipality(request.getParameter("municipality"));
		address.setProvince(request.getParameter("province"));
		address.setZipcode(Integer.parseInt(request.getParameter("zipcode")));
		person.setAddress(address);

		person.setEmploymentStatus(request.getParameter("employmentStatus"));
		person.setGwa(Float.parseFloat(request.getParameter("gwa")));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date bday = new Date();
		String tmpBday = request.getParameter("birthday");
		tmpBday = tmpBday.replace("-","/");
		try {
		bday = formatter.parse(tmpBday);
		} catch(Exception e){
			e.printStackTrace();
		}
		person.setBirthday(bday);

		String[] requestRoles = request.getParameterValues("role");
		Set<Role> roles = new HashSet<Role>();
		for(String tempRole : requestRoles){
			int id = Integer.parseInt(tempRole);
			Role role = op.getRole(id);
			roles.add(role);
		}
		person.setRoles(roles);
		op.updatePerson(person);
		response.sendRedirect("/Main");
	}	
}