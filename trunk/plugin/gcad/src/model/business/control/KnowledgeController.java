package model.business.control;

import exceptions.NoProjectProposalsException;

import java.sql.SQLException;
import java.util.ArrayList;

import persistence.PFProposal;

import model.business.knowledge.AbstractProposal;
import model.business.knowledge.Proposal;

/**
 * This class represents a manager that allows to manage the knowledge (proposals or answers)
 * Singleton Pattern is applied
 */
public class KnowledgeController {

	
	//TODO: private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	private static ArrayList<AbstractProposal> proposals;
	
	/**
	 * This method is used to retrieve the proposals and answers hierarchy from database.
	 */
	public static ArrayList<AbstractProposal> getProposalsTree() throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO: Se muestra a la inversa
		// This method access to database only the first time, when "proposals" is not initialized.
		if (proposals == null)
			proposals = PFProposal.queryProposalTreeProject(2);
		return proposals;
	}
	
	/**
	 * This method returns all existing proposals, recursively
	 */
	public static ArrayList<AbstractProposal> getProposals() throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
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
	
	private static ArrayList<AbstractProposal> getRecursiveProposal(Proposal proposal) {
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

	public static void addKnowledge(AbstractProposal knowledge, AbstractProposal parent) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Add the new knowledge(proposal/answer) to the existing proposal
		parent.add(knowledge);
		// The new knowledge is inserted into database
		if (knowledge instanceof Proposal)
			PFProposal.insert((Proposal)knowledge, parent.getId());
		/* TODO:
		 * else if (knowledge instanceof Answer)
			PFAnswer.insert((Answer)knowledge, parent.getId());*/
	}
	
	/* TODO: usado?????
	 * public void addProposalManagerListener(ProposalListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeProposalManagerListener(ProposalListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}*/
	
	
}
