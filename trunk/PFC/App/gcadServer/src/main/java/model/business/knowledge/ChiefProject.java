package model.business.knowledge;

import java.io.Serializable;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * This class represents a Chief Project
 */

@XmlAccessorType( XmlAccessType.FIELD )
public class ChiefProject extends User implements Serializable {
	
	private static final long serialVersionUID = 2298009369141533960L;

	public ChiefProject () {
		
	}

	public ChiefProject(String nif, String login, String password, String name, String surname, String email, String telephone, Company c) {
		super(nif, login, password, name, surname, email, telephone, c);
	}
	
	public UserRole getRole(){
		return UserRole.ChiefProject;
	}

	public Object clone() {
		ChiefProject c; 
		HashSet<Project> projects = new HashSet<Project>();		
		c = new ChiefProject(getNif(), getLogin(), getPassword(), getName(), getSurname(), getEmail(), getTelephone(), (Company)getCompany().clone());
		c.setId(getId());
		for (Project p: getProjects())
			projects.add((Project)p.clone());
		c.setProjects(projects);
		return c;
	}

}
