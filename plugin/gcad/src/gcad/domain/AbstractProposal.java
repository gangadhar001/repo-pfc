package gcad.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractProposal {

	protected String title;
	protected String description;
	protected Date date;
		
	public AbstractProposal() {
		title = "";
		description = "";
		date = null;
	}
	
	public AbstractProposal(String title, String description, Date date) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
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
	
}
