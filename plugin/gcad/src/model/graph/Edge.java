package model.graph;

import model.business.knowledge.Knowledge;

/**
 * This class represents an edge of the graph
 */
public class Edge {

	private String id;
	private String label;
	private Knowledge source;
	private Knowledge destination;
	
	public Edge(String id, String label, Knowledge source, Knowledge destination) {
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

	public Knowledge getDestination() {
		return destination;
	}	
	
}
