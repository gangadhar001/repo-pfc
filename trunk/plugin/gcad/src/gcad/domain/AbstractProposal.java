package gcad.domain;

import java.util.Date;

public abstract class AbstractProposal {

	protected int id;
	protected String title;
	protected String description;
	protected Date date;
		
	public AbstractProposal() {
		title = "";
		description = "";
		date = new Date();
	}
	
	public AbstractProposal(String title, String description, Date date) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public abstract void add(AbstractProposal aProposal);

    public abstract void remove(AbstractProposal aProposal);

    public abstract String getInformation();
	
}