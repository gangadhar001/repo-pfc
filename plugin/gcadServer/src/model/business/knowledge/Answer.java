package model.business.knowledge;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/** 
 * This class represents an Answer given to a Proposal
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Answer extends Knowledge implements Serializable{
			
	private static final long serialVersionUID = -1238919159476139631L;
	
	// TODO: cambiarlo por una enumeracion {Pro, Contra}
	private String argument;
	
	public Answer () {
		
	}
	
	public Answer(String title, String description, Date date, String argument) {
		super(title, description, date);
		this.argument = argument;
		
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Answer:\n");
		result.append("      " + title + "\n");
		result.append("      " + description + "\n");
		result.append("      " + date + "\n");
		result.append("      " + user + "\n");
		return result.toString();
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (this == obj)
			result = true;
		else if (obj == null)
			result = false;
		else if (getClass() != obj.getClass())
			result = false;
		else if (obj instanceof Answer) {
			Answer other = (Answer) obj;
			result = (title.equals(other.getTitle()) && date.equals(other.getDate()) &&
					description.equals(other.getDescription()) && user.equals(other.getUser()) && argument.equals(other.getArgument()));
		}
		return result;
	}	
	
	public Object clone () {
		Answer a;
		a = new Answer(getTitle(), getDescription(), getDate(), getArgument());
		a.setId(getId());
		a.setUser((User)getUser().clone());
		return a;
	}		
	
}
