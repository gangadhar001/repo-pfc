package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents the knowledge to manage, which can be a Topic, a Proposal or an Answer. 
 */
@XmlAccessorType( XmlAccessType.FIELD )
public abstract class Knowledge implements Serializable {

	private static final long serialVersionUID = -7039151251262020404L;
	
	protected int id;
	protected String title;
	protected Date date;
	protected String description;
	@XmlElement protected User user;
		
	public Knowledge() {
	}
	
	public Knowledge(String title, String description, Date date) {
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
		
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
		
	public void setDescription(String description) {
		this.description = description;
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public abstract Object clone();
	
	public abstract boolean equals(Object o);
	
}
