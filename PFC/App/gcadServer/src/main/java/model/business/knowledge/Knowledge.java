package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import model.business.control.auxiliary.DateAdapter;

/**
 * This class represents the knowledge to manage, which can be a Topic, a Proposal or an Answer. 
 */
@XmlAccessorType( XmlAccessType.FIELD )
public abstract class Knowledge implements Serializable {

	private static final long serialVersionUID = -7039151251262020404L;
	
	protected int id;
	protected String title;
	@XmlJavaTypeAdapter(DateAdapter.class)
	protected Date date;
	protected String description;
	protected KnowledgeStatus status;
	@XmlElement( name = "Author" ) protected User user;
		
	public Knowledge() {
	}
	
	public Knowledge(String title, String description, Date date) {
		this.title = title;
		this.description = description;
		this.date = date;
		this.status = KnowledgeStatus.Open;
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
	
	public KnowledgeStatus getStatus() {
		return status;
	}

	public void setStatus(KnowledgeStatus status) {
		this.status = status;
	}

	public abstract Object clone();
	
	public abstract boolean equals(Object o);
	
}
