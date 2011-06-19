package model.business.knowledge;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class that represents a system user
 */
@XmlAccessorType( XmlAccessType.FIELD )
public abstract class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5527024994128298797L;
	
	protected int id;
	protected String nif;
	protected String login;
	protected String password;
	protected String name;
	protected String surname;
	protected String email;
	protected String telephone;
	protected int seniority;
	@XmlElement( name = "Project" )
	private Set<Project> projects = new HashSet<Project>();
	@XmlElement private Company company;
	
	public User () {
		
	}
	
	public User(String nif, String login, String password, String name, String surname, String email, String telephone, int seniority, Company c) {
		this.nif = nif;
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telephone = telephone;
		this.seniority = seniority;
		this.company = c;
	}
	
	public abstract UserRole getRole();
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getSeniority() {
		return seniority;
	}

	public void setSeniority(int seniority) {
		this.seniority = seniority;
	}

	@Override
	public String toString () {
		return name + " " + surname;
	}
	
	public String getInfo() {
		StringBuffer result = new StringBuffer();
		Project p;
		result.append("User:\n");
		result.append("      " + nif + "\n");
		result.append("      " + name + "\n");
		result.append("      " + surname + "\n");
		result.append("      " + email + "\n");
		result.append("      " + telephone + "\n");
		result.append("      " + company + "\n");
		result.append("      Projects:\n");
		for (Iterator<Project> i = projects.iterator(); i.hasNext(); ) {
			p = (Project) i.next();
			result.append("      " + p + "\n");
		}			
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof User) {
			User other = (User) obj;
			result = (nif.equals(other.getNif()));
			}
		return result;
	}
		
	public abstract Object clone();
	
}
