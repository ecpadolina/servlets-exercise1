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
  private int role_id;
  @Column(name = "role_type")
  private String role_type;
  @Column(name = "isActive", nullable = false)
  private boolean isActive = true;
  @ManyToMany(mappedBy="roles", fetch=FetchType.EAGER)
  private Set<Person> persons;
  
  public Role(){}
  public Role(int role_id, String role_type, Set<Person> persons, boolean isActive){
    this.role_id = role_id;
    this.role_type = role_type;
    this.persons = persons;
    this.isActive = isActive;
  }  
  
  public void setRole_id(int role_id){
    this.role_id = role_id;
  }
  
  public void setRole_type(String role_type){
    this.role_type = role_type;
  }
  
  public void setPersons(Set<Person> persons){
    this.persons = persons;
  }
  
  public int getRole_id(){
    return role_id;
  }
  
  public String getRole_type(){
    return role_type;
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
		if(this.role_id == obj2.getRole_id()){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.role_id;
	}
  
}
