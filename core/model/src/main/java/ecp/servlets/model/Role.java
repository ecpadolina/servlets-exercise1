package ecp.servlets.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.Cacheable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "roles")
@Immutable
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
public class Role{

  @Id
  @Column(name = "role_id")
  private int roleId;
  @Column(name = "role_type")
  private String roleType;
  @Column(name = "isActive", nullable = false)
  private boolean isActive = true;
  @ManyToMany(mappedBy="roles", fetch=FetchType.EAGER)
  private Set<Person> persons;
  
  public Role(){}
  public Role(int roleId, String roleType, Set<Person> persons, boolean isActive){
    this.roleId = roleId;
    this.roleType = roleType;
    this.persons = persons;
    this.isActive = isActive;
  }  
  
  public void setRoleId(int roleId){
    this.roleId = roleId;
  }
  
  public void setRole_type(String roleType){
    this.roleType = roleType;
  }
  
  public void setPersons(Set<Person> persons){
    this.persons = persons;
  }
  
  public int getRoleId(){
    return roleId;
  }
  
  public String getRoleType(){
    return roleType;
  }
  
  public Set<Person> getPersons(){
    return persons;
  }
  
  public void setIsActive(boolean isActive){
    this.isActive = isActive;
  }

  public boolean getIsActive(){
    return isActive;
  }
  
	@Override	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!this.getClass().equals(obj.getClass())) return false;
		Role obj2 = (Role)obj;
		if(this.roleId == obj2.getRoleId()){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.roleId;
	}
  
}
