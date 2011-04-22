package model.business.knowledge;

import java.io.Serializable;

/**
 * This class represents a notification about new Knowledge available in the project
 */
public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1555825725259998376L;
	
	private int id;
	private Knowledge knowledge;
	private String state;
	private Project project;
	
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

	@Override
	public String toString() {
		return "Notification [id=" + id + ", knowledge=" + knowledge + "]";
	}	
	
	public Object clone() {
		Notification n;
		n = new Notification((Knowledge)getKnowledge().clone());
		n.setId(getId());
		n.setState(getState());
		n.setProject((Project)getProject().clone());
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
