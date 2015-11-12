package ecp.servlets.service;

import ecp.servlets.model.Person;
import java.util.List;

public interface PersonManager{
	void addPerson(Person person);
	void updatePerson(Person person);
	void deletePerson(Person person);
	Person getPerson(int personId, String initCollection);
	List listPerson(int order, String column);
	List listPersonWithRoles(int roleId);
}