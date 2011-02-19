package model.business.knowledge;

/**
 * This class represents a Session for one user
 */
public class Session implements ISession {
	
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

	public boolean equals(Object o) {
		Session u;
		boolean dev;
		
		dev = false;
		if(o != null && o instanceof Session) {
			u = (Session)o;
			dev = idSesion == u.getId() && user.equals(u.getUser());
		}
		return dev;
	}


}
