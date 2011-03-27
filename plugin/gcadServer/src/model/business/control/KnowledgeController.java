package model.business.control;

import java.sql.SQLException;
import java.util.ArrayList;

import model.business.knowledge.Answer;
import model.business.knowledge.Project;
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
			for (Topic t: DAOTopic.queryTopicsProject(Server.getInstance().getSession().getCurrentActiveProject()))
				topicWrapper.add(t);
		}
		return topicWrapper;
	}
	
	/**
	 * This method returns all existing proposals from a project 
	 */
	public static ArrayList<Proposal> getProposals() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoProposalsException {
		ArrayList<Proposal> proposals = new ArrayList<Proposal>();
		for (Topic t: getTopicsWrapper().getTopics()) {
			proposals.addAll(t.getProposals());
		}
		return proposals;
	}
	
	/**
	 * This method returns all existing answers from a project
	 */
	public static ArrayList<Answer> getAnswers() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		ArrayList<Answer> answers = new ArrayList<Answer>();
		for (Topic t: getTopicsWrapper().getTopics()) {
			for (Proposal p: t.getProposals()) {
				answers.addAll(p.getAnswers());
			}
		}
		return answers;
	}

	/**
	 * Methods used to add new knowledge
	 */
	public static void addTopic(User u, Project p, Topic topic) throws SQLException {
		topic.setUser(u);
		topic.setProject(p);
		topicWrapper.add(topic);
		DAOTopic.insert(topic);
		
	}
	
	public static void addProposal(User u, Proposal proposal, Topic parent) throws SQLException {
		proposal.setUser(u);
		parent.add(proposal);
		DAOProposal.insert(proposal, parent.getId());
	}
	
	public static void addAnswer(User u, Answer answer, Proposal parent) throws SQLException {
		answer.setUser(u);
		parent.add(answer);
		DAOAnswer.insert(answer, parent.getId());
	}
	
	/**
	 * Methods used to modify knowledge
	 */
	
	public static void modifyTopic(User user, Topic newTopic, Topic oldTopic) throws SQLException {
		
		newTopic.setUser(user);
		// Copy proposal list and project to new Topic
		newTopic.setProposals(oldTopic.getProposals());
		newTopic.setProject(oldTopic.getProject());
		// Remove old topic
		int index = topicWrapper.getTopics().indexOf(oldTopic);
		topicWrapper.remove(oldTopic);
		// Add new answer to proposal
		topicWrapper.getTopics().add(index, newTopic);
		DAOTopic.update(newTopic);
	}
	
	public static void modifyProposal(User user, Proposal newProposal, Proposal oldProposal, Topic newParent) throws SQLException {
		newProposal.setUser(user);
				
		// If the new parent is different from the previous one, the old proposal is removed
		Topic t = findParentProposal(oldProposal);
		if (t!=null && !t.equals(newParent)) {	
			t.getProposals().remove(oldProposal);
			// Delete the old proposal from old topic (it deletes the answers too)
			DAOProposal.delete((Proposal)oldProposal.clone());
			DAOProposal.insert(newProposal, newParent.getId());
			// Insert answers from old Proposal
			for (Answer a: oldProposal.getAnswers())
				DAOAnswer.insert(a, newProposal.getId());
			newProposal.setAnswers(oldProposal.getAnswers());
			newParent.add(newProposal);
			
		}
		else {
			newParent.remove(oldProposal);
			newParent.add(newProposal);
			DAOProposal.update(newProposal);
		}
	}
	
	public static void modifyAnswer(User user, Answer newAnswer, Answer oldAnswer, Proposal newParent) throws SQLException {
		newAnswer.setUser(user);
		// If the new parent is different from the previous one, the old answer is removed
		Proposal p = findParentAnswer(oldAnswer);
		if (p!=null && !p.equals(newParent)) {		
			p.getAnswers().remove(oldAnswer);
			newParent.add(newAnswer);
			DAOAnswer.delete(oldAnswer);
			DAOAnswer.insert(newAnswer, newParent.getId());
		}
		else {
			newParent.remove(oldAnswer);
			newParent.add(newAnswer);
			DAOAnswer.update(newAnswer);
		}
	}
	
	public static void deleteTopic(Topic to) throws SQLException {
		topicWrapper.remove(to);
		DAOTopic.delete(to);
	}
	
	public static void deleteProposal(Proposal p) throws SQLException {
		Topic t = findParentProposal(p);
		if (t!=null)
			t.getProposals().remove(p);
		DAOProposal.delete(p);
	}

	public static void deleteAnswer(Answer a) throws SQLException {
		Proposal p = findParentAnswer(a);
		if (p!=null)
			p.getAnswers().remove(a);
		DAOAnswer.delete(a);
	}

	private static Proposal findParentAnswer(Answer a) {
		Proposal result = null;
		for(Topic t: topicWrapper.getTopics()){
			for (Proposal p: t.getProposals())
				if (p.getAnswers().contains(a))
					result = p;
		}
		return result;
	}
	
	private static Topic findParentProposal(Proposal p) {
		Topic result = null;	
		for(Topic t: topicWrapper.getTopics()){
			if (t.getProposals().contains(p))
				result = t;
		}
		return result;
	}		
}
