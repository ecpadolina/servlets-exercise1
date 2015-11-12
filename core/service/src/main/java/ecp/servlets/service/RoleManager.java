package ecp.servlets.service;

import ecp.servlets.model.Role;
import java.util.List;

public interface RoleManager{
	List listRoles();
	Role getRole(int roleId);
	void updateRole(Role role);
	List listRolesWithPerson(String query);
}