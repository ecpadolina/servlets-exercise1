package ecp.servlets.dao.commands;

import ecp.servlets.dao.Command;
import ecp.servlets.model.Person;
import ecp.servlets.model.PersonModel;
import java.util.List;
import org.hibernate.criterion.Projections;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
		crit.add(Restrictions.eq("roles.roleId", parameter));
		crit.createAlias("roles","roles");
		crit.createAlias("contacts","contacts", JoinType.FULL_JOIN);
		properties.add(Projections.property("id"), "id");
		properties.add(Projections.property("name.firstName"), "firstName");
		properties.add(Projections.property("name.lastName"), "lastName");
		properties.add(Projections.property("birthday"), "birthday");
		properties.add(Projections.property("gwa"), "gwa");
		properties.add(Projections.property("contacts.contactInfo"), "contactInfo");
		crit.setProjection(properties);
		crit.setResultTransformer(Transformers.aliasToBean(PersonModel.class));
		return crit.list();
	}
}