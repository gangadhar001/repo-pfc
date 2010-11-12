package gcad.proposal.models;

import gcad.domain.Answer;
import gcad.domain.Proposal;
import gcad.listeners.ProposalListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProposalManager {

	//TODO: singleton
	
	private static ProposalManager manager;
	private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	
	public ProposalManager () {	
	}
	
	public static ProposalManager getManager() {
		if (manager == null) {
			manager = new ProposalManager();
		}
		return manager;
	}
	
	public void makeProposalsTree (Proposal root) {
		
		// TODO: temporal. Aqui se cargarian las propuestas (de ambos tipos) de la base de datos
		// TODO: en la persistencia, se mira el tipo de la propuesta para crear un objeto de la subclase adecuada 
		Proposal ex = new Proposal("Raiz", "prueba", new Date(),0);

		ex.add(new Answer("Nodo1", "a", new Date()));

		ex.add(new Answer("Nodo2", "b", new Date()));
		

		Proposal comp = new Proposal("Raiz2", "prueba2", new Date(),1);

		comp.add(new Answer("Nodo3", "c", new Date()));
		root.add(ex);
		root.add(comp);
	}
	
	public void addProposalManagerListener(ProposalListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeProposalManagerListener(ProposalListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}
	
	
}
