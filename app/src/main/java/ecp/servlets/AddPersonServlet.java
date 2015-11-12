import java.util.*;
import ecp.servlets.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import java.text.SimpleDateFormat;

public class AddPersonServlet extends HttpServlet{
	String openHtml;
	String closeHtml;
	Operation op;	
	Person person;
	Name name;
	Address address;
	ContactInfo contact;
	Role role;
	String addPersonHtml;

	public void init() throws ServletException{
		op = new Operation();
		name = new Name();
		address = new Address();
		contact = new ContactInfo();
		role = new Role();
		person = new Person();
		openHtml = "<!DOCTYPE html> \n<html> <head> <title>Servlets Activity - Add Person</title>\n"+
				   "<body><h3><a href=\"/Main\">Home</a></h3>";
		closeHtml = "</body></html>";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		FileInputStream fis = new FileInputStream("src/main/webapp/AddPerson.html");
		addPersonHtml = IOUtils.toString(fis, "UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(openHtml);	
		out.println(addPersonHtml);
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

		Set<ContactInfo> contacts = new HashSet<ContactInfo>();
		contact.setContactType(request.getParameter("contactType"));
		contact.setContactInfo(request.getParameter("contact"));
		contacts.add(contact);
		person.setContacts(contacts);

		String[] requestRoles = request.getParameterValues("role");
		Set<Role> roles = new HashSet<Role>();
		for(String tempRole : requestRoles){
			int id = Integer.parseInt(tempRole);
			Role role = op.getRole(id);
			roles.add(role);
		}
		person.setRoles(roles);
		op.addPerson(person);
		response.sendRedirect("/Main");
	}
}