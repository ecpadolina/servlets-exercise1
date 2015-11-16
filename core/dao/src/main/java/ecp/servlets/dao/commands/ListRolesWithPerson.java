package ecp.servlets.dao.commands;

import ecp.servlets.dao.Command;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import ecp.servlets.model.Role;
import ecp.servlets.model.Role;
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
		List<Object> list = session.createQuery(query).setCacheable(true).setResultTransformer(Transformers.aliasToBean(Role.class)).list();
		return list;
	}
}