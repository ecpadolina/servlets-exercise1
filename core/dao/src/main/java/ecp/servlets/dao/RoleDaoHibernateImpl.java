package ecp.servlets.dao;

import ecp.servlets.dao.commands.Get;
import ecp.servlets.dao.commands.GetList;
import ecp.servlets.dao.commands.Update;
import ecp.servlets.dao.commands.ListRolesWithPerson;
import ecp.servlets.model.Role;
import java.util.List;

public class RoleDaoHibernateImpl implements RoleDao{

	public List listRoles() {
        return HibernateUtil.perform(new GetList(1, "role_id", Role.class), List.class);
    }
    public Role getRole(int roleId) {
        return HibernateUtil.perform(new Get(roleId, Role.class, ""), Role.class);
    }
    public Boolean updateRole(Role role){
    	return HibernateUtil.perform(new Update(role), Boolean.class);
    }
    public List listRolesWithPerson(String query){
    	return HibernateUtil.perform(new ListRolesWithPerson(query), List.class);
    }
}