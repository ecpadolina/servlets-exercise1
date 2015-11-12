package ecp.servlets.dao.commands;

import org.hibernate.Hibernate;
import ecp.servlets.model.Person;
import org.hibernate.Session;
import ecp.servlets.dao.Command;

public class Get<T> implements Command{
	private Session session;
	private Integer id;
	private Class<T> entityClass;
	private String initCollection;

	public Get(Integer id, Class<T> entityClass, String initCollection){
		this.id = id;
		this.entityClass = entityClass;
		this.initCollection = initCollection;
	}

	public void setSession(Session session){
		this.session = session;
	}

	public Object execute(){
		Object obj = session.get(entityClass, id);
		if(obj instanceof Person){
			Person person = (Person)obj;
			if(initCollection.contains("Contacts"))
				person.getContacts().size();
			if(initCollection.contains("Roles"))
				person.getRoles().size();
			obj = person;
		}
		return obj;
	}
}