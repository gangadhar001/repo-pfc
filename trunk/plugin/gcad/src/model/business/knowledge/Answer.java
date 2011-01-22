package model.business.knowledge;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/** 
 * This class represents an Answer given to a Proposal
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Answer extends AbstractKnowledge{
		
	public Answer(String title, String argument, Date date, User u) {
		super(title, argument, date, u);
		
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
	
}
