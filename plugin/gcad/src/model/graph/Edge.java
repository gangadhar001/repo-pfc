package model.graph;

import model.business.knowledge.AbstractKnowledge;

public class Edge {

	private String id;
	private String label;
	// TODO: el origen puede ser un Topic o una Proposal
	private Object source;
	private AbstractKnowledge destination;
	
	public Edge(String id, String label, Object source, AbstractKnowledge destination) {
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

	public Object getSource() {
		return source;
	}

	public AbstractKnowledge getDestination() {
		return destination;
	}	
	
}
