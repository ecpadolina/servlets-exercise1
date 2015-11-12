package ecp.servlets.dao.commands;

import ecp.servlets.model.Person;
import ecp.servlets.dao.Command;
import org.hibernate.Session;

public class Update<T> implements Command{
	private Session session;
	private T t;

	public Update(T t){
		this.t = t;
	}

	public void setSession(Session session){
		this.session = session;
	}

	public Object execute(){
		session.update(t);
		return new Boolean(true);
	}
}