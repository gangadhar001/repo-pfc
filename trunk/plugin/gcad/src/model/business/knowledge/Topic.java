package model.business.knowledge;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType( XmlAccessType.FIELD )
public class Topic {
	
	private int id;
	private String title;
	@XmlElement( name = "Proposal" )
	private ArrayList<Proposal> proposals;
	
	public Topic() {
		proposals = new ArrayList<Proposal>();
	}
	
	public Topic(String title) {
		this.title = title;
		proposals = new ArrayList<Proposal>();
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

	public ArrayList<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(ArrayList<Proposal> proposals) {
		this.proposals = proposals;
	}
	
	public void add(Proposal proposal) {
		proposals.add(proposal);
		
	}
	
	public void remove(Proposal proposal) {
		proposals.remove(proposal);
		
	}	

}
