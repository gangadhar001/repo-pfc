package gcad.domain;

import java.util.Date;

public class Issue {
	
	// TODO: si hay herencia, hay que cambiarlo a protected
	private String title;
	private String description;
	private Date date;
	// This attribute indicates if the proposal has been accepted or not yet.
	private int state;
	
	public Issue() {
	
	}
	
	public Issue(String title, String description, Date date, int state) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	
	}
	
}
