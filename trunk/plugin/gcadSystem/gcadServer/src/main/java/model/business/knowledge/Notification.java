package model.business.knowledge;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a notification about new Knowledge available in the project
 */
public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1555825725259998376L;
	
	private int id;
	private Knowledge knowledge;
	private String state;
	private Project project;
	private String subject;
	private Set<User> users;
	
	public Notification() { }
	
	public Notification(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Knowledge getKnowledge() {
		return knowledge;
	}
	
	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", knowledge=" + knowledge + "]";
	}	
	
	public Object clone() {
		Notification n;
		Set<User> users = new HashSet<User>();
		n = new Notification((Knowledge)getKnowledge().clone());
		n.setId(getId());
		n.setState(getState());
		n.setProject((Project)getProject().clone());
		n.setSubject(getSubject());
		for (User u: getUsers())
			users.add((User)u.clone());
		n.setUsers(users);
		return n;
	}
	
	public boolean equals(Object obj) {
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof Notification) {
			Notification other = (Notification) obj;
//			result = (knowledge.equals(other.getKnowledge()) && state.equals(other.getState()) &&
//					project.equals(other.getProject()));
			result = (id == other.getId());
		}
		return result;
	}
	
	public int hashCode() {
		return id;
	}

}
