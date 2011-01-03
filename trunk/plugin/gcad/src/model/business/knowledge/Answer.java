package model.business.knowledge;


import internationalization.BundleInternationalization;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** 
 * This class represents an Answer given to a Proposal
 */
@XmlAccessorType( XmlAccessType.FIELD )
public class Answer extends AbstractProposal{
		
	public Answer() {
	}
	
	public Answer(String title, String argument, Date date) {
		super(title, argument, date);
		
	}

	// An answer cannot have any child
	@Override
	public void add(AbstractProposal aProposal) {		
	}

	@Override
	public void remove(AbstractProposal aProposal) {		
	}
	
	@Override
	public String getInformation() {
		// Information about this answer
		return BundleInternationalization.getString("Title") +": " + super.getTitle() 
		+ " " + BundleInternationalization.getString("Description") +": " + super.getDescription() 
		+ " " + BundleInternationalization.getString("Date") +": " + super.getDate();
	}
	
	public String toString(){
		return getInformation();
	}


}
