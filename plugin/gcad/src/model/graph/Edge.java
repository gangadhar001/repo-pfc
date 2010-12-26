package model.graph;

import model.business.knowledge.AbstractProposal;

public class Edge {

	private String id;
	private String label;
	private AbstractProposal source;
	private AbstractProposal destination;
	
	public Edge(String id, String label, AbstractProposal source, AbstractProposal destination) {
		this.id = id;
		this.label = label;
		this.source = source;
		this.destination = destination;
	}
	
	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public AbstractProposal getSource() {
		return source;
	}

	public AbstractProposal getDestination() {
		return destination;
	}	
	
}
