package model.business.knowledge;

import java.io.Serializable;

/**
 * This class represents a Session for one user
 */
public class Session implements ISession, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1987681435226028780L;
	private long idSesion;
	private int currentActiveProject;
	private User user;
	
	public Session(long idSesion, User user) {
		this.idSesion = idSesion;
		this.user = user;
	}
	
	public long getId() {
		return idSesion;
	}

	public void setId(long idSesion) {
		this.idSesion = idSesion;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public int getRole() {
		return user.getRole().ordinal();
	}
	
	public int getCurrentActiveProject() {
		return currentActiveProject;
	}

	public void setCurrentActiveProject(int currentActiveProject) {
		this.currentActiveProject = currentActiveProject;
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof Session) {
			Session other = (Session) obj;
			result = (idSesion == other.getId() && currentActiveProject == other.getCurrentActiveProject() && user.equals(other.getUser()));
		}
		return result;
	}


}
