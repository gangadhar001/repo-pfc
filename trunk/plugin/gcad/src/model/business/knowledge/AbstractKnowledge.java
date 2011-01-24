package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * This class represents the knowledge to manage, which can be a Proposal or an Answer.
 * Composite Pattern is applied 
 */

@XmlAccessorType( XmlAccessType.FIELD )
public abstract class AbstractKnowledge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7039151251262020404L;
	
	protected int id;
	protected String title;
	protected String description;
	protected Date date;
	@XmlElement protected User user;
		
	public AbstractKnowledge() {
	}
	
	public AbstractKnowledge(String title, String description, Date date) {
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
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
