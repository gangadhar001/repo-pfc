package gcad.domain;

import java.util.Date;

public class Answer extends AbstractProposal{
		
	public Answer() {
	}
	
	public Answer(String title, String argument, Date date) {
		super(title, argument, date);
		
	}

	@Override
	public void add(AbstractProposal aProposal) {		
	}

	@Override
	public String getInformation() {
		String information = "";
		// Information about this answer
		information += "Title: " + super.getTitle() + " Description: " + super.getDescription() + " Date: " + super.getDate() + "\n";
		return information;
	}

	@Override
	public void remove(AbstractProposal aProposal) {		
	}	

}
