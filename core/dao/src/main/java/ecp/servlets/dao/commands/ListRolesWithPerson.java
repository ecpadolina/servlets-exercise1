package ecp.servlets.dao.commands;

import ecp.servlets.dao.Command;
import org.hibernate.Session;
import java.util.List;

public class ListRolesWithPerson implements Command{
	private Session session;
	private String query;

	public ListRolesWithPerson(String query){
		this.query = query;
	}

	public void setSession(Session session){
		this.session = session;
	}

	public Object execute(){
		List<Object> list = session.createQuery(query).list();
		return list;
	}
}