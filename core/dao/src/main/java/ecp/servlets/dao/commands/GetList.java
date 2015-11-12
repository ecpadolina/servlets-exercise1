package ecp.servlets.dao.commands;

import ecp.servlets.model.Person;
import ecp.servlets.dao.Command;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import java.util.List;

public class GetList<T> implements Command{
	private Session session;
	private String column;
	private int order;
	private Class<T> entityClass;

	public GetList(int order, String column, Class<T> entityClass){
		this.column = column;
		this.order = order;
		this.entityClass = entityClass;
	}

	public void setSession(Session session){
		this.session = session;
	}

	public Object execute(){
		List list = null;
		if(order == 1){
			list = session.createCriteria(entityClass).addOrder(Order.asc(column)).setCacheable(true).list();
		} else {
			list = session.createCriteria(entityClass).addOrder(Order.desc(column)).setCacheable(true).list();
		}
		return list;
	}
}