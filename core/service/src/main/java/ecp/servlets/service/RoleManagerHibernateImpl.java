package ecp.servlets.service;

import ecp.servlets.dao.RoleDaoHibernateImpl;
import ecp.servlets.dao.RoleDao;
import ecp.servlets.model.Role;

import java.util.List;

public class RoleManagerHibernateImpl implements RoleManager{

  private RoleDao rdao;
  
  public RoleManagerHibernateImpl(){
    rdao = new RoleDaoHibernateImpl();
  }

  public List listRoles(){
    return rdao.listRoles();
  }

  public Role getRole(int roleId){
    return rdao.getRole(roleId);
  }
  public void updateRole(Role role){
    rdao.updateRole(role);
    System.out.println("Role Updated!");
  }
  public List listRolesWithPerson(String query){
    return rdao.listRolesWithPerson(query);
  }
}