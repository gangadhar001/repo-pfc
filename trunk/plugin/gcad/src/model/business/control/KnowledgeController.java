package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.knowledge.Answer;
import model.business.knowledge.Proposal;
import model.business.knowledge.Topic;
import model.business.knowledge.TopicWrapper;
import model.business.knowledge.User;
import persistence.DAOAnswer;
import persistence.DAOProposal;
import persistence.DAOTopic;
import exceptions.NoProposalsException;

/**
 * This class represents a controller that allows to manage knowledge.
 */
public class KnowledgeController {
	
	private static TopicWrapper topicWrapper;
	
	public static TopicWrapper getTopicsWrapper() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// This method access to database only the first time, when "topicWrapper" is not initialized.
		if (topicWrapper == null) {
			topicWrapper = new TopicWrapper();
			for (Topic t: DAOTopic.queryTopicsProject(Controller.getInstance().getSession().getCurrentActiveProject()))
				topicWrapper.add(t);
		}
		return topicWrapper;
	}
	
	/**
	 * This method returns all existing proposals 
	 */
	public static ArrayList<Proposal> getProposals() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException {
		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		for (Topic t: getTopicsWrapper().getTopics()) {
			proposals.addAll(t.getProposals());
		}
		return proposals;
	}

	public static void addTopic(Topic topic) throws SQLException {
		topicWrapper.add(topic);
		DAOTopic.insert(topic);
		
	}
	
	public static void addProposal(User u, Proposal proposal, Topic parent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		proposal.setUser(u);
		parent.add(proposal);
		DAOProposal.insert(proposal, parent.getId());
	}
	
	public static void addAnswer(User u, Answer answer, Proposal parent) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		answer.setUser(u);
		parent.add(answer);
		DAOAnswer.insert(answer, parent.getId());
	}
	
	public static void deleteTopic(Topic to) throws SQLException {
		topicWrapper.remove(to);
		DAOTopic.delete(to);
	}
	
	public static void deleteProposal(Proposal p) throws SQLException {
		for(Topic t: topicWrapper.getTopics()){
			if (t.getProposals().contains(p))
				t.getProposals().remove(p);
		}
		DAOProposal.delete(p);
	}

	public static void deleteAnswer(Answer a) throws SQLException {
		for(Topic t: topicWrapper.getTopics()){
			for (Proposal p: t.getProposals())
				if (p.getAnswers().contains(a))
					p.getAnswers().remove(a);
		}
		DAOAnswer.delete(a);
	}

		
}
