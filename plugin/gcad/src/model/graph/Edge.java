package model.graph;

import model.business.knowledge.AbstractKnowledge;

public class Edge {

	private String id;
	private String label;
	private AbstractKnowledge source;
	private AbstractKnowledge destination;
	
	public Edge(String id, String label, AbstractKnowledge source, AbstractKnowledge destination) {
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

	public AbstractKnowledge getSource() {
		return source;
	}

	public AbstractKnowledge getDestination() {
		return destination;
	}	
	
}
