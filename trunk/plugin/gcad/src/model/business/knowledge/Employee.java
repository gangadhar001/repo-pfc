package model.business.knowledge;

import java.io.Serializable;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * This class represents an Employee of a Company
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Employee extends User implements Serializable {
	
	private static final long serialVersionUID = -9206473778252947612L;

	public Employee () {
		
	}

	public Employee(String nif, String login, String password, String name, String surname, String email, String telephone, Company c) {
		super(nif, login, password, name, surname, email, telephone, c);
	}
	
	public UserRole getRole(){
		return UserRole.Employee;
	}
	
	public Object clone() {
		Employee c; 
		HashSet<Project> projects = new HashSet<Project>();		
		c = new Employee(getNif(), getLogin(), getPassword(), getName(), getSurname(), getEmail(), getTelephone(), (Company)getCompany().clone());
		c.setId(getId());
		for (Project p: getProjects())
			projects.add((Project)p.clone());
		c.setProjects(projects);
		return c;
	}

}
