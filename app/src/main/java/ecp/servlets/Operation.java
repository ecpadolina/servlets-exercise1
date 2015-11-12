import java.util.*;
import ecp.servlets.model.*;
import ecp.servlets.service.PersonManagerHibernateImpl;
import ecp.servlets.service.RoleManagerHibernateImpl;

public class Operation{
	PersonManagerHibernateImpl pm;
	RoleManagerHibernateImpl rm;

	public Operation(){
		pm = new PersonManagerHibernateImpl();
		rm = new RoleManagerHibernateImpl();
	}

	public void deletePerson(Integer id){
		Person person = pm.getPerson(id, "");
		person.setRoles(null);
		pm.deletePerson(person);
	}

	public Role getRole(int id){
		return rm.getRole(id);
	}

	public Person getPerson(int id){
		return pm.getPerson(id, "");
	}

	public void addPerson(Person person){
		pm.addPerson(person);
	}

	public void updatePerson(Person person){
		pm.updatePerson(person);
	}

	public String listRolesWithPerson(){
		String form = "<form method=\"GET\">List Person By Roles: <select name=\"roles\">";
		List<Object[]> tmpRoles = rm.listRolesWithPerson("select proles.id, proles.role_type from Person person INNER JOIN person.roles as proles");
		Map<Integer, String> roles = new HashMap<Integer,String>();
		for(Object[] tmpRole : tmpRoles){
			roles.put((Integer)tmpRole[0], (String)tmpRole[1]);
		}
		for(Map.Entry<Integer,String> entry : roles.entrySet()){
			form = form + "<option value=\"" + (Integer)entry.getKey() + "\">" + (String)entry.getValue() + "</option>";
		}
		form = form + " <input type=\"submit\"></form><br>";
		return form;
	}

	public String listEmploymentStatusForm(String employmentStatus){
		String temp = "";
		if(employmentStatus.equals("Intern")){
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Intern\" checked=\"checked\">Intern<br>";
		} else {
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Intern\">Intern<br>";
		}

		if(employmentStatus.equals("Probationary")){
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Probationary\" checked=\"checked\">Probationary<br>";
		} else {
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Probationary\">Probationary<br>";
		}

		if(employmentStatus.equals("Regular")){
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Regular\" checked=\"checked\">Regular<br>";
		}else {
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Regular\">Regular<br>";
		}

		if(employmentStatus.equals("Resigned")){
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Resigned\" checked=\"checked\">Resigned<br>";
		}else {
			temp = temp + "<input type=\"radio\" name=\"employmentStatus\" value=\"Resigned\">Resigned<br>";
		}
		return temp;
	}

	public String listRoleForm(Set<Role> roles){
		String temp = "";
		List<String> tmpList = new ArrayList<String>();
		for(Role role : roles){
			tmpList.add(role.getRole_type());
		}
		if(tmpList.contains("CEO")){
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"1\" checked>CEO";
		} else {
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"1\">CEO";
		}

		if(tmpList.contains("CFO")){
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"2\" checked>CFO";
		} else {
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"2\">CFO";
		}

		if(tmpList.contains("COO")){
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"3\" checked>COO";
		} else {
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"3\">COO";
		}

		if(tmpList.contains("Admin")){
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"4\" checked>Admin";
		} else {
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"4\">Admin";
		}

		if(tmpList.contains("Manager")){
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"5\" checked>Manager";
		} else {
			temp = temp + "<input type=\"checkbox\" name=\"role\" value=\"5\">Manager";
		}
		return temp;
	}

