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

}
