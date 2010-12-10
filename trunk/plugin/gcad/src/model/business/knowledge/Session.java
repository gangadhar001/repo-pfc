package model.business.knowledge;

public class Session implements ISession {
	
	private long idSesion;
	private User user;
	
	public Session(long idSesion, User user) {
		this.idSesion = idSesion;
		this.user = user;
	}
	
	public long getId() {
		return idSesion;
	}

	public long getRol() {
		return user.getRol().ordinal();
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
