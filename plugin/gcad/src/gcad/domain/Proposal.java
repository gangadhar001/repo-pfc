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
	
}
