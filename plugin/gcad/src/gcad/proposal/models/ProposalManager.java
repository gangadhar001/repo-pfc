package gcad.proposal.models;

import gcad.domain.Answer;
import gcad.domain.Proposal;
import gcad.listeners.ProposalListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ProposalManager {

	//TODO: singleton
	
	private static ProposalManager manager;
	private Collection<Proposal> issues;
	private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	
	private ProposalManager () {	
	}
	
	public static ProposalManager getManager() {
		if (manager == null) {
			manager = new ProposalManager();
		}
		return manager;
	}
	
	public IProposalItem[] getProposals() {
		if (issues == null)
			loadProposals();
		return issues.toArray(new IProposalItem[issues.size()]);
	}
	
	// TODO: temporal. Aqui se cargarian las propuestas (de ambos tipos) de la base de datos
	public void loadProposals() {
		issues = new HashSet<Proposal>();
		// TODO: en la persistencia, se mira el tipo de la propuesta para crear un objeto de la subclase adecuada 
		issues.add(new Answer("a", "b", new Date()));
		issues.add(new Answer("c0", "d", new Date()));
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
