package model.business.control;

import exceptions.NoProjectProposalsException;

import java.sql.SQLException;
import java.util.ArrayList;

import persistence.PFAnswer;
import persistence.PFProposal;
import persistence.PFTopic;

import model.business.knowledge.AbstractKnowledge;
import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;

/**
 * This class represents a manager that allows to manage the knowledge (proposals or answers)
 * Singleton Pattern is applied
 */
public class KnowledgeController {

	
	//TODO: private List<ProposalListener> listeners = new ArrayList<ProposalListener>();
	private static TopicWrapper topicWrapper;
	
	/**
	 * This method is used to retrieve the proposals and answers hierarchy from database.
	 */
	public static TopicWrapper getKnowledgeTreeProject(int idProject) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO: Se muestra a la inversa
		// This method access to database only the first time, when "proposals" is not initialized.
		if (topicWrapper == null) {
			 topicWrapper = new TopicWrapper();
			 topicWrapper.setTopics(PFTopic.queryTopicsProject(idProject));
		}
		return topicWrapper;
	}
	
	/**
	 * This method returns all existing proposals, recursively
	 */
	public static ArrayList<AbstractKnowledge> getProposals() throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<AbstractKnowledge> existingProposals = getKnowledgeTree();
		ArrayList<AbstractKnowledge> result = new ArrayList<AbstractKnowledge>();
		for (AbstractKnowledge p: existingProposals) {
			if (p instanceof Proposal) {
				result.add(p);
				result.addAll(getRecursiveProposal((Proposal)p));
			}
		}
		return result;
	}
	
	private static ArrayList<AbstractKnowledge> getRecursiveProposal(Proposal proposal) {
		ArrayList<AbstractKnowledge> list = new ArrayList<AbstractKnowledge>();
		ArrayList<AbstractKnowledge> result = new ArrayList<AbstractKnowledge>();
		list = proposal.getProposals();
		for (AbstractKnowledge p: list) {
			if (p instanceof Proposal) {
				result.add(p);
				result.addAll(getRecursiveProposal((Proposal)p));
			}
		}
		return result;
	}

	public static void addProposal(Proposal proposal, Topic parent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Add the new proposal to the existing topic
		PFProposal.insert(proposal, parent.getId());
	}
	
	/*public static void addAnswer(Answer answer, Proposal parent) throws SQLException, NoProjectProposalsException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Add the new proposal to the existing topic
		PFAnswer.insert(answer, parent.getId());
	}*/
	
	
}
