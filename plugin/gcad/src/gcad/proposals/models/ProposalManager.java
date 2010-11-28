package gcad.proposals.models;

import gcad.domain.AbstractProposal;
import gcad.domain.Proposal;
import gcad.exceptions.NoProjectProposalsException;
import gcad.listeners.ProposalListener;
import gcad.persistence.PFProposal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Esta clase se encarga de gestionar las propuestas.  
 */

public class ProposalManager {

	//TODO: singleton
	
	private static ProposalManager manager;
	private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	private ArrayList<AbstractProposal> proposals;
	
	public ProposalManager () {	
	}
	
	public static ProposalManager getManager() {
		if (manager == null) {
			manager = new ProposalManager();
		}
		return manager;
	}
	
	/**
	 * Este método se utiliza para leer la jerarquia de propuestas de la base de datos.
	 * Se devuelven aquellas que sn raices primarias, junto a todos sus hijos
	 */
	public ArrayList<AbstractProposal> getProposalsTree() throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO: traducir a ingles. Se muestra a la inversa
		if (proposals == null)
			proposals = PFProposal.queryProposalTreeProject(2);
		return proposals;
	}
	
	/**
	 * Este metodo devuelve todas las propuestas existentes
	 */
	public ArrayList<AbstractProposal> getProposals() throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<AbstractProposal> existingProposals = getProposalsTree();
		ArrayList<AbstractProposal> result = new ArrayList<AbstractProposal>();
		for (AbstractProposal p: existingProposals) {
			if (p instanceof Proposal) {
				result.add(p);
				result.addAll(getRecursiveProposal((Proposal)p));
			}
		}
		return result;
	}
	
	private ArrayList<AbstractProposal> getRecursiveProposal(Proposal proposal) {
		ArrayList<AbstractProposal> list = new ArrayList<AbstractProposal>();
		ArrayList<AbstractProposal> result = new ArrayList<AbstractProposal>();
		list = proposal.getProposals();
		for (AbstractProposal p: list) {
			if (p instanceof Proposal) {
				result.add(p);
				result.addAll(getRecursiveProposal((Proposal)p));
			}
		}
		return result;
	}

	// TODO: hacer la clase generica para propuestas/respuestas
	public void addKnowledge(AbstractProposal knowledge, AbstractProposal parent) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Se añade la nueva propuesta al modelo ya existente
		//ArrayList<AbstractProposal> existingProposals = getProposalsTree();
		// Se busca dentro de la jerarquia la propuesta padre, para añadirle un nuevo hijo
		parent.add(knowledge);
		//int index = existingProposals.indexOf(parent);
		//existingProposals.get(index).add(knowledge);
		// Se inserta en la base de datos
		if (knowledge instanceof Proposal)
			PFProposal.insert((Proposal)knowledge, parent.getId());
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
