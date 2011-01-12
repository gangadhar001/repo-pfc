package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.NoProposalsException;

import persistence.PFAnswer;
import persistence.PFProposal;
import persistence.PFTopic;

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
	public static TopicWrapper getTopicsProject(int idProject) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// This method access to database only the first time, when "proposals" is not initialized.
		if (topicWrapper == null) {
			 topicWrapper = new TopicWrapper();
			 topicWrapper.setTopics(PFTopic.queryTopicsProject(idProject));
		}
		return topicWrapper;
	}
	
	/**
	 * This method returns all existing proposals 
	 */
	public static ArrayList<Proposal> getProposals() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException {
		return PFProposal.queryAllProposals();		
	}

	public static void addProposal(Proposal proposal, Topic parent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Add the new proposal to the existing topic
		parent.add(proposal);
		PFProposal.insert(proposal, parent.getId());
	}
	
	public static void addAnswer(Answer answer, Proposal parent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Add the new proposal to the existing topic
		parent.add(answer);
		PFAnswer.insert(answer, parent.getId());
	}
	
	
}
