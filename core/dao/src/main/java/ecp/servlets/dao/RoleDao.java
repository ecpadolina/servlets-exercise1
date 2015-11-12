package ecp.servlets.dao;

import ecp.servlets.model.Role;
import java.util.List;

public interface RoleDao{
	List listRoles();
	Role getRole(int roleId);
	Boolean updateRole(Role role);
	List listRolesWithPerson(String query);
}