package ecp.servlets.dao.commands;

import ecp.servlets.dao.Command;
import ecp.servlets.model.Person;
import org.hibernate.criterion.Projections;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.Session;
import java.util.List;

public class CustomQuery implements Command{
	private Session session;
	private int parameter;

	public CustomQuery(int parameter){
		this.parameter = parameter;
	}

	public void setSession(Session session){
		this.session = session;
	}

	public Object execute(){
		Criteria crit = session.createCriteria(Person.class);
		ProjectionList properties = Projections.projectionList();
		crit.add(Restrictions.eq("roles.role_id", parameter));
		crit.createAlias("roles","roles");
		crit.createAlias("contacts","contacts", JoinType.FULL_JOIN);
		properties.add(Projections.property("id"));
		properties.add(Projections.property("name.firstName"));
		properties.add(Projections.property("name.lastName"));
		properties.add(Projections.property("birthday"));
		properties.add(Projections.property("gwa"));
		properties.add(Projections.property("contacts.contactInfo"));
		crit.setProjection(properties);
		return crit.list();
	}
}