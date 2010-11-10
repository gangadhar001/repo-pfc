package gcad.proposal.models;

import gcad.domain.AbstractProposal;
import gcad.domain.Answer;
import gcad.listeners.ProposalListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ProposalManager {

	//TODO: singleton
	
	private static ProposalManager manager;
	private Collection<AbstractProposal> proposals;
	private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	
	public ProposalManager () {	
	}
	
	public static ProposalManager getManager() {
		if (manager == null) {
			manager = new ProposalManager();
		}
		return manager;
	}
	
	public IProposalItem[] getProposals() {
		if (proposals == null)
			loadProposals();
		return proposals.toArray(new IProposalItem[proposals.size()]);
	}
	
	// TODO: temporal. Aqui se cargarian las propuestas (de ambos tipos) de la base de datos
	public void loadProposals() {
		proposals = new HashSet<AbstractProposal>();
		// TODO: en la persistencia, se mira el tipo de la propuesta para crear un objeto de la subclase adecuada 
		proposals.add(new Answer("a", "b", new Date()));
		proposals.add(new Answer("c0", "d", new Date()));
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
