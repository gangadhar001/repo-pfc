package model.business.knowledge;

import java.sql.Timestamp;
import java.util.Date;

public class LogEntry {
	
	private int id;
	private String user;
	private Timestamp date;
	private String action;
	private String message;
	
	public LogEntry() {
		id = -1;
		user = "";
		date = new Timestamp((new Date()).getTime());
		action = "";
		message = "";
	}

	public LogEntry(String user, Timestamp date, String action, String message) {
		this.id = -1;
		this.user = user;
		this.date = date;
		this.action = action;		
		this.message = message;
	}
	
	public LogEntry(String user, String action, String message) {
		this.id = -1;
		this.user = user;
		date = new Timestamp((new Date()).getTime());
		this.action = action;		
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object clone() {
		LogEntry le = new LogEntry(getUser(), getAction(), getMessage());
		le.setDate((Timestamp) getDate().clone());
		le.setId(getId());
		return le;
	}
	
	public boolean equals(Object o) {
		LogEntry e;
		boolean res;
		
		res = false;
		if(o != null && o instanceof LogEntry) {
			e = (LogEntry)o;
			res = getDate().equals(e.getDate()) && getAction().equals(e.getAction())
			    && getMessage().equals(e.getMessage());
			if(getUser() == null) {
				res = res && e.getUser() == null;
			} else {
				res = res && getUser().equals(e.getUser());
			}
		}
		return res;
	}
	

}
