package model.business.knowledge;


import internationalization.BundleInternationalization;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

/**
 * This class represents a Proposal
 */

@XmlRootElement ( name="Proposals" )
@XmlAccessorType( XmlAccessType.FIELD )
public class Proposal extends AbstractProposal {
	
	// TODO: si hay herencia con categorias de analisis y diseño, hay que cambiarlo a protected
	
	// This attribute indicates if the proposal has been accepted or not yet.
	private int state;
	
	private Categories category;
	
	// A proposal has another proposals and answers
	@XmlElements({
        @XmlElement(name = "Answer",   type = Answer.class),
        @XmlElement(name = "Proposal", type = Proposal.class)
    }) private ArrayList<AbstractProposal> proposals;
	
	public Proposal() {
		super();
		proposals = new ArrayList<AbstractProposal>();
	}
	
	public Proposal(String title, String description, Date date, Categories category, int state) {
		super(title, description, date);
		this.state = state;
		this.category = category;
		proposals = new ArrayList<AbstractProposal>();
	}

	public ArrayList<AbstractProposal> getProposals() {
		return proposals;
	}

	public void setProposals(ArrayList<AbstractProposal> proposals) {
		this.proposals = proposals;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	@Override
	public void add(AbstractProposal aProposal) {
		proposals.add(aProposal);
		
	}
	
	@Override
	public void remove(AbstractProposal aProposal) {
		proposals.remove(aProposal);
		
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
	public String toString () {
		return BundleInternationalization.getString("Title") +": " + super.getTitle() 
		+ " " + BundleInternationalization.getString("Description") +": " + super.getDescription() 
		+ " " + BundleInternationalization.getString("Date") +": " + super.getDate()
		+ " " + BundleInternationalization.getString("Category") +": " + getCategory()
		+ " " + BundleInternationalization.getString("State") +": "  + this.state;
	}
	
}