	public String listPersonWithRoles(int roleId){
		List<Object[]> list = pm.listPersonWithRoles(roleId);
		String output = "<table><tr><th>ID</th>"
					  + "<th>First Name</th>"
					  + "<th>Last Name</th>"
					  + "<th>Birthday</th>"
					  + "<th>Grade</th>"
					  + "<th>Contacts</th>"
					  + "<th>Actions</th>";

		if(!list.isEmpty()){
			LinkedHashMap<Integer, Person> map = new LinkedHashMap<Integer,Person>();
			for(Object[] obj : list){
				if(map.get(obj[0]) == null){
					Person person = new Person();
					Set<ContactInfo> contacts = new HashSet<ContactInfo>();
					ContactInfo contact = new ContactInfo();
					Name name = new Name();
					person.setId((Integer)obj[0]);
					name.setFirstName((String)obj[1]);
					name.setLastName((String)obj[2]);
					person.setName(name);
					person.setBirthday((Date)obj[3]);
					person.setGwa((Float)obj[4]);
					if(obj[5] != null){
						contact.setContactInfo((String)obj[5]);
						contacts.add(contact);
						person.setContacts(contacts);
					}
					map.put((Integer)obj[0], person);
				} else {
					Person person = map.get((Integer)obj[0]);
					Set<ContactInfo> contacts = person.getContacts();
					ContactInfo contact = new ContactInfo();
					contact.setContactInfo((String)obj[5]);
					contacts.add(contact);
					person.setContacts(contacts);
				}
			}
			for(Map.Entry<Integer,Person> entry : map.entrySet()){
				Person person = (Person)entry.getValue();
				Set<ContactInfo> contacts = person.getContacts();
				String numbers = "";
				if(contacts != null){
					for(ContactInfo contact : contacts){
						numbers += contact.getContactInfo()  + " ";
					}
				}
			output = output + "<tr><td>" + person.getId() + "</td><td> " + person.getName().getFirstName()
				   + "</td><td>" + person.getName().getLastName() + "</td><td>" + person.getBirthday().toString()
				   + "</td><td>" + person.getGwa() + "</td><td>" + numbers + "</td>"
				   + "<td><form method=\"POST\" action=\"/Main\"><button name=\"delete\" type=\"submit\" "
			       + "value=\""+ person.getId() + "\">Delete</button></form>"
			       + "<form method=\"GET\" action=\"/EditPerson\"><button name=\"edit\" type=\"submit\" "
			       + "value=\"" + person.getId() +"\">Edit</button></form>"
			       + "<form method=\"GET\" action=\"/ManageContacts\"><button name=\"manage\" type=\"submit\" "
				   + "value=\"" + person.getId() + "\">Manage Contacts</button></form></td></tr>";
			}
		}
		output += "</table>";
		return output;
	}

	public String listPersons(int order, String column){
		List list = pm.listPerson(order,column);
		String output = "<table id=\"maintable\">"
	            +"<tr>"
	            +"<th>ID</th>"
	            +"<th>First Name</th>"
	            +"<th>Last Name</th>"
	            +"<th>Birthday</th>"
	            +"<th>Grade</th>"
	            +"<th>Contact Number</th>"
	            +"<th>Roles</th>"
	            +"<th>Actions</th>"
	            +"</tr>";
	    String newPersonButton = "<br><button onClick=\"location.href='AddPerson'\">Add Person</button>";
		if(!list.isEmpty()){
			LinkedHashMap<Integer, Person> map = new LinkedHashMap<Integer, Person>();
			
	         for(Iterator ir = list.iterator(); ir.hasNext();){
			      Person person = (Person)ir.next();
			      map.put(person.getId(), person);
			  }
			  for(Map.Entry<Integer, Person> entry : map.entrySet()){
			  	  Person person = entry.getValue();
			      Set<ContactInfo> contacts = person.getContacts();
			      Set<Role> roles = person.getRoles();
			      String contactString = "";
			      String roleString = "";
			      String deleteButton = "<form method=\"POST\" action=\"/Main\"><button name=\"delete\" type=\"submit\" "
			      					+ "value=\""+ person.getId() + "\">Delete</button></form>";
			      String editButton = "<form method=\"GET\" action=\"/EditPerson\"><button name=\"edit\" type=\"submit\" "
			      					+ "value=\"" + person.getId() +"\">Edit</button></form>";
				  String manageContactButton = "<form method=\"GET\" action=\"/ManageContacts\"><button name=\"manage\" type=\"submit\" "
									+ "value=\"" + person.getId() + "\">Manage Contacts</button></form>";
			      for(ContactInfo contact : contacts){
			      	contactString = contactString + contact.getContactInfo() + " ";
			      }
			      for(Role role : roles){
			      	roleString = roleString + role.getRole_type() + " ";
			      }

			      output = output + "<tr><td>" + person.getId() + "</td><td>" + person.getName().getFirstName()
			      + "</td><td>" + person.getName().getLastName() + "</td><td>" + person.getBirthday().toString()
			      + "</td><td>" + person.getGwa() + "</td><td>" + contactString + "</td><td>" + roleString + "</td><td>"
			      + deleteButton + editButton + manageContactButton + "</td></tr>";
			  }
		}
		output = output + "</table>" + newPersonButton;
        return output;
	}
}