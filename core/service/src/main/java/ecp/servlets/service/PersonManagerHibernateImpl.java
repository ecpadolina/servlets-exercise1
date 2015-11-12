package ecp.servlets.service;

import ecp.servlets.dao.PersonDaoHibernateImpl;
import ecp.servlets.dao.PersonDao;
import ecp.servlets.model.Person;

import java.util.List;

public class PersonManagerHibernateImpl implements PersonManager{

  private PersonDao pdao;
  
  public PersonManagerHibernateImpl(){
    pdao = new PersonDaoHibernateImpl();
  }
  
  public void addPerson(Person person){
    if(pdao.addPerson(person))
      System.out.println("Person added!");
    else
      System.out.println("Person wasn't added!");
  }
  
  public void updatePerson(Person person){
    if(pdao.updatePerson(person))
      System.out.println("Person updated!");
    else
      System.out.println("Person wasn't updated!");
  }
  
  public void deletePerson(Person person){
    if((pdao.deletePerson(person)).booleanValue())
      System.out.println("Person deleted!");
    else
      System.out.println("Person wasn't deleted");
  }
  
  public Person getPerson(int id, String initCollection){
    return pdao.getPerson(id, initCollection);
  }
  
  public List listPerson(int order, String column){
    return pdao.listPerson(order,column);
  }
  public List listPersonWithRoles(int roleId){
    return pdao.listPersonWithRoles(roleId);
  }
}