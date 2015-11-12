package ecp.servlets.dao;

import ecp.servlets.model.Person;
import java.util.List;

public interface PersonDao{
	Boolean addPerson(Person person);
	Person getPerson(int personID, String initCollection);
	Boolean updatePerson(Person updatedPerson);
	Boolean deletePerson(Person person);
	List listPerson(int order, String column);
	List listPersonWithRoles(int roleId);
}