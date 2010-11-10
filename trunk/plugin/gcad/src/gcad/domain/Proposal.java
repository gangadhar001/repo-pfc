package gcad.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Proposal extends AbstractProposal {
	
	// TODO: si hay herencia con categorias de analisis y diseño, hay que cambiarlo a protected
	// This attribute indicates if the proposal has been accepted or not yet.
	private int state;
	
	// TODO: Una propuesta tiene a su vez otras propuestas o respuestas
	private List<AbstractProposal> proposals;
	
	public Proposal() {
		super();
	}
	
	public Proposal(String title, String description, Date date, int state) {
		super(title, description, date);
		this.state = state;
		proposals = new ArrayList<AbstractProposal>();
	}

	public List<AbstractProposal> getProposals() {
		return proposals;
	}

	public void setProposals(List<AbstractProposal> proposals) {
		this.proposals = proposals;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	
	}

	@Override
	public void add(AbstractProposal aProposal) {
		proposals.add(aProposal);
		
	}

	@Override
	public String getInformation() {
		String information = "";
		// Information about this proposal
		information += this.toString();
		// Get information recursively about child proposals
		for (int i=0; i<proposals.size(); i++) {
			information += "\t" + proposals.get(i).getInformation();
		}
		return information;
		
		
	}

	@Override
	public void remove(AbstractProposal aProposal) {
		proposals.remove(aProposal);
		
	}
	
	@Override
	public String toString () {
		return "Title: " + super.getTitle() + " Description: " + super.getDescription() + " Date: " + super.getDate() + " State: " + this.state + "\n";
	}
	
}
