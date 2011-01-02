package model.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.business.knowledge.AbstractProposal;
import model.business.knowledge.Answer;
import model.business.knowledge.Categories;
import model.business.knowledge.Proposal;

public class PruebaModelo {

	private List<AbstractProposal> nodos;
	private List<Edge> aristas;
	
	public PruebaModelo () {
		nodos = new ArrayList<AbstractProposal>();
		aristas = new ArrayList<Edge>();
		AbstractProposal n1 = new Proposal("a","a",new Date(), Categories.Analysis, 0);
		AbstractProposal n2 = new Answer("b","b",new Date());
		nodos.add(n1);
		nodos.add(n2);
		Edge e = new Edge("1", "", nodos.get(0), nodos.get(1));
		aristas.add(e);
	}

	public List<AbstractProposal> getNodos() {
		return nodos;
	}
	
	
}
