package model.business.knowledge;


import internationalization.BundleInternationalization;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/** 
 * This class represents an Answer given to a Proposal
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Answer extends AbstractKnowledge{
		
	public Answer() {
	}
	
	public Answer(String title, String argument, Date date) {
		super(title, argument, date);
		
	}

	@Override
	public String toString() {
		// Information about this answer
		return BundleInternationalization.getString("Title") +": " + super.getTitle() 
		+ " " + BundleInternationalization.getString("Description") +": " + super.getDescription() 
		+ " " + BundleInternationalization.getString("Date") +": " + super.getDate();
	}
	
}
