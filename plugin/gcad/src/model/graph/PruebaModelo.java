package model.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.business.knowledge.AbstractKnowledge;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;

public class PruebaModelo {

	private List<AbstractKnowledge> nodos;
	private List<Edge> aristas;
	
	public PruebaModelo () {
		nodos = new ArrayList<AbstractKnowledge>();
		aristas = new ArrayList<Edge>();
		AbstractKnowledge n1 = new Proposal("a","a",new Date(), Categories.Analysis, 0);
		AbstractKnowledge n2 = new Answer("b","b",new Date());
		nodos.add(n1);
		nodos.add(n2);
		Edge e = new Edge("1", "", nodos.get(0), nodos.get(1));
		aristas.add(e);
	}

	public List<AbstractKnowledge> getNodos() {
		return nodos;
	}
	
	
}
